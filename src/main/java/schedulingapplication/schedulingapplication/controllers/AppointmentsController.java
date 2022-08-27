package schedulingapplication.schedulingapplication.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import schedulingapplication.schedulingapplication.SchedulingApplication;
import schedulingapplication.schedulingapplication.models.Appointment;
import schedulingapplication.schedulingapplication.models.Volunteer;
import schedulingapplication.schedulingapplication.models.Client;
import schedulingapplication.schedulingapplication.models.User;
import schedulingapplication.schedulingapplication.utilities.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableMap;

/**
 * Controller for the appointments-view.fxml file
 */
public class AppointmentsController implements Initializable {
    User user;
    @FXML
    private TableView<Appointment> tvAppointments;

    private ObservableList<Appointment> appList = AppointmentsHelper.getAppointments();
    private final Map<String, Integer> clientMap = observableMap(new HashMap<>());
    private final ObservableList<Client> clients = ClientsHelper.getClients();
    private ObservableList clientNames;
    private final ObservableList<Volunteer> vols = VolunteersHelper.getVolunteers();
    private ObservableList volNames;
    private final Map<String, Integer> volMap = observableMap(new HashMap<>());
    private final ObservableList<User> users = UserHelper.getUsers();
    private ObservableList userNames;
    private final Map<String, Integer> userMap = observableMap(new HashMap<>());


    @FXML private GridPane gpEditAppointment;
    @FXML private TableColumn<?, ?> colId;
    @FXML private TableColumn<?, ?> colTitle;
    @FXML private TableColumn<?, ?> colDesc;
    @FXML private TableColumn<?, ?> colLoc;
    @FXML private TableColumn<?, ?> colType;
    @FXML private TableColumn<Appointment, Timestamp> colStart;
    @FXML private TableColumn<Appointment, Timestamp> colEnd;
    @FXML private TableColumn<?, ?> colCust;
    @FXML private TableColumn<?, ?> colUser;
    @FXML private TableColumn<?, ?> colCon;
    @FXML private TextField txtQuery;
    @FXML private TextField txtApptID;
    @FXML private TextField txtApptTitle;
    @FXML private TextField txtApptDesc;
    @FXML private TextField txtApptLoc;
    @FXML private TextField txtApptType;
    @FXML private DatePicker dpApptStart;
    @FXML private DatePicker dpApptEnd;
    @FXML private Spinner spnStartHour;
    @FXML private Spinner spnStartMinute;
    @FXML private RadioButton rbStartAm;
    @FXML private RadioButton rbStartPm;
    @FXML private Spinner spnEndHour;
    @FXML private Spinner spnEndMinute;
    @FXML private RadioButton rbEndAm;
    @FXML private RadioButton rbEndPm;
    ToggleGroup tgStart = new ToggleGroup();
    ToggleGroup tgEnd = new ToggleGroup();

    @FXML private RadioButton rbWeek;
    @FXML private RadioButton rbMonth;
    @FXML private RadioButton rbAll;
    ToggleGroup tgAppFilter = new ToggleGroup();
    @FXML private ComboBox cbClientName;
    @FXML private ComboBox cbVolunteerName;
    @FXML private ComboBox cbUserName;
    @FXML private Button btnSaveApp;
    @FXML private Button btnSaveNewApp;
    @FXML private VBox vbMain;
    private Appointment selApp;

    public AppointmentsController() throws SQLException {
    }


    /**
     * <p>Initializes the data for the view</p>
     * <p>Sets the user variable as the logged in User</p>
     * <p>Binds the preferred width of the TableView to the width of the Stage</p>
     * @param user The logged in User
     * @param stage The Stage
     */
    public void initData(User user, Stage stage)
    {
        this.user = user;
        tvAppointments.prefWidthProperty().bind(stage.widthProperty());
    }

