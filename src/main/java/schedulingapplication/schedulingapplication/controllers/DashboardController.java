package schedulingapplication.schedulingapplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;
import schedulingapplication.schedulingapplication.SchedulingApplication;
import schedulingapplication.schedulingapplication.models.User;
import schedulingapplication.schedulingapplication.utilities.AppointmentsHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the dashboard view
 */
public class DashboardController
{
    User user;
    @FXML
    private Label lblDashboardWelcome;
    @FXML private Label lblAppointments;
    @FXML private Button btnUsers;

    /**
     * <p>Initializes the data for the dashboard view</p>
     * <p>Sets the user as the logged in User</p>
     * <p>Displays a Hello message to the user</p>
     * <p>Checks for Appointments with a start time withing the next 15 minutes</p>
     * @param user The logged in User
     * @param stage
     * @throws SQLException
     */
    public void initData(User user, Stage stage) throws SQLException {
        this.user = user;
        btnUsers.setVisible(this.user.getName().equals("admin"));
        stage.setMaximized(true);
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" ");
        sb.append(user.getName());
        lblDashboardWelcome.setText(sb.toString());
        lblAppointments.setText(AppointmentsHelper.getUpcomingAppointments(user));
    }

    /**
     * <p>Displays the Customers view</p>
     * <p>Passes the logged in user to the Customers controller</p>
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void onCustomersButtonClick(ActionEvent event) throws SQLException, IOException {
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("customers-view.fxml"));*/
        FXMLLoader loader = new FXMLLoader(SchedulingApplication.class.getResource("clients-view.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        ClientsController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        controller.initData(user, window);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        window.setScene(tableViewScene);
        window.setMaximized(true);
        window.show();

    }

    /**
     * <p>Displays the Appointments view</p>
     * <p>Passes the logged in user to the Appointments controller</p>
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void onAppointmentsButtonClick(ActionEvent event) throws SQLException, IOException {
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("appointments-view.fxml"));*/
        FXMLLoader loader = new FXMLLoader(SchedulingApplication.class.getResource("appointments-view.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        AppointmentsController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        controller.initData(user, window);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        window.setScene(tableViewScene);
        window.setMaximized(true);
        window.show();
    }

    /**
     * <p>Displays the Contacts view</p>
     * <p>Passes the logged in user to the Contacts controller</p>
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void onContactsButtonClick(ActionEvent event) throws SQLException, IOException {
        /*FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("edit-customer-view.fxml"));
        loader.setLocation(getClass().getResource("contacts-view.fxml"));*/
        FXMLLoader loader = new FXMLLoader(SchedulingApplication.class.getResource("volunteers-view.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        window.setScene(tableViewScene);
        window.setMaximized(true);
        window.show();
    }

    /**
     * <p>Displays the Reports view</p>
     * <p>Passes the logged in user to the Reports controller</p>
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void onReportsButtonClick(ActionEvent event) throws SQLException, IOException {
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("reports-view.fxml"));*/
        FXMLLoader loader = new FXMLLoader(SchedulingApplication.class.getResource("reports-view.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        ReportsController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        controller.initData(user, window);
        window.setScene(tableViewScene);
        window.setMaximized(true);
        window.show();

    }

    /**
     * <p>Displays the Reports view</p>
     * <p>Passes the logged in user to the Reports controller</p>
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void onVolunteersButtonClick(ActionEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(SchedulingApplication.class.getResource("volunteers-view.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        VolunteersController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        controller.initData(user, window);
        window.setScene(tableViewScene);
        window.setMaximized(true);
        window.show();

    }

    public void onUsersButtonClick(ActionEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(SchedulingApplication.class.getResource("users-view.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        UsersController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        controller.initData(user, window);
        window.setScene(tableViewScene);
        window.setMaximized(true);
        window.show();

    }
}
