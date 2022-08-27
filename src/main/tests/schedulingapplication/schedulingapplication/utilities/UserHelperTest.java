package schedulingapplication.schedulingapplication.utilities;

import javafx.collections.ObservableList;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import schedulingapplication.schedulingapplication.SchedulingApplication;
import schedulingapplication.schedulingapplication.models.User;
import schedulingapplication.schedulingapplication.models.Volunteer;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserHelperTest extends TestCase {

    SchedulingApplication main = new SchedulingApplication();
    public void setUp() throws Exception {
        try
        {
            JDBC.openConnection();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGetUsers() throws SQLException {
        ObservableList<?> testUsers = UserHelper.getUsers();
        //assertEquals(3, UserHelper.getUsers().size());
        User testUser = new User("name", "pass");
        Volunteer vol = new Volunteer("Name", "Email", "Strength", "Quote");

        boolean typeMatches = true;
        for (Object u: testUsers)
        {
            if(u.getClass() != testUser.getClass())
            {
                typeMatches = false;
                break;
            }
        }
        assertEquals(true, typeMatches);
    }

    public void testUpdateUser() throws SQLException {
        User user = new User("TestUser", "TestPass");
        ObservableList<User> testUsers = UserHelper.getUsers();
        User u = testUsers.get(0);
        String uname = u.getName();
        u.setName("NEW NAME HERE");
        UserHelper.updateUser(u, user);
        testUsers = UserHelper.getUsers();
        User updatedUser = testUsers.get(0);
        assertTrue(updatedUser.getName().equals(uname));
    }

    public void testAddUser() throws SQLException {
        User u = new User("NEW USER ADDED", "password");
        User user = new User("TestUser", "TestPass");
        int userCount = UserHelper.getUsers().size();
        UserHelper.addUser(u, user);
        ObservableList<User> users = UserHelper.getUsers();
        int countAfterAdd = users.size();
        assertTrue(userCount == countAfterAdd);
        //Clean up after add test
        UserHelper.deleteUser(users.get(users.size() - 1), user);
    }

    public void testDeleteUser() throws SQLException {
        User u = new User("NEW USER ADDED", "password");
        User usertest = new User("TestUser", "TestPass");
        UserHelper.addUser(u, usertest);
        ObservableList<User> users = UserHelper.getUsers();
        User user = users.get(users.size() - 1);
        int userCount = users.size();
        UserHelper.deleteUser(user, usertest);
        ObservableList<User> afterDelete = UserHelper.getUsers();
        int countAfterDelete = afterDelete.size();
        assertTrue(userCount == countAfterDelete);
    }

    public void testAdminUserUpdateUser() throws SQLException {
        User user = new User("admin", "admin");
        ObservableList<User> testUsers = UserHelper.getUsers();
        User u = testUsers.get(0);
        String uname = u.getName();
        u.setPass("NEW NAME HERE");
        UserHelper.updateUser(u, user);
        testUsers = UserHelper.getUsers();
        User updatedUser = testUsers.get(0);
        assertTrue(updatedUser.getName().equals(uname));
    }

    public void testAdminUserAddUser() throws SQLException {
        User u = new User("NEW USER ADDED", "password");
        User user = new User("admin", "admin");
        int userCount = UserHelper.getUsers().size();
        UserHelper.addUser(u, user);
        ObservableList<User> users = UserHelper.getUsers();
        int countAfterAdd = users.size();
        assertFalse(userCount == countAfterAdd);
        //Clean up after add test
        UserHelper.deleteUser(users.get(users.size() - 1), user);
    }

    public void testAdminUserDeleteUser() throws SQLException {
        User u = new User("NEW USER ADDED", "password");
        User usertest = new User("admin", "admin");
        UserHelper.addUser(u, usertest);
        ObservableList<User> users = UserHelper.getUsers();
        User user = users.get(users.size() - 1);
        int userCount = users.size();
        UserHelper.deleteUser(user, usertest);
        ObservableList<User> afterDelete = UserHelper.getUsers();
        int countAfterDelete = afterDelete.size();
        assertFalse(userCount == countAfterDelete);
    }
}