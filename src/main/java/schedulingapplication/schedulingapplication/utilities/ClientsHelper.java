package schedulingapplication.schedulingapplication.utilities;

import javafx.collections.ObservableList;
import schedulingapplication.schedulingapplication.models.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Utility class for handling Client specific functions
 */
public abstract class ClientsHelper
{
    /**
     * Get all clients from the clients table
     * @return An ObservableList of Clients
     * @throws SQLException
     */
    public static ObservableList<Client> getClients() throws SQLException {
        String sql = "SELECT c.Client_ID, c.Client_Name, c.Address, c.Postal_Code, c.Phone, c.Last_Update, c.Last_Updated_By, c.Division_ID, d.Division, co.Country_ID, co.Country FROM clients AS c JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID JOIN countries AS co ON d.Country_ID = co.Country_ID ORDER BY c.Client_ID ASC";
        Query.queryDb(sql);
        ResultSet clients = Query.getResults();
        ObservableList<Client> clist = observableArrayList();
        while (clients.next())
        {
            Integer cid = clients.getInt("Client_ID");
            String name = clients.getString("Client_Name");
            String address = clients.getString("Address");
            String zip = clients.getString("Postal_Code");
            String phone = clients.getString("Phone");
            Integer div = clients.getInt("Division_ID");
            String divName = clients.getString("Division");
            int countryId = clients.getInt("Country_ID");
            String country = clients.getString("Country");
            String updatedBy = clients.getString("Last_Updated_By");
            Timestamp lastUpdated = DateTimeHelper.utcToLocal(clients.getTimestamp("Last_Update"));
            Client c = new Client(cid, name, address, zip, phone, div, divName, countryId, country, updatedBy, lastUpdated);
            clist.add(c);
        }
        return clist;
    }

    /**
     * Update a client in the clients table
     * @param c The Client to update in the clients table
     * @throws SQLException
     */
    public static void updateClient(Client c) throws SQLException {
        StringBuilder sqlUpdate = new StringBuilder();
        sqlUpdate.append("UPDATE clients SET Client_Name = '");
        sqlUpdate.append(c.getName());
        sqlUpdate.append("', Address = '");
        sqlUpdate.append(c.getAddress());
        sqlUpdate.append("', Postal_Code = '");
        sqlUpdate.append(c.getZip());
        sqlUpdate.append("', Phone = '");
        sqlUpdate.append(c.getPhone());
        sqlUpdate.append("', Division_ID = '");
        sqlUpdate.append(c.getDivId());
        sqlUpdate.append("', Last_Update = '");
        sqlUpdate.append(c.getUpdatedDate());
        sqlUpdate.append("', Last_Updated_By = '");
        sqlUpdate.append(c.getUpdatedBy());
        sqlUpdate.append("' WHERE Client_ID = ");
        sqlUpdate.append(c.getId());
        Query.queryDb(sqlUpdate.toString());

    }

    /**
     * Insert a client into the clients table
     * @param c The Client to add to the clients table
     * @throws SQLException
     */
    public static void addClient(Client c) throws SQLException {
        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append("INSERT INTO clients (Client_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES('");
        sqlInsert.append(c.getName());
        sqlInsert.append("', '");
        sqlInsert.append(c.getAddress());
        sqlInsert.append("', '");
        sqlInsert.append(c.getZip());
        sqlInsert.append("', '");
        sqlInsert.append(c.getPhone());
        sqlInsert.append("', '");
        sqlInsert.append(c.getCreatedDate());
        sqlInsert.append("', '");
        sqlInsert.append(c.getCreatedBy());
        sqlInsert.append("', '");
        sqlInsert.append(c.getUpdatedDate());
        sqlInsert.append("', '");
        sqlInsert.append(c.getUpdatedBy());
        sqlInsert.append("', '");
        sqlInsert.append(c.getDivId());
        sqlInsert.append("')");

        Query.queryDb(sqlInsert.toString());
    }

    /**
     * Delete a client from the clients table
     * @param c The client to delete
     * @throws SQLException
     */
    public static void deleteClient(Client c) throws SQLException {

        AppointmentsHelper.deleteAppointments(c);
        StringBuilder clientDelete = new StringBuilder();
        clientDelete.append("DELETE FROM clients WHERE Client_ID = ");
        clientDelete.append(c.getId());
        try
        {
            Query.queryDb(clientDelete.toString());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}