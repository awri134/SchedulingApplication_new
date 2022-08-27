package schedulingapplication.schedulingapplication.controllers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import schedulingapplication.schedulingapplication.SchedulingApplication;
import schedulingapplication.schedulingapplication.models.Appointment;
import schedulingapplication.schedulingapplication.models.Client;
import schedulingapplication.schedulingapplication.models.User;
import schedulingapplication.schedulingapplication.models.Volunteer;
import schedulingapplication.schedulingapplication.utilities.AppointmentsHelper;
import schedulingapplication.schedulingapplication.utilities.ClientsHelper;
import schedulingapplication.schedulingapplication.utilities.DateTimeHelper;
import schedulingapplication.schedulingapplication.utilities.VolunteersHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class VolunteersController implements Initializable {

    @FXML
    private TableView<Volunteer> tvVol;
    ObservableList<Volunteer> volList = Volunteer.getVolunteers();
    User user;
    @FXML
    TableColumn<Volunteer, Integer> idCol;
    @FXML TableColumn<Volunteer, String> nameCol;
    @FXML TableColumn<Volunteer, String> emailCol;
    @FXML TableColumn<Volunteer, String> skillCol;

    @FXML
    TextField txtVolID;
    @FXML
    TextField txtVolName;
    @FXML
    TextField txtVolEmail;
    @FXML
    TextField txtVolSkill;
    @FXML
    TextField txtVolQuote;
    @FXML
    GridPane paneEdit;
    @FXML Button btnSave;
    @FXML Button btnSaveNew;
    @FXML TextField txtQuery;

    private Volunteer selVol;

    public VolunteersController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        skillCol.setCellValueFactory(new PropertyValueFactory<>("skill"));
        tvVol.setItems(volList);
        paneEdit.setVisible(false);
    }


    public void onEditClick(ActionEvent actionEvent)
    {
        if (tvVol.getSelectionModel().getSelectedItem() != null)
        {
            paneEdit.setVisible(true);
            ObservableList cells = tvVol.getSelectionModel().getSelectedCells();
            //System.out.println(cells.toString());
            btnSaveNew.setVisible(false);
            btnSave.setVisible(true);
            paneEdit.setVisible(true);
            //paneEditClient.getItems();
            Volunteer selectedVolunteer = tvVol.getSelectionModel().getSelectedItem();
            selVol = selectedVolunteer;
            int vid = selVol.getId();
            String vname = selVol.getName();
            String vemail = selVol.getEmail();
            String vskill = selVol.getSkill();
            String vquote = selVol.getQuote();
            txtVolID.setText(String.valueOf(selVol.getId()));
            txtVolName.setText(vname);
            txtVolEmail.setText(vemail);
            txtVolSkill.setText(vskill);
            txtVolQuote.setText(vquote);
            //cbCountry.setItems((ObservableList) countriesMap);
        }
    }

    public void initData(User user, Stage window) {
        this.user = user;
    }

    /**
     * Clears the Client edit/add fields
     * Displays the fields for creating a Client
     * @param event
     * @throws SQLException
     */
    public void onCreateClick(ActionEvent event) throws SQLException {
        btnSaveNew.setVisible(true);
        btnSave.setVisible(false);
        paneEdit.setVisible(true);
        txtVolName.clear();
        txtVolEmail.clear();
        txtVolSkill.clear();
        txtVolQuote.clear();
    }

    public void showIntro()
    {
        if (tvVol.getSelectionModel().getSelectedItem() != null)
        {
            Volunteer selectedVolunteer = tvVol.getSelectionModel().getSelectedItem();
            selectedVolunteer.giveIntro();
        }
    }

    /**
     * Updates and saves the selected Client using the data from the input fields
     * @throws SQLException
     */
    public void custSaveButtonClick() throws SQLException
    {
        try
        {
            for (Node node : paneEdit.getChildren()) {
                if (isEmpty(node))
                {
                    throw new NullPointerException("All fields are required.");
                }
            }
            // check the table's selected item and get selected item
            selVol.setName(txtVolName.getText());
            selVol.setEmail(txtVolEmail.getText());
            selVol.setSkill(txtVolSkill.getText());
            selVol.setQuote(txtVolQuote.getText());
            VolunteersHelper.updateVolunteer(selVol);
            volList.removeAll(volList);
            //FXCollections.copy(custList, Client.customers);
            volList = VolunteersHelper.getVolunteers();
            refreshTableView();
            tvVol.refresh();
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
     * Creates and saves a new Client using the data from the input fields
     * @throws SQLException
     */
    public void custSaveNewButtonClick() throws SQLException {
        try
        {
            for (Node node : paneEdit.getChildren()) {
                if (isEmpty(node) && !node.getId().equals(txtVolID.getId()))
                {
                    throw new NullPointerException("All fields are required.");
                }
            }
            String vname = txtVolName.getText();
            String vemail = txtVolEmail.getText();
            String vskill = txtVolSkill.getText();
            String vquote = txtVolQuote.getText();
            Volunteer newVol = new Volunteer(vname, vemail, vskill, vquote);
            try {
                VolunteersHelper.addVolunteer(newVol);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            volList.removeAll(volList);
            //FXCollections.copy(custList, Client.customers);
            volList = VolunteersHelper.getVolunteers();
            refreshTableView();
            tvVol.refresh();
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
     * Deletes the selected Client
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    private void onDeleteClick(ActionEvent actionEvent) throws SQLException {
        if (tvVol.getSelectionModel().getSelectedItem() != null)
        {
            Volunteer selectedVolunteer = tvVol.getSelectionModel().getSelectedItem();
            VolunteersHelper.deleteVolunteer(selectedVolunteer);
            refreshTableView();
            tvVol.refresh();
        }
    }

    /**
     * Gets the Clients from the database and fills the TableView with the updated data
     * @throws SQLException
     */
    private void refreshTableView() throws SQLException {
        tvVol.getItems().clear();
        volList = VolunteersHelper.getVolunteers();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        skillCol.setCellValueFactory(new PropertyValueFactory<>("skill"));
        tvVol.setItems(volList);
    }

    /**
     * Clears the input fields and hides them
     * @param actionEvent
     */
    @FXML
    private void onCancelClick(ActionEvent actionEvent)
    {
        paneEdit.setVisible(false);
        txtVolName.clear();
        txtVolEmail.clear();
        txtVolSkill.clear();
        txtVolID.clear();
        txtVolQuote.clear();
    }

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

    @FXML
    public void searchVolunteers() throws SQLException {
        System.out.println("SEARCH BUTTON CLICKED!");
        String query = txtQuery.getText();
        //ObservableList<Volunteer> results = VolunteersHelper.searchVolunteers(query);


        tvVol.getItems().clear();
        volList = VolunteersHelper.searchVolunteers(query);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        skillCol.setCellValueFactory(new PropertyValueFactory<>("skill"));
        tvVol.setItems(volList);
        tvVol.refresh();
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
        /*System.out.print(node.getId());
        System.out.print(" : ");
        System.out.println(node);*/
        if(node instanceof Label)
        {

        }
        else {
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