    /**
     * <p>Defines the actions to take place when the volrol is initialized</p>
     * <p>Sets the hour and minute Spinners' min and max values</p>
     * <p>Sets the ToggleGroup for the AM and PM Radio Buttons</p>
     * <p>Sets the volent and display of the TableView used for displaying appointments</p>
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        SpinnerValueFactory.IntegerSpinnerValueFactory svfh = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12,12);
        SpinnerValueFactory.IntegerSpinnerValueFactory svfm = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        SpinnerValueFactory.IntegerSpinnerValueFactory evfh = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12,12);
        SpinnerValueFactory.IntegerSpinnerValueFactory evfm = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        spnStartHour.setValueFactory(svfh);
        spnStartMinute.setValueFactory(svfm);
        spnEndHour.setValueFactory(evfh);
        spnEndMinute.setValueFactory(evfm);
        rbStartAm.setToggleGroup(tgStart);
        rbStartPm.setToggleGroup(tgStart);
        rbEndAm.setToggleGroup(tgEnd);
        rbEndPm.setToggleGroup(tgEnd);
        rbWeek.setToggleGroup(tgAppFilter);
        rbMonth.setToggleGroup(tgAppFilter);
        rbAll.setToggleGroup(tgAppFilter);
        rbAll.setSelected(true);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStart.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        colStart.setCellFactory(getCustomCellFactory());
        colEnd.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        colEnd.setCellFactory(getCustomCellFactory());
        colCust.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colCon.setCellValueFactory(new PropertyValueFactory<>("volName"));
        tvAppointments.setItems(appList);
        clientNames = observableArrayList();
        volNames = observableArrayList();
        userNames = observableArrayList();
        fillCollections();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
    }

    @FXML
    public void searchAppointments() throws SQLException {
        String query = txtQuery.getText();
        ObservableList<Appointment> results = AppointmentsHelper.searchAppointments(query);
        appList = results;
        refreshTableView("SEARCH");
        tvAppointments.refresh();
    }

    /**
     * <p>Gets the data from the input volrols and updates the selected Appointment</p>
     * @param actionEvent The event that triggered this method call - Save Button click
     * @throws ParseException
     * @throws SQLException
     */
    @FXML
    public void apptSaveButtonClick(ActionEvent actionEvent) throws ParseException, SQLException {
        // check the table's selected item and get selected item

        try {
            for(Node node : gpEditAppointment.getChildren())
            {
                if(isEmpty(node))
                {
                    throw new NullPointerException("All fields are required.");

                }
            }
            selApp.setTitle(txtApptTitle.getText());
            selApp.setDescription(txtApptDesc.getText());
            selApp.setType(txtApptType.getText());
            selApp.setLocation(txtApptLoc.getText());
            int shour = (Integer) spnStartHour.getValue() == 12 ? 0 : (Integer) spnStartHour.getValue();
            int ehour = (Integer) spnEndHour.getValue() == 12 ? 0 : (Integer) spnEndHour.getValue();
            shour = tgStart.getSelectedToggle().equals(rbStartAm) ? shour : shour + 12;
            ehour = tgEnd.getSelectedToggle().equals(rbEndAm) ? ehour : ehour + 12;

            String startDateString = DateTimeHelper.buildDateString(String.valueOf(String.format("%02d", dpApptStart.getValue().getYear())), String.valueOf(String.format("%02d", dpApptStart.getValue().getMonthValue())), String.valueOf(String.format("%02d", dpApptStart.getValue().getDayOfMonth())), String.valueOf(String.format("%02d", shour)), String.valueOf(String.format("%02d", spnStartMinute.getValue())));

            Date newStartDate = DateTimeHelper.getDateFormattedUTC(startDateString);
            Timestamp sts = DateTimeHelper.localtoUtc(DateTimeHelper.getDateFormattedUTC(startDateString));

            String endDateString = DateTimeHelper.buildDateString(String.valueOf(String.format("%02d", dpApptEnd.getValue().getYear())), String.valueOf(String.format("%02d", dpApptEnd.getValue().getMonthValue())), String.valueOf(String.format("%02d", dpApptEnd.getValue().getDayOfMonth())), String.valueOf(String.format("%02d", ehour)), String.valueOf(String.format("%02d", spnEndMinute.getValue())));
            Timestamp ets = DateTimeHelper.localtoUtc(DateTimeHelper.getDateFormattedUTC(endDateString));

            Date newEndDate = DateTimeHelper.getDateFormattedUTC(endDateString);

            selApp.setStart(sts);
            selApp.setEnd(ets);
            selApp.setUserId(userMap.get(cbUserName.getValue()));
            selApp.setClientId(clientMap.get(cbClientName.getValue()));
            selApp.setVolunteerId(volMap.get(cbVolunteerName.getValue()));
            selApp.setUpdatedBy(user.getName());
            selApp.setUpdatedDate(DateTimeHelper.localtoUtc(Timestamp.valueOf(LocalDateTime.now())));
            System.out.println("Appointment Times");
            System.out.print("Start: ");
            System.out.println(selApp.getStart());
            System.out.print("End: ");
            System.out.println(selApp.getEnd());
            if (DateTimeHelper.officeIsOpen(DateTimeHelper.utcToLocal(selApp.getStart()))) {
                AppointmentsHelper.updateAppointment(selApp);
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Bad Appointment Start Time");
                errorAlert.setContentText("Appointment start time must be between 8am and 10pm Eastern.");
                errorAlert.showAndWait();
                System.out.println("OFFICE IS CLOSED!");
            }

            refreshTableView("ALL");
            tvAppointments.refresh();
        }
        catch (NullPointerException e)
        {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("All Fields Required");
            error.setContentText("Please fill out all fields and try again.");
            error.show();
        }
    }

