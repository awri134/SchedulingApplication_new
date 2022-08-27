package schedulingapplication.schedulingapplication.utilities;

import schedulingapplication.schedulingapplication.models.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Utility class for handling login functions
 */
public abstract class LoginHelper {

    /**
     * Checks if the User can be authenticated
     * @param user The created User that is attempting to log in
     * @throws SQLException
     */
    public static void auth(User user) throws SQLException
    {
        String sql = "SELECT * FROM users WHERE User_Name='" + user.getName() + "'";
        Query.queryDb(sql);
        ResultSet users = Query.getResults();
        if(users.next())
        {
            String un = users.getString("User_Name");
            String pw = users.getString("Password");
            if(un.equals(user.getName()) && pw.equals(user.getPass()))
            {
                user.setId(users.getInt("User_ID"));
                user.setAuthenticated(true);

            }
        }
    }

    /**
     * Logs
     * <ul>
     * <li>timestamp</li>
     * <li>username</li>
     * <li>authentication status</li>
     * </ul>
     * to the login_activity.txt file
     * @param user The authenticated User to log
     *
     */
    public static void loginLog(User user)
    {
        try
        {
            String fileName = "src/schedulingApplication/login_activity.txt";
            StringBuilder sb = new StringBuilder();
            Date date = new Date();
            Timestamp timestamp = DateTimeHelper.localtoUtc(new Timestamp(date.getTime()));
            sb.append(timestamp);
            sb.append(" UTC; Username: ");
            sb.append(user.getName());
            sb.append("; Authenticated: ");
            sb.append(user.isAuthenticated());
            sb.append("\n");

            FileWriter writer = new FileWriter(new File(fileName), true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(String.valueOf(sb));
            bw.close();
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}