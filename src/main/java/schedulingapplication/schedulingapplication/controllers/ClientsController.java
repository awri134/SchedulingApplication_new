package schedulingapplication.schedulingapplication.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import schedulingapplication.schedulingapplication.models.Client;
import schedulingapplication.schedulingapplication.models.User;
import schedulingapplication.schedulingapplication.utilities.ClientsHelper;
import java.sql.SQLException;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import schedulingapplication.schedulingapplication.SchedulingApplication;
import schedulingapplication.schedulingapplication.utilities.DateTimeHelper;
import schedulingapplication.schedulingapplication.utilities.Query;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableMap;





/**
 * Controller for the Clients view
 */
public class ClientsController implements Initializable
{
    User user;
    @FXML
    private TableView<Client> tvCust;
    private ObservableList<Client> custList = ClientsHelper.getClients();
    private final Map<String, Integer> countriesMap = observableMap(new HashMap<>());
    private final ObservableList countries = getCountries();
    private ObservableList divisions;
    private final Map<String, Integer> divMap = observableMap(new HashMap<>());
    @FXML private TableColumn<Client, Integer> idCol;
    @FXML private TableColumn<Client, String> nameCol;
    @FXML private TableColumn<Client, String> addCol;
    @FXML private TableColumn<Client, String> zipCol;
    @FXML private TableColumn<Client, String> phoneCol;
    //@FXML private TableColumn<Client, Integer> divIdCol;
    @FXML private TableColumn<Client, Integer> divNameCol;
    @FXML private TableColumn<Client, Integer> countryCol;
    @FXML private TableColumn<Client, Integer> updatedByCol;
    @FXML private TableColumn<Client, Timestamp> updatedDateCol;
    @FXML private GridPane paneEditClient;
    @FXML private TextField txtCustID;
    @FXML private TextField txtCustName;
    @FXML private TextField txtCustAddress;
    @FXML private TextField txtCustZip;
    @FXML private TextField txtCustPhone;
    @FXML private ComboBox cbCountry;
    @FXML private ComboBox cbDivisions;
    @FXML private Button btnSaveCust;
    @FXML private Button btnSaveNewCust;
    private Client selCust;


    @FXML
    ButtonBar btnbarModify;
    @FXML
    ButtonBar btnbarBack;


    public ClientsController() throws SQLException {
    }

    /**
     * Initializes the data for the Clients Controller
     * @param user The logged in User
     * @param stage
     */
    public void initData(User user, Stage stage)
    {
        this.user = user;
        stage.setMaxWidth(stage.getWidth()-50);
        tvCust.prefWidthProperty().bind(stage.widthProperty());
        btnbarModify.prefWidthProperty().bind(stage.widthProperty());
        btnbarBack.prefWidthProperty().bind(stage.widthProperty());
        tvCust.setMaxWidth(stage.getWidth()-50);
    }

