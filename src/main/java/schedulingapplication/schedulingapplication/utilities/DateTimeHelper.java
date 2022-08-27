package schedulingapplication.schedulingapplication.utilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for handling date and time functions
 */
public class DateTimeHelper
{
    /**
     * Formats a date string to a UTC formatted Timestamp
     * @param d the String to convert to a Timestamp in UTC
     * @return Timestamp
     * @throws ParseException
     */
    public static Timestamp getDateFormattedUTC(String d) throws ParseException {

        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date newDate = sDate.parse(d);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        Timestamp newTs = toUTCTimeStamp(calendar);

        return newTs;

    }

    /**
     * Builds a String representing a date
     * @param y Year
     * @param m Month
     * @param d Day of month
     * @param h Hour
     * @param mi Minute
     * @return A string built and formatted to convert to a Date or Timestamp object
     */
    public static String buildDateString(String y, String m, String d, String h, String mi)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(y);
        sb.append("-");
        sb.append(m);
        sb.append("-");
        sb.append(d);
        sb.append(" ");
        sb.append(h);
        sb.append(":");
        sb.append(mi);
        sb.append(":00");
        return String.valueOf(sb);
    }


    /**
     * Converts a Calendar object to a Timestamp formatted to UTC
     * @param cal The calendar object to convert to a UTC Timestamp
     * @return Timestamp
     */
    public static Timestamp toUTCTimeStamp(Calendar cal)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return Timestamp.valueOf(sdf.format(cal.getTime()));
    }

    /**
     * Converts a UTC Timestamp to the user's local date and converts it to a Timestamp
     * @param uts the UTC Timestamp to convert to local time
     * @return The converted Timestamp in local time
     */
    public static Timestamp utcToLocal(Timestamp uts){
        LocalDateTime utcldt = uts.toLocalDateTime();
        ZonedDateTime zdt = utcldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime lzdt = zdt.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime locldt = lzdt.toLocalDateTime();
        Timestamp lts = Timestamp.valueOf(locldt);
        return lts;
    }

    /**
     * Converts a local Timestamp to UTC
     * @param local The local Timestamp to convert to UTC
     * @return Timestamp converted to UTC
     */
    public static Timestamp localtoUtc(Timestamp local){
        LocalDateTime ldt = local.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime uzdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime uldt = uzdt.toLocalDateTime();
        /*System.out.println("LocalToUTC");
        System.out.print("UZDT: ");
        System.out.println(uzdt);
        System.out.print("ULDT: ");
        System.out.println(uldt);*/
        Timestamp uts = Timestamp.valueOf(uldt);
        return uts;
    }

    /**
     * Compares a Timestamp to the open office hours
     * @param lts Timestamp in local time to convert to Eastern time to check
     * @return true if lts is between 8am and 10pm Eastern time or false otherwise
     */
    public static boolean officeIsOpen(Timestamp lts)
    {
        boolean isOpen = true;
        LocalDateTime ldt = lts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime ezdt = zdt.withZoneSameInstant(ZoneId.of("US/Eastern"));
        LocalDateTime eldt = ezdt.toLocalDateTime();
        int ehour = eldt.getHour();
        if(ehour >= 22 || ehour < 8)
        {
            isOpen = false;
        }
        return isOpen;
    }

}
