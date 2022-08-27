package schedulingapplication.schedulingapplication.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;

/**
 * Utility class for handling report functions
 */
public abstract class ReportsHelper {
    /**
     * Get the Appointment totals by type from the appointments table
     * @return a string built to display the Appointment totals by type data
     * @throws SQLException
     */
    public static String getTotalsReport() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT Type, count(*) as 'count' FROM appointments GROUP BY type ORDER BY type";
        Query.queryDb(sql);
        ResultSet rs = Query.getResults();
        while(rs.next())
        {
            int count = rs.getInt("count");
            sb.append(rs.getString("Type"));
            sb.append(": ");
            sb.append(count);
            sb.append("\n");
        }

        return String.valueOf(sb);
    }

    /**
     * Get the Appointment totals by Month from the appointments table based on the start date and time
     * @return a string built to display the Appointment totals by month data
     * @throws SQLException
     */
    public static String getMonthsReport() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT strftime('%m', Start) as 'month', count(*) as 'count' FROM appointments GROUP BY 'month' ORDER BY 'month'";
        Query.queryDb(sql);
        ResultSet rs = Query.getResults();
        while(rs.next())
        {
            int count = rs.getInt("count");
            Month m = Month.of(rs.getInt("month"));
            sb.append(m);
            sb.append(": ");
            sb.append(count);
            sb.append("\n");
        }

        return String.valueOf(sb);
    }

    /**
     * Get the average appointment time for each Customer in minutes
     * @return a string holding the average appointment time data
     * @throws SQLException
     */
    public static String getCustTimeReport() throws SQLException {

        StringBuilder sb = new StringBuilder();
        String sql = "SELECT Client_ID, avg(timestampdiff(minute, Start, End)) as avgAppTime, count(*) as 'count' FROM appointments GROUP BY Client_ID ORDER BY Client_ID";
        Query.queryDb(sql);
        ResultSet rs = Query.getResults();
        sb.append("Client\t\tAverage Time\t\tTotal Appointments\n");
        int total = 0;
        while(rs.next())
        {
            int count = rs.getInt("count");
            int custId = rs.getInt("Client_ID");
            double avgTime = rs.getDouble("avgAppTime");
            sb.append(custId);
            sb.append("\t\t");
            int hours = (int) Math.floor(avgTime/60);
            sb.append(hours);
            sb.append(" hours and ");
            int minutes = (int) Math.floor(avgTime%60);
            sb.append(minutes);
            sb.append(" minutes\t\t");
            sb.append(count);
            sb.append("\n");
            total += count;
        }

        return String.valueOf(sb);
    }
}
