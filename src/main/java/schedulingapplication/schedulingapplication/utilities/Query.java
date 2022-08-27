package schedulingapplication.schedulingapplication.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class to handle executing queries on the database
 */
public abstract class Query {
    private static final Connection conn = JDBC.getConnection();
    private static String query;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static final String useDb = "USE volunteer_schedules;";

    /**
     * Execute a mySql query
     * @param s The string holding the query
     * @throws SQLException
     */
    public static void queryDb(String s) throws SQLException
    {
        //query = useDb + " " + s;
        query = s;
        ps = conn.prepareStatement(s);
        if(s.toLowerCase().startsWith("select"))
        {
            rs = ps.executeQuery();
        }
        if(s.toLowerCase().startsWith("delete") || s.toLowerCase().startsWith("insert") || s.toLowerCase().startsWith("update"))
        {
            try {
                ps.executeUpdate();
            }
            catch(Exception e)
            {
                System.out.println("Error from queryDb");
                System.out.println(e.getMessage());
                System.out.println(s);
            }
        }
    }

    /**
     * Get the ResultSet from the last query executed
     * @return the ResultSet built from the executed query
     */
    public static ResultSet getResults() {
        return rs;
    }
}