    /**
     * <p>Defines the actions to take place when the control is initialized</p>
     * 5<p>Sets the content and display of the TableView used for displaying Clients</p>
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        //divIdCol.setCellValueFactory(new PropertyValueFactory<>("divId"));
        divNameCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
        updatedDateCol.setCellValueFactory(new PropertyValueFactory<>("updatedDate"));
        updatedDateCol.setCellFactory(getCustomCellFactory());
        idCol.prefWidthProperty().bind(tvCust.widthProperty().divide(12)); // w * 1/12
        nameCol.prefWidthProperty().bind(tvCust.widthProperty().divide(6)); // w * 2/12
        addCol.prefWidthProperty().bind(tvCust.widthProperty().divide(6)); // w * 2/12
        zipCol.prefWidthProperty().bind(tvCust.widthProperty().divide(12)); // w * 1/12
        phoneCol.prefWidthProperty().bind(tvCust.widthProperty().divide(12)); // w * 1/12
        divNameCol.prefWidthProperty().bind(tvCust.widthProperty().divide(12)); // w * 1/12
        countryCol.prefWidthProperty().bind(tvCust.widthProperty().divide(12)); // w * 1/12
        updatedByCol.prefWidthProperty().bind(tvCust.widthProperty().divide(12)); // w * 1/12
        updatedDateCol.prefWidthProperty().bind(tvCust.widthProperty().divide(6)); // w * 2/12
        tvCust.setItems(custList);

    }

    /**
     * <p>Gets the selected Appointment from the TableView and fills edit fields with data from the selected Appointment</p>
     * <p>If nothing is selected in the TableView, this does nothing</p>
     * @param actionEvent The event that triggered this method call - Edit Button click
     * @param actionEvent
     * @throws SQLException
     */
    public void onEditClick(ActionEvent actionEvent) throws SQLException {
        if (tvCust.getSelectionModel().getSelectedItem() != null)
        {
            ObservableList cells = tvCust.getSelectionModel().getSelectedCells();
            //System.out.println(cells.toString());
            btnSaveNewCust.setVisible(false);
            btnSaveCust.setVisible(true);
            paneEditClient.setVisible(true);
            //paneEditClient.getItems();
            Client selectedClient = tvCust.getSelectionModel().getSelectedItem();
            selCust = selectedClient;
            int cid = selCust.getId();
            String cname = selCust.getName();
            String caddress = selCust.getAddress();
            String czip = selCust.getZip();
            String cphone = selCust.getPhone();
            String cCountry = selCust.getCountry();
            String cDiv = selCust.getDivision();
            //int cdivid = selCust.getDivId();
            txtCustID.setText(String.valueOf(selCust.getId()));
            txtCustName.setText(cname);
            txtCustAddress.setText(caddress);
            txtCustZip.setText(czip);
            txtCustPhone.setText(cphone);
            cbCountry.setItems(countries);
            cbCountry.setValue(cCountry);
            getDivisions(countriesMap.get(cCountry));
            cbDivisions.setItems(divisions);
            cbDivisions.setValue(cDiv);
            //cbCountry.setItems((ObservableList) countriesMap);
        }
    }

    /**
     * Geets the selected country and fills the lower level division ComboBox
     * @throws SQLException
     */
    public void onCountrySelected() throws SQLException {
        String selCountry = (String) cbCountry.getValue();
        int countryCode = countriesMap.get(selCountry);
        getDivisions(countryCode);
        cbDivisions.setItems(divisions);
    }

    /**
     * Clears the Client edit/add fields
     * Displays the fields for creating a Client
     * @param event
     * @throws SQLException
     */
    public void onAddClientClick(ActionEvent event) throws SQLException {
        btnSaveNewCust.setVisible(true);
        btnSaveCust.setVisible(false);
        paneEditClient.setVisible(true);
        cbCountry.setItems(countries);
        txtCustName.clear();
        txtCustPhone.clear();
        txtCustAddress.clear();
        txtCustZip.clear();
    }


