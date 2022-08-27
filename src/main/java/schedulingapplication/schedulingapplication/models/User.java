package schedulingapplication.schedulingapplication.models;

import schedulingapplication.schedulingapplication.SchedulingApplication;
import schedulingapplication.schedulingapplication.utilities.LoginHelper;

import java.sql.SQLException;
import java.util.Locale;

/**
 * Creates a User
 */
public class User extends Person
{
    private String pass;
    private boolean authenticated;

    private final Locale locale = SchedulingApplication.locale;

    /**
     * Creates a User and attempts to authenticate them
     * @param uname A String representation of the User username
     * @param pass A String representation of the User password
     */
    public User(String uname, String pass) {
        super(uname);
        this.pass = pass;
    }

    /**
     * @param uid The identifier of the User - Must be unique
     * @param uname A String representation of the User username
     * @param pass A String representation of the User password
     */
    public User(int uid, String uname, String pass) {
        super(uid, uname);
        this.pass = pass;

    }

    /**
     * @return  A String representation of the User password
     */
    public String getPass() {
        return this.pass;
    }

    /**
     * @param pass A String representation of the User password
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return The locale for the current user
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @return a boolean for whether the user passed the authentication test in LoginHelper.java
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * @param authenticated A boolean value based on User authentication status - true if authenticated, false if not authenticated
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * Method to call authentication and login attempt logging methods
     * @throws SQLException
     */
    public void auth() throws SQLException
    {
        LoginHelper.auth(this);
        LoginHelper.loginLog(this);
    }

}