    /**
     * <p>Gets the data from the input volrols and creates a new Appointment</p>
     * @param actionEvent The event that triggered this method call - Save New Appointment Button clicked
     * @throws SQLException
     * @throws ParseException
     */
    public void apptSaveNewButtonClick(ActionEvent actionEvent) throws SQLException, ParseException {

        try {
            for (Node node : gpEditAppointment.getChildren()) {
                if (isEmpty(node) && !node.getId().equals(txtApptID.getId()))
                {
                    throw new NullPointerException("All fields are required.");
                }
            }

            String title = txtApptTitle.getText();
            String description = txtApptDesc.getText();
            String type = txtApptType.getText();
            String location = txtApptLoc.getText();
            int shour = (Integer) spnStartHour.getValue() == 12 ? 0 : (Integer) spnStartHour.getValue();
            int ehour = (Integer) spnEndHour.getValue() == 12 ? 0 : (Integer) spnEndHour.getValue();
            shour = tgStart.getSelectedToggle() == rbStartAm ? shour : shour + 12;
            ehour = tgEnd.getSelectedToggle() == rbEndAm ? ehour : ehour + 12;
            String startDateString = DateTimeHelper.buildDateString(String.valueOf(String.format("%02d", dpApptStart.getValue().getYear())), String.valueOf(String.format("%02d", dpApptStart.getValue().getMonthValue())), String.valueOf(String.format("%02d", dpApptStart.getValue().getDayOfMonth())), String.valueOf(String.format("%02d", shour)), String.valueOf(String.format("%02d", spnStartMinute.getValue())));
            Timestamp sts = DateTimeHelper.localtoUtc(DateTimeHelper.getDateFormattedUTC(startDateString));
            String endDateString = DateTimeHelper.buildDateString(String.valueOf(String.format("%02d", dpApptEnd.getValue().getYear())), String.valueOf(String.format("%02d", dpApptEnd.getValue().getMonthValue())), String.valueOf(String.format("%02d", dpApptEnd.getValue().getDayOfMonth())), String.valueOf(String.format("%02d", ehour)), String.valueOf(String.format("%02d", spnEndMinute.getValue())));
            Timestamp ets = DateTimeHelper.localtoUtc(DateTimeHelper.getDateFormattedUTC(endDateString));
            int userId = userMap.get(cbUserName.getValue());
            int client = clientMap.get(cbClientName.getValue());
            int vol = volMap.get(cbVolunteerName.getValue());
            String createdByBy = user.getName();
            Timestamp createdDate = DateTimeHelper.localtoUtc(Timestamp.valueOf(LocalDateTime.now()));

            Appointment app = new Appointment(title, description, location, type, sts, ets, createdByBy, createdDate, createdByBy, createdDate, client, userId, vol);
            System.out.println("Appointment Times");
            System.out.print("Start: ");
            System.out.println(app.getStart());
            System.out.print("End: ");
            System.out.println(app.getEnd());
            if (DateTimeHelper.officeIsOpen(DateTimeHelper.utcToLocal(app.getStart()))) {
                AppointmentsHelper.createAppointment(app);
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Bad Appointment Start Time");
                errorAlert.setContentText("Appointent start time must be between 8am and 10pm Eastern.");
                errorAlert.showAndWait();
                System.out.println("OFFICE IS CLOSED!");
            }
            refreshTableView("ALL");
            tvAppointments.refresh();
        }
        catch (NullPointerException e)
        {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("All Fields Required");
            error.setContentText("Please fill out all fields and try again.");
            error.show();
        }
    }

