package schedulingapplication.schedulingapplication.utilities;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import schedulingapplication.schedulingapplication.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Utility class to handle User specific functions
 */
public abstract class UserHelper
{
    /**
     * This method gets a list of users from the database table
     * @return an ObservableList of Users from the users table
     * @throws SQLException
     */
    public static ObservableList<User> getUsers() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY User_ID ASC";
        Query.queryDb(sql);
        ResultSet users = Query.getResults();
        ObservableList<User> ulist = observableArrayList();
        while (users.next())
        {
            Integer uid = users.getInt("User_ID");
            String uname = users.getString("User_Name");
            String upass = users.getString("Password");
            User u = new User(uid, uname, upass);
            ulist.add(u);
        }
        return ulist;
    }


    public static void updateUser(User u, User currentUser) throws SQLException {
        if(currentUser.getName() == "admin")
        {
            StringBuilder sqlUpdate = new StringBuilder();
            sqlUpdate.append("UPDATE users SET User_Name = '");
            sqlUpdate.append(u.getName());
            sqlUpdate.append("', Password = '");
            sqlUpdate.append(u.getPass());
            sqlUpdate.append("' WHERE User_ID = ");
            sqlUpdate.append(u.getId());
            Query.queryDb(sqlUpdate.toString());

        }
        else
        {
            notAdminAlert();
        }

    }

    /**
     * Insert a client into the clients table
     * @param u The Client to add to the clients table
     * @throws SQLException
     */
    public static void addUser(User u, User currentUser) throws SQLException {
        if(currentUser.getName() == "admin")
        {
            StringBuilder sqlInsert = new StringBuilder();
            sqlInsert.append("INSERT INTO users (User_Name, Password) VALUES('");
            sqlInsert.append(u.getName());
            sqlInsert.append("', '");
            sqlInsert.append(u.getPass());
            sqlInsert.append("')");
            Query.queryDb(sqlInsert.toString());
        }
        else
        {
            notAdminAlert();
        }
    }

    /**
     * Delete a client from the clients table
     * @param u The client to delete
     * @throws SQLException
     */
    public static void deleteUser(User u, User currentUser) throws SQLException {
        if(currentUser.getName() == "admin")
        {
            StringBuilder userDelete = new StringBuilder();
            userDelete.append("DELETE FROM users WHERE User_ID = ");
            userDelete.append(u.getId());
            try
            {
                Query.queryDb(userDelete.toString());
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            notAdminAlert();
        }
    }


    public static ObservableList<User> searchUsers(String q) throws SQLException {
        ObservableList<User> users = getUsers();
        ObservableList<User> results = observableArrayList();
        for (User v: users) {
            if(hasQuery(v, q))
            {
                results.add(v);
            }
        }

        return results;
    }

    public static boolean hasQuery(User u, String query)
    {
        boolean found = false;
        String q = query.toUpperCase();
        if(String.valueOf(u.getId()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(u.getName().toUpperCase().indexOf(q) >= 0){found = true;}
        return found;
    }

    public static void notAdminAlert()
    {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Permissions Error");
            alert.setTitle("Permissions Error");
            alert.setContentText("This action requires elevated permissions.");
            alert.show();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
