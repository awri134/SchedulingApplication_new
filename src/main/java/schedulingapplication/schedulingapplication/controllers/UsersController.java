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
import schedulingapplication.schedulingapplication.models.User;
import schedulingapplication.schedulingapplication.models.Volunteer;
import schedulingapplication.schedulingapplication.utilities.UserHelper;
import schedulingapplication.schedulingapplication.utilities.VolunteersHelper;
import schedulingapplication.schedulingapplication.SchedulingApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML
    private TableView<User> tvUsers;
    ObservableList<User> userList = UserHelper.getUsers();
    User user;
    @FXML
    TableColumn<User, Integer> idCol;
    @FXML TableColumn<User, String> nameCol;

    @FXML
    TextField txtUserID;
    @FXML
    TextField txtUsername;
    @FXML
    TextField txtPass;
    @FXML
    GridPane paneEdit;
    @FXML
    Button btnSave;
    @FXML Button btnSaveNew;
    @FXML TextField txtQuery;

    private User selUser;

    public UsersController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvUsers.setItems(userList);
    }


    public void onEditClick(ActionEvent actionEvent)
    {
        if(user.getName() == "admin")
        {

            if (tvUsers.getSelectionModel().getSelectedItem() != null)
            {
                paneEdit.setVisible(true);
                ObservableList cells = tvUsers.getSelectionModel().getSelectedCells();
                //System.out.println(cells.toString());
                btnSaveNew.setVisible(false);
                btnSave.setVisible(true);
                paneEdit.setVisible(true);
                //paneEditClient.getItems();
                User selectedUser = tvUsers.getSelectionModel().getSelectedItem();
                selUser = selectedUser;
                int uid = selUser.getId();
                String uname = selUser.getName();
                String upass = selUser.getPass();
                txtUserID.setText(String.valueOf(uid));
                txtUsername.setText(uname);
                txtPass.setText(upass);
                //cbCountry.setItems((ObservableList) countriesMap);
            }
        }
        else
        {
            UserHelper.notAdminAlert();
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
        txtUserID.clear();
        txtUsername.clear();
        txtPass.clear();
    }


    /**
     * Updates and saves the selected Client using the data from the input fields
     * @throws SQLException
     */
    public void userSaveButtonClick() throws SQLException
    {
        if(user.getName() == "admin")
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
                selUser.setName(txtUsername.getText());
                selUser.setPass(txtPass.getText());
                UserHelper.updateUser(selUser, user);
                userList.removeAll(userList);
                //FXCollections.copy(custList, Client.customers);
                userList = UserHelper.getUsers();
                refreshTableView();
                tvUsers.refresh();
            }
            catch (NullPointerException e)
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("All Fields Required");
                error.setContentText("Please fill out all fields and try again.");
                error.show();
            }
        }
        else
        {
            UserHelper.notAdminAlert();
        }
    }

    /**
     * Creates and saves a new Client using the data from the input fields
     * @throws SQLException
     */
    public void userSaveNewButtonClick() throws SQLException {
        if(user.getName() == "admin")
        {
            try
            {
                for (Node node : paneEdit.getChildren()) {
                    if (isEmpty(node) && !node.getId().equals(txtUserID.getId()))
                    {
                        throw new NullPointerException("All fields are required.");
                    }
                }
                String uname = txtUsername.getText();
                String upass = txtPass.getText();
                User newUser = new User(uname, upass);
                try {
                    UserHelper.addUser(newUser, user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                userList.removeAll(userList);
                //FXCollections.copy(custList, Client.customers);
                userList = UserHelper.getUsers();
                refreshTableView();
                tvUsers.refresh();
            }
            catch (NullPointerException e)
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("All Fields Required");
                error.setContentText("Please fill out all fields and try again.");
                error.show();
            }
        }
        else
        {
            UserHelper.notAdminAlert();
        }
    }

    /**
     * Deletes the selected Client
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    private void onDeleteClick(ActionEvent actionEvent) throws SQLException {
        if(user.getName() == "admin")
        {
            if (tvUsers.getSelectionModel().getSelectedItem() != null)
            {
                User selectedUser = tvUsers.getSelectionModel().getSelectedItem();
                UserHelper.deleteUser(selectedUser, user);
                refreshTableView();
                tvUsers.refresh();
            }
        }
        else
        {
            UserHelper.notAdminAlert();
        }
    }

    /**
     * Gets the Clients from the database and fills the TableView with the updated data
     * @throws SQLException
     */
    private void refreshTableView() throws SQLException {
        tvUsers.getItems().clear();
        userList = UserHelper.getUsers();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvUsers.setItems(userList);
    }

    /**
     * Clears the input fields and hides them
     * @param actionEvent
     */
    @FXML
    private void onCancelClick(ActionEvent actionEvent)
    {
        paneEdit.setVisible(false);
        txtUserID.clear();
        txtUsername.clear();
        txtPass.clear();
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
    public void searchUsers() throws SQLException {
        System.out.println("SEARCH BUTTON CLICKED!");
        String query = txtQuery.getText();
        //ObservableList<Volunteer> results = VolunteersHelper.searchVolunteers(query);


        tvUsers.getItems().clear();
        userList = UserHelper.searchUsers(query);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvUsers.setItems(userList);
        tvUsers.refresh();
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