    /**
     * Updates and saves the selected Client using the data from the input fields
     * @throws SQLException
     */
    public void custSaveButtonClick() throws SQLException
    {
        try
        {
            for (Node node : paneEditClient.getChildren()) {
                if (isEmpty(node))
                {
                    throw new NullPointerException("All fields are required.");
                }
            }
            // check the table's selected item and get selected item
            selCust.setName(txtCustName.getText());
            selCust.setAddress(txtCustAddress.getText());
            selCust.setZip(txtCustZip.getText());
            selCust.setPhone(txtCustPhone.getText());
            selCust.setCountryId(countriesMap.get(cbCountry.getValue()));
            selCust.setDivId(divMap.get(cbDivisions.getValue()));
            selCust.setUpdatedBy(user.getName());
            selCust.setUpdatedDate(DateTimeHelper.localtoUtc(Timestamp.valueOf(LocalDateTime.now())));
            ClientsHelper.updateClient(selCust);
            custList.removeAll(custList);
            //FXCollections.copy(custList, Client.customers);
            custList = ClientsHelper.getClients();
            refreshTableView();
            tvCust.refresh();
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
            for (Node node : paneEditClient.getChildren()) {
                if (isEmpty(node) && !node.getId().equals(txtCustID.getId()))
                {
                    throw new NullPointerException("All fields are required.");
                }
            }
            String cname = txtCustName.getText();
            String caddress = txtCustAddress.getText();
            String czip = txtCustZip.getText();
            String cphone = txtCustPhone.getText();
            String cCountry = (String) cbCountry.getValue();
            int cCountryId = countriesMap.get(cbCountry.getValue());
            int cDiv = divMap.get(cbDivisions.getValue());
            String cDivName = (String) cbDivisions.getValue();
            Timestamp created = DateTimeHelper.localtoUtc(Timestamp.valueOf(LocalDateTime.now()));
            Timestamp updated = DateTimeHelper.localtoUtc(Timestamp.valueOf(LocalDateTime.now()));
            String createdBy = user.getName();
            String updatedBy = user.getName();
            Client newCust = new Client(cname, caddress, czip, cphone, cDiv, cDivName, cCountryId, cCountry, updatedBy, updated, createdBy, created);
            try {
                ClientsHelper.addClient(newCust);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            custList.removeAll(custList);
            //FXCollections.copy(custList, Client.customers);
            custList = ClientsHelper.getClients();
            refreshTableView();
            tvCust.refresh();
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
        if (tvCust.getSelectionModel().getSelectedItem() != null)
        {
            Client selectedClient = tvCust.getSelectionModel().getSelectedItem();
            ClientsHelper.deleteClient(selectedClient);
            refreshTableView();
            tvCust.refresh();
        }
    }

    /**
     * Fills the divisions and divMap collections with the divisions matching the provided country id
     * @param countryCode The id ov the country
     * @throws SQLException
     */
    private void getDivisions(int countryCode) throws SQLException {
        StringBuilder divSQL = new StringBuilder();
        divSQL.append("SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = ");
        divSQL.append(countryCode);
        Query.queryDb(String.valueOf(divSQL));
        ResultSet rs = Query.getResults();
        ObservableList divs = observableArrayList();
        while(rs.next())
        {
            int divId = rs.getInt("Division_ID");
            String divName = rs.getString("Division");
            divs.add(divName);
            divMap.put(divName, divId);
        }
        divisions = divs;
    }

    /**
     * Gets a list of countries available from the countries table
     * @return An ObservableList of countries
     * @throws SQLException
     */
    private ObservableList getCountries() throws SQLException {
        ObservableList allCountries = observableArrayList();
        String countrySQL = "SELECT Country_ID, Country FROM countries";
        Query.queryDb(countrySQL);
        ResultSet rs = Query.getResults();
        while (rs.next())
        {
            Integer cid = rs.getInt("Country_ID");
            String name = rs.getString("Country");
            allCountries.add(name);
            countriesMap.put(name, cid);
        }
        return allCountries;
    }

    /**
     * Gets the Clients from the database and fills the TableView with the updated data
     * @throws SQLException
     */
    private void refreshTableView() throws SQLException {
        tvCust.getItems().clear();
        custList = ClientsHelper.getClients();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        //divIdCol.setCellValueFactory(new PropertyValueFactory<>("divId"));
        divNameCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
        updatedDateCol.setCellValueFactory(new PropertyValueFactory<>("updatedDate"));
        tvCust.setItems(custList);
    }

    /**
     * Clears the input fields and hides them
     * @param actionEvent
     */
    @FXML
    private void onCancelClick(ActionEvent actionEvent)
    {
        paneEditClient.setVisible(false);
        txtCustName.clear();
        txtCustPhone.clear();
        txtCustZip.clear();
        txtCustAddress.clear();
    }

    /**
     * Sets the display formatting of the TimeStamp object for the given column
     * @return The CallBack for displaying the correctly formatted TimeStamp
     */
    private Callback<TableColumn<Client, Timestamp>, TableCell<Client, Timestamp>> getCustomCellFactory()
    {
        return new Callback<TableColumn<Client, Timestamp>, TableCell<Client, Timestamp>>()
        {
            @Override
            public TableCell<Client, Timestamp> call(TableColumn<Client, Timestamp> appointmentTimestampTableColumn)
            {
                TableCell<Client, Timestamp> cell = new TableCell<Client, Timestamp>()
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
     * @param event
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
    /**
     * Checks if the value of the given Node is null or an empty String
     * @param node The node to check
     * @return true if the Node is null or the node value is either an empty String or null
     */
    @FXML
    private boolean isEmpty(Node node)
    {
        boolean isEmpty = false;
        System.out.print(node.getId());
        System.out.print(" : ");
        System.out.println(node);
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