    /**
     * <p>Gets the selected Appointment from the TableView and fills edit fields with data from the selected Appointment</p>
     * <p>If nothing is selected in the TableView, this does nothing</p>
     * @param actionEvent The event that triggered this method call - Edit Button click
     * @throws SQLException
     * @throws ParseException
     */
    public void onEditClick(ActionEvent actionEvent) throws SQLException, ParseException {
        if (tvAppointments.getSelectionModel().getSelectedItem() != null)
        {
            ObservableList cells = tvAppointments.getSelectionModel().getSelectedCells();

            btnSaveNewApp.setVisible(false);
            btnSaveApp.setVisible(true);
            gpEditAppointment.setVisible(true);

            Appointment selectedAppointment = tvAppointments.getSelectionModel().getSelectedItem();
            selApp = selectedAppointment;
            int aid = selApp.getId();
            String atitle = selApp.getTitle();
            String adesc = selApp.getDescription();
            String aloc = selApp.getLocation();
            String atype = selApp.getType();
            Timestamp astart = selApp.getStart();

            LocalDateTime sdt = astart.toLocalDateTime();
            LocalDate sDate = sdt.toLocalDate();
            int shour = sdt.getHour();
            if(shour >= 12)
            {
                rbStartPm.setSelected(true);
                rbStartAm.setSelected(false);
            }
            else
            {
                rbStartPm.setSelected(false);
                rbStartAm.setSelected(true);
            }
            if(shour >12)
            {
                shour = shour - 12;
            }
            else if (shour == 0)
            {
                shour = shour + 12;
            }
            int sminute = astart.getMinutes();
            Timestamp aend = selApp.getEnd();

            LocalDateTime edt = aend.toLocalDateTime();
            LocalDate eDate = edt.toLocalDate();
            int ehour = edt.getHour();
            if(ehour >= 12)
            {
                rbEndAm.setSelected(false);
                rbEndPm.setSelected(true);
            }
            else
            {
                rbEndAm.setSelected(true);
                rbEndPm.setSelected(false);
            }
            if(ehour >12)
            {
                ehour = ehour - 12;
            }
            else if (ehour == 0)
            {
                ehour = ehour + 12;
            }
            int eminute = edt.getMinute();
            String aclient = selApp.getClientName();
            String auser = selApp.getUserName();
            String acon = selApp.getVolName();
            txtApptID.setText(String.valueOf(aid));
            txtApptTitle.setText(atitle);
            txtApptDesc.setText(adesc);
            txtApptLoc.setText(aloc);
            txtApptType.setText(atype);
            dpApptStart.setValue(sDate);
            dpApptEnd.setValue(eDate);
            spnStartHour.getValueFactory().setValue(shour);
            spnEndHour.getValueFactory().setValue(ehour);
            spnStartMinute.getValueFactory().setValue(sminute);
            spnEndMinute.getValueFactory().setValue(eminute);
            cbClientName.setValue(aclient);
            cbVolunteerName.setValue(acon);
            cbUserName.setValue(auser);


            refreshTableView("ALL");
            tvAppointments.refresh();
        }
    }

    /**
     * Displays the fields for creating a new Appointment
     * @param actionEvent Create Appointment Button clicked
     */
    @FXML
    public void onCreateClick(ActionEvent actionEvent)
    {
        btnSaveNewApp.setVisible(true);
        btnSaveApp.setVisible(false);
        gpEditAppointment.setVisible(true);
    }

    /**
     * <p>Fills the Collections used for Clients, Volunteers, and Users</p>
     * <p>Once the Collections are filled, the related ComboBoxes are filled with options</p>
     * <p>Lambda Expression for filling clientMap and clientNames collections</p>
     * <p>Lambda Expression for filling volMap and volNames collections</p>
     * <p>Lambda Expression for filling userMap and userNames collections</p>
     */
    private void fillCollections()
    {
        clients.forEach((Client c) -> {clientMap.put(c.getName(), c.getId()); clientNames.add(c.getName());});
        vols.forEach((Volunteer v) -> {volMap.put(v.getName(), v.getId()); volNames.add(v.getName());});
        users.forEach((User u) -> {userMap.put(u.getName(), u.getId()); userNames.add(u.getName());});

        cbClientName.setItems(clientNames);
        cbVolunteerName.setItems(volNames);
        cbUserName.setItems(userNames);
    }

    /**
     * Clears the input fields and hides the edit volainer
     * @param actionEvent Cancel Button clicked
     */
    @FXML
    private void onCancelClick(ActionEvent actionEvent)
    {
        txtApptTitle.clear();
        txtApptLoc.clear();
        txtApptType.clear();
        txtApptDesc.clear();
        txtApptID.clear();
        dpApptStart.setValue(LocalDate.now());
        dpApptEnd.setValue(LocalDate.now());
        spnStartMinute.getValueFactory().setValue(0);
        spnStartHour.getValueFactory().setValue(1);
        spnEndMinute.getValueFactory().setValue(0);
        spnStartHour.getValueFactory().setValue(1);
        tgStart.selectToggle(rbStartAm);
        tgEnd.selectToggle(rbEndAm);
        cbVolunteerName.setValue("");
        cbClientName.setValue("");
        cbUserName.setValue(user.getName());
        gpEditAppointment.setVisible(false);
    }

