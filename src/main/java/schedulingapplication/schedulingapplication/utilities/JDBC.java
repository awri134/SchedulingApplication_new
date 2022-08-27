package schedulingapplication.schedulingapplication.utilities;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Utility class for database functionality
 */
public abstract class JDBC
{
    private static final String protocol = "jdbc";
    private static final String vendor = ":sqlite:";
    private static final String location = "//localhost/";
    //private static final String databaseName = "volunteer_schedules.db";
    private static final String databaseName = "volunteer_schedules.sqlite";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String sqlitejdbcUrl = protocol + vendor  + databaseName; // sqlite Database
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String sqlitedriver = "org.sqlite.JDBC";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";

    public static Connection connection;

    /**
     * Connects to the defined database
     */
    public static void openConnection()
    {
        try
        {
            Class.forName(sqlitedriver);
            //connection = DriverManager.getConnection(jdbcUrl, userName, password);
            connection = DriverManager.getConnection(sqlitejdbcUrl);
            connection.setAutoCommit(true);
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());

        }
    }

    /**
     * @return The Connection to the database
     */
    public static Connection getConnection()
    {
        return connection;
    }

    public static void closeConnection()
    {
        try
        {
            connection.close();
        }
        catch(Exception e)
        {

        }

    }
}
