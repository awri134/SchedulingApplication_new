package schedulingapplication.schedulingapplication.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import schedulingapplication.schedulingapplication.SchedulingApplication;
import schedulingapplication.schedulingapplication.models.Appointment;
import schedulingapplication.schedulingapplication.models.User;
import schedulingapplication.schedulingapplication.models.Volunteer;
import schedulingapplication.schedulingapplication.utilities.AppointmentsHelper;
import schedulingapplication.schedulingapplication.utilities.ReportsHelper;
import schedulingapplication.schedulingapplication.utilities.VolunteersHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Controller for the Reports view
 */
public class ReportsController
{
    @FXML
    Text txtReport;
    @FXML
    Text txtTypeTotals;
    @FXML
    Text txtMonthTotals;
    @FXML
    Text txtCustReport;
    @FXML
    HBox hbTotals;
    @FXML
    Label lblReportTitle;

    @FXML
    VBox v;

    @FXML
    Pane paneCustReport;
    User user;

    /**
     * Initializes the data used for the controller setting the user to the logged in User
     * @param user The logged in User
     * @param stage
     */
    public void initData(User user, Stage stage)
    {
        this.user = user;

    }

    /**
     * Gets the data and displays it for the Month and Types totals report
     * @param event
     * @throws SQLException
     */
    public void onTotalsClicked(ActionEvent event) throws SQLException {
        v.setVisible(false);
        paneCustReport.setVisible(false);
        lblReportTitle.setText("Appointment Totals");
        txtTypeTotals.setText(ReportsHelper.getTotalsReport());
        txtMonthTotals.setText(ReportsHelper.getMonthsReport());
        hbTotals.setVisible(true);
    }

    /**
     * Gets the average appointment time in hours and minutes for each customer and displays it
     * @param event
     * @throws SQLException
     */
    public void onCustTimeClicked(ActionEvent event) throws SQLException {

        v.setVisible(false);
        hbTotals.setVisible(false);
        lblReportTitle.setText("Appointment Totals");
        txtCustReport.setText(ReportsHelper.getCustTimeReport());
        paneCustReport.setVisible(true);
    }


    /**
     * Displays the appointment schedules for each of the stored contacts
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void onSchedulesClicked(ActionEvent event) throws IOException, SQLException {
        hbTotals.setVisible(false);
        paneCustReport.setVisible(false);
        txtReport.setText("VOLUNTEERS SCHEDULES REPORT");
        TableView tv = new TableView();
        v.getChildren().clear();
        Group root = new Group();
        lblReportTitle.setText("Contact Schedules");
        ObservableList<Volunteer> volList = VolunteersHelper.getVolunteers();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        v.prefWidthProperty().bind(stage.widthProperty());
        v.prefHeightProperty().bind(stage.heightProperty());
        v.setPadding(new Insets(15,25,55,25));
        for (Volunteer con:volList)
        {
            ObservableList<Appointment> appList = AppointmentsHelper.getAppointments(con);

            TableView tableView = new TableView();
            tableView.setPlaceholder(new Label("No rows to display"));
            TableColumn<Appointment, Integer> colId = new TableColumn<>("ID");
            TableColumn<Appointment, String> colTitle = new TableColumn<>("Title");
            TableColumn<Appointment, String> colDesc = new TableColumn<>("Description");
            TableColumn<Appointment, String> colLoc = new TableColumn<>("Location");
            TableColumn<Appointment, String> colType = new TableColumn<>("Type");
            TableColumn<Appointment, Timestamp> colStart = new TableColumn<>("Start");
            TableColumn<Appointment, Timestamp> colEnd = new TableColumn<>("End");
            TableColumn<Appointment, Integer> colCust = new TableColumn<>("Client ID");
            TableColumn<Appointment, String> colCustName = new TableColumn<>("Client Name");


            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            colLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colStart.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
            colStart.setCellFactory(getCustomCellFactory());
            colEnd.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
            colEnd.setCellFactory(getCustomCellFactory());
            colCust.setCellValueFactory(new PropertyValueFactory<>("clientId"));
            colCustName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
            tableView.getColumns().add(colId);
            tableView.getColumns().add(colTitle);
            tableView.getColumns().add(colDesc);
            tableView.getColumns().add(colType);
            tableView.getColumns().add(colStart);
            tableView.getColumns().add(colEnd);
            tableView.getColumns().add(colCust);
            tableView.getColumns().add(colCustName);
            tableView.setItems(appList);
            tableView.setMinWidth(v.getMinWidth());
            tableView.maxHeight(v.getPrefHeight()/3.5);
            tableView.minHeight(300);

            tableView.prefHeight(v.getPrefHeight()/3.5);
            Label l = new Label();
            StringBuilder sb = new StringBuilder();
            sb.append(con.getName());
            sb.append(" Schedule");
            l.setText(String.valueOf(sb));
            l.prefWidthProperty().bind(v.widthProperty());
            l.setPadding(new Insets(15,15,5,15));
            l.setAlignment(Pos.CENTER);
            l.setFont(new Font(20));
            v.getChildren().add(l);
            v.getChildren().add(tableView);
            tableView.prefWidthProperty().bind(v.widthProperty());
            tableView.setVisible(true);

        }
        v.setPadding(v.getPadding());
        v.setVisible(true);
    }
    /**
     * Sets the display formatting of the TimeStamp object for the given column
     * @return The CallBack for displaying the correctly formatted TimeStamp
     */
    private Callback<TableColumn<Appointment, Timestamp>, TableCell<Appointment, Timestamp>> getCustomCellFactory()
    {
        return new Callback<TableColumn<Appointment, Timestamp>, TableCell<Appointment, Timestamp>>()
        {
            @Override
            public TableCell<Appointment, Timestamp> call(TableColumn<Appointment, Timestamp> appointmentTimestampTableColumn)
            {
                TableCell<Appointment, Timestamp> cell = new TableCell<Appointment, Timestamp>()
                {
                    @Override
                    public void updateItem(final Timestamp item, boolean empty)
                    {
                        if(item != null)
                        {
                            SimpleDateFormat tsf = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
                            setText(tsf.format(item));
                        }
                    }
                };
                return cell;
            }
        };
    }
    /**
     * Takes the user back to the dashboard view
     * @param event Back to Main Button clicked
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void backToMain(ActionEvent event) throws IOException, SQLException {
        /*FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("dashboard-view.fxml"));*/
        FXMLLoader loader = new FXMLLoader(SchedulingApplication.class.getResource("dashboard-view.fxml"));
        Parent dashboardParent = loader.load();
        Scene dashboardScene = new Scene(dashboardParent);
        DashboardController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        controller.initData(user, window);
        window.setTitle("Hello!");
        window.setScene(dashboardScene);
        window.show();
    }
}