    /**
     * <p>Displays a confirmation Alert to the user asking if they are sure they want to delete the selected Appointment</p>
     * <p>If no Appointment is selected, this does nothing</p>
     * @param actionEvent Delete Button clicked
     * @throws SQLException
     */
    @FXML
    private void onDeleteClick(ActionEvent actionEvent) throws SQLException {
        if (tvAppointments.getSelectionModel().getSelectedItem() != null)
        {
            Appointment selectedAppointment = tvAppointments.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            StringBuilder sb = new StringBuilder();
            sb.append("Are you sure you want to delete this appointment?\n");
            sb.append("Appointment ID: ");
            sb.append(selectedAppointment.getId());
            sb.append("\nTitle: ");
            sb.append(selectedAppointment.getTitle());
            sb.append("\nVolunteer: ");
            sb.append(selectedAppointment.getVolName());
            sb.append("\nType: ");
            sb.append(selectedAppointment.getType());
            alert.setContentText(String.valueOf(sb));
            alert.setHeaderText("Delete Appointment?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK)
            {
                onDeleteConfirmed(selectedAppointment);
            }
        }
    }

    /**
     * <p>Deletes the provided Appointment</p>
     * <p>Displays a notice to the user that the Appointment was deleted</p>
     * <p>Calls refreshTableView to updated the display</p>
     * @param selectedAppointment The Appointment to delete
     * @throws SQLException
     */
    @FXML
    private void onDeleteConfirmed(Appointment selectedAppointment) throws SQLException {

        AppointmentsHelper.deleteAppointment(selectedAppointment);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Deleted");
        alert.setHeaderText("Appointment Deleted");
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment ID: ");
        sb.append(selectedAppointment.getId());
        sb.append("\nTitle: ");
        sb.append(selectedAppointment.getTitle());
        sb.append("\nVolunteer: ");
        sb.append(selectedAppointment.getVolName());
        sb.append("\nType: ");
        sb.append(selectedAppointment.getType());
        alert.setContentText(String.valueOf(sb));
        alert.show();
        refreshTableView("ALL");
        tvAppointments.refresh();

    }

    /**
     * <p>Refreshes the TableView with the updated list of Appointments after applying the filter</p>
     * @param filter String used for the filter to apply when getting the Appointments to display
     * @throws SQLException
     */
    private void refreshTableView(String filter) throws SQLException {
        switch(filter)
        {
            case "ALL":
                appList = AppointmentsHelper.getAppointments();
                break;
            case "MONTH":
                appList = AppointmentsHelper.getMonthFilteredAppointments();
                break;
            case "WEEK":
                appList = AppointmentsHelper.getWeekFilteredAppointments();
                break;
            case "SEARCH":
                break;
            default:
                appList = AppointmentsHelper.getAppointments();
                break;
        }

        tvAppointments.getItems().clear();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStart.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        colEnd.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        colCust.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colCon.setCellValueFactory(new PropertyValueFactory<>("volName"));
        tvAppointments.setItems(appList);
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
     * Filters the displayed Appointments by month, week, or all
     * @param actionEvent One of the filtering Buttons was clicked
     * @throws SQLException
     */
    @FXML
    private void filterAppointments(ActionEvent actionEvent) throws SQLException {
        String filter;
        if(tgAppFilter.getSelectedToggle().equals(rbWeek))
        {
            filter = "WEEK";
        }
        else if(tgAppFilter.getSelectedToggle().equals(rbMonth))
        {
            filter = "MONTH";
        }
        else
        {
            filter = "ALL";
        }

        refreshTableView(filter);
        tvAppointments.refresh();
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
        DashboardController volroller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        volroller.initData(user, window);
        window.setTitle("Hello!");
        window.setScene(dashboardScene);
        window.show();
    }

    /**
     * Checks if the value of the given Node is null or an empty String
     * @param node The node to check
     * @return true if the Node is null or the node value is either an empty String or null
     */
    @FXML
    private boolean isEmpty(Node node)
    {
        boolean isEmpty = false;
        if(node instanceof Label)
        {

        }
        else
        {
            if (node instanceof DatePicker) {
                if (((DatePicker) node).getValue() == null) {
                    isEmpty = true;
                }
            }
            if (node instanceof TextField) {
                if (((TextField) node).getText() == "") {
                    isEmpty = true;
                }
            }
            if (node instanceof ComboBox<?>) {
                if (((ComboBox) node).getValue() == null) {
                    isEmpty = true;
                }
            }
            if (node instanceof RadioButton) {
                if (((RadioButton) node).getToggleGroup().getSelectedToggle() == null) {
                    isEmpty = true;
                }
            }
            if (node == null) {
                isEmpty = true;
            }

        }
        return isEmpty;
    }
}
