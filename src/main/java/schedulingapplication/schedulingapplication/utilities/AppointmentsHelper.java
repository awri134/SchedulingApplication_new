package schedulingapplication.schedulingapplication.utilities;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import schedulingapplication.schedulingapplication.models.Appointment;
import schedulingapplication.schedulingapplication.models.Client;
import schedulingapplication.schedulingapplication.models.User;
import schedulingapplication.schedulingapplication.models.Volunteer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Utility class for handling appointment functions
 */
public abstract class AppointmentsHelper
{

    /**
     * Gets all Appointments from the appointments table
     * @return An ObservableList of all appointments as Appointment objects
     * @throws SQLException
     */
    public static ObservableList getAppointments() throws SQLException {
        ObservableList<Appointment> appointments = observableArrayList();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Create_Date, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Client_ID, a.User_ID, a.Volunteer_ID, c.Client_Name, u.User_Name, co.Volunteer_Name FROM appointments AS a JOIN clients AS c ON a.Client_ID = c.Client_ID JOIN users AS u ON a.User_ID = u.User_ID JOIN volunteers AS co ON a.Volunteer_ID = co.Volunteer_ID ORDER BY a.Start ASC";
        Query.queryDb(sql);
        ResultSet apps = Query.getResults();
        while (apps.next())
        {
            Integer appId = apps.getInt("Appointment_ID");
            String title = apps.getString("Title");
            String desc = apps.getString("Description");
            String loc = apps.getString("Location");
            String type = apps.getString("Type");
            //Timestamp start = apps.getTimestamp("Start");
            //Timestamp end = apps.getTimestamp("End");
            Timestamp start = DateTimeHelper.utcToLocal(apps.getTimestamp("Start"));
            Timestamp end = DateTimeHelper.utcToLocal(apps.getTimestamp("End"));
            String updatedBy = apps.getString("Last_Updated_By");
            Timestamp lastUpdated = apps.getTimestamp("Last_Update");
            String createdBy = apps.getString("Created_By");
            Timestamp createdDate = apps.getTimestamp("Create_Date");
            Integer clientId = apps.getInt("Client_ID");
            Integer userId = apps.getInt("User_ID");
            Integer volunteerId = apps.getInt("Volunteer_ID");
            String clientName = apps.getString("Client_Name");
            String userName = apps.getString("User_Name");
            String volName = apps.getString("Volunteer_Name");
            Appointment a = new Appointment(appId, title, desc, loc, type, start, end, updatedBy, lastUpdated, createdBy, createdDate, clientId, userId, volunteerId, clientName, userName, volName);

            appointments.add(a);
        }
        return appointments;
    }

    /**
     * Gets Appointments related to the provided Volunteer
     * @param volunteer The volunteer object to use when getting appointments.
     * @return an ObservableList of Appointments for the provided volunteer.
     * @throws SQLException
     */
    public static ObservableList getAppointments(Volunteer volunteer) throws SQLException {
        ObservableList<Appointment> appointments = observableArrayList();
        int cid = volunteer.getId();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Create_Date, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Client_ID, a.User_ID, a.Volunteer_ID, c.Client_Name, u.User_Name, co.Volunteer_Name FROM appointments AS a JOIN clients AS c ON a.Client_ID = c.Client_ID JOIN users AS u ON a.User_ID = u.User_ID JOIN volunteers AS co ON a.Volunteer_ID = co.Volunteer_ID AND a.Volunteer_ID = ");
        sb.append(cid);
        sb.append(" ORDER BY a.Start ASC");
        String sql = String.valueOf(sb);
        Query.queryDb(sql);
        ResultSet apps = Query.getResults();
        while (apps.next())
        {
            Integer appId = apps.getInt("Appointment_ID");
            String title = apps.getString("Title");
            String desc = apps.getString("Description");
            String loc = apps.getString("Location");
            String type = apps.getString("Type");
            Timestamp start = DateTimeHelper.utcToLocal(apps.getTimestamp("Start"));
            Timestamp end = DateTimeHelper.utcToLocal(apps.getTimestamp("End"));
            String updatedBy = apps.getString("Last_Updated_By");
            Timestamp lastUpdated = apps.getTimestamp("Last_Update");
            String createdBy = apps.getString("Created_By");
            Timestamp createdDate = apps.getTimestamp("Create_Date");
            Integer clientId = apps.getInt("Client_ID");
            Integer userId = apps.getInt("User_ID");
            Integer volunteerId = apps.getInt("Volunteer_ID");
            String clientName = apps.getString("Client_Name");
            String userName = apps.getString("User_Name");
            String volName = apps.getString("Volunteer_Name");
            Appointment a = new Appointment(appId, title, desc, loc, type, start, end, updatedBy, lastUpdated, createdBy, createdDate, clientId, userId, volunteerId, clientName, userName, volName);

            appointments.add(a);
        }
        return appointments;
    }

    /**
     * Gets Appointments with start times in the current month
     * @return An ObservableList of Appointments
     * @throws SQLException
     */
    public static ObservableList getMonthFilteredAppointments() throws SQLException {
        ObservableList<Appointment> appointments = observableArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Create_Date, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Client_ID, a.User_ID, a.Volunteer_ID, c.Client_Name, u.User_Name, co.Volunteer_Name FROM appointments AS a JOIN clients AS c ON a.Client_ID = c.Client_ID JOIN users AS u ON a.User_ID = u.User_ID JOIN volunteers AS co ON a.Volunteer_ID = co.Volunteer_ID AND ((DATE(Start) >= DATE(\"now\")) AND (date(Start) <  date('now','start of month','+1 month','-1 day')))");
        sb.append(" ORDER BY a.Start ASC");
        String sql = String.valueOf(sb);
        Query.queryDb(sql);
        ResultSet apps = Query.getResults();
        while (apps.next())
        {
            Integer appId = apps.getInt("Appointment_ID");
            String title = apps.getString("Title");
            String desc = apps.getString("Description");
            String loc = apps.getString("Location");
            String type = apps.getString("Type");
            Timestamp start = DateTimeHelper.utcToLocal(apps.getTimestamp("Start"));
            Timestamp end = DateTimeHelper.utcToLocal(apps.getTimestamp("End"));
            String updatedBy = apps.getString("Last_Updated_By");
            Timestamp lastUpdated = apps.getTimestamp("Last_Update");
            String createdBy = apps.getString("Created_By");
            Timestamp createdDate = apps.getTimestamp("Create_Date");
            Integer clientId = apps.getInt("Client_ID");
            Integer userId = apps.getInt("User_ID");
            Integer volunteerId = apps.getInt("Volunteer_ID");
            String clientName = apps.getString("Client_Name");
            String userName = apps.getString("User_Name");
            String volName = apps.getString("Volunteer_Name");
            Appointment a = new Appointment(appId, title, desc, loc, type, start, end, updatedBy, lastUpdated, createdBy, createdDate, clientId, userId, volunteerId, clientName, userName, volName);
            appointments.add(a);
        }
        return appointments;
    }

    /** Gets Appointments with a start date between the current day and 7 days after the current day
     * @return An ObservableList of Appointments
     * @throws SQLException
     */
    public static ObservableList getWeekFilteredAppointments() throws SQLException {
        ObservableList<Appointment> appointments = observableArrayList();
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 1);

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Create_Date, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Client_ID, a.User_ID, a.Volunteer_ID, c.Client_Name, u.User_Name, co.Volunteer_Name FROM appointments AS a JOIN clients AS c ON a.Client_ID = c.Client_ID JOIN users AS u ON a.User_ID = u.User_ID JOIN volunteers AS co ON a.Volunteer_ID = co.Volunteer_ID AND ((Start >= DATE(\"now\")) AND (Start <  DATE(\"now\", \"+7 Days\")))");

        sb.append(" ORDER BY a.Start ASC");
        String sql = String.valueOf(sb);
        Query.queryDb(sql);
        ResultSet apps = Query.getResults();
        while (apps.next())
        {
            Integer appId = apps.getInt("Appointment_ID");
            String title = apps.getString("Title");
            String desc = apps.getString("Description");
            String loc = apps.getString("Location");
            String type = apps.getString("Type");
            Timestamp start = DateTimeHelper.utcToLocal(apps.getTimestamp("Start"));
            Timestamp end = DateTimeHelper.utcToLocal(apps.getTimestamp("End"));
            String updatedBy = apps.getString("Last_Updated_By");
            Timestamp lastUpdated = apps.getTimestamp("Last_Update");
            String createdBy = apps.getString("Created_By");
            Timestamp createdDate = apps.getTimestamp("Create_Date");
            Integer clientId = apps.getInt("Client_ID");
            Integer userId = apps.getInt("User_ID");
            Integer volunteerId = apps.getInt("Volunteer_ID");
            String clientName = apps.getString("Client_Name");
            String userName = apps.getString("User_Name");
            String volName = apps.getString("Volunteer_Name");
            Appointment a = new Appointment(appId, title, desc, loc, type, start, end, updatedBy, lastUpdated, createdBy, createdDate, clientId, userId, volunteerId, clientName, userName, volName);
            appointments.add(a);
        }
        return appointments;
    }

    /**
     * Checks for appointments within the next 15 minutes and gets a message for the user
     * @param user the currently logged in user
     * @return A string built as a message to display to the user if the user has an appointment within 15 minutes from the current time
     * @throws SQLException
     */
    public static String getUpcomingAppointments(User user) throws SQLException {

        int uid = user.getId();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT Appointment_ID, Title, Start FROM appointments WHERE User_ID = ");
        sb.append(uid);
        sb.append(" AND (DATE(Start) = DATE('now') AND (TIME(Start, 'utc') BETWEEN TIME('now','utc') and (TIME('now','+15 minutes','utc'))))");
        sb.append(" ORDER BY Start ASC");
        String sql = String.valueOf(sb);
        System.out.print("Upcoming SQL: ");
        System.out.println(sql);
        Query.queryDb(sql);

        ResultSet apps = Query.getResults();
        StringBuilder asb = new StringBuilder();
        if(apps.next())
        {
            Timestamp start = DateTimeHelper.utcToLocal(apps.getTimestamp("Start"));
            int aid = apps.getInt("Appointment_ID");
            String atitle = apps.getString("Title");
            asb.append("You have appointment ");
            asb.append(aid);
            asb.append(" - ");
            asb.append(atitle);
            asb.append(" on ");
            SimpleDateFormat dsdf = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault());
            asb.append(dsdf.format(start));
            asb.append(" at ");
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            String appTime = sdf.format(start);
            asb.append(appTime);
        }
        else
        {
            asb.append("No upcoming appointments.");
        }
        return String.valueOf(asb);
    }


    /**
     * Updates an Appointment in the appointments table
     * @param a Appointment to update
     * @throws SQLException
     */
    public static void updateAppointment(Appointment a) throws SQLException {
        if(!appointmentsOverlap(a)) {
            StringBuilder sqlUpdate = new StringBuilder();
            sqlUpdate.append("UPDATE appointments SET Title = '");
            sqlUpdate.append(a.getTitle());
            sqlUpdate.append("', Description = '");
            sqlUpdate.append(a.getDescription());
            sqlUpdate.append("', Location = '");
            sqlUpdate.append(a.getLocation());
            sqlUpdate.append("', Type = '");
            sqlUpdate.append(a.getType());
            sqlUpdate.append("', Start = '");
            sqlUpdate.append(a.getStart());
            sqlUpdate.append("', End = '");
            sqlUpdate.append(a.getEnd());
            sqlUpdate.append("', Last_Updated_By = '");
            sqlUpdate.append(a.getUpdatedBy());
            sqlUpdate.append("', Last_Update = '");
            sqlUpdate.append(a.getUpdatedDate());
            sqlUpdate.append("', Client_ID = '");
            sqlUpdate.append(a.getClientId());
            sqlUpdate.append("', User_ID = '");
            sqlUpdate.append(a.getUserId());
            sqlUpdate.append("', Volunteer_ID = '");
            sqlUpdate.append(a.getVolunteerId());
            sqlUpdate.append("' WHERE Appointment_ID = ");
            sqlUpdate.append(a.getId());
            Query.queryDb(String.valueOf(sqlUpdate));
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not update appointment\nClient already has appointment during that time.");
            alert.setTitle("Scheduling Conflict");
            alert.setHeaderText("Scheduling Conflict");
            alert.show();
        }
    }
    /**
     * Inserts an Appointment into the appointments table
     * @param a Appointment to insert
     * @throws SQLException
     */
    public static void createAppointment(Appointment a) throws SQLException {
        if(!appointmentsOverlap(a)) {
            StringBuilder sqlInsert = new StringBuilder();
            sqlInsert.append("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Client_ID, User_ID, Volunteer_ID) VALUES('");
            sqlInsert.append(a.getTitle());
            sqlInsert.append("', '");
            sqlInsert.append(a.getDescription());
            sqlInsert.append("', '");
            sqlInsert.append(a.getLocation());
            sqlInsert.append("', '");
            sqlInsert.append(a.getType());
            sqlInsert.append("', '");
            sqlInsert.append(a.getStart());
            sqlInsert.append("', '");
            sqlInsert.append(a.getEnd());
            sqlInsert.append("', '");
            sqlInsert.append(a.getCreatedDate());
            sqlInsert.append("', '");
            sqlInsert.append(a.getCreatedBy());
            sqlInsert.append("', '");
            sqlInsert.append(a.getUpdatedDate());
            sqlInsert.append("', '");
            sqlInsert.append(a.getUpdatedBy());
            sqlInsert.append("', '");
            sqlInsert.append(a.getClientId());
            sqlInsert.append("', '");
            sqlInsert.append(a.getUserId());
            sqlInsert.append("', '");
            sqlInsert.append(a.getVolunteerId());
            sqlInsert.append("')");
            Query.queryDb(String.valueOf(sqlInsert));
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not update appointment\nClient already has appointment during that time.");
            alert.setTitle("Scheduling Conflict");
            alert.setHeaderText("Scheduling Conflict");
            alert.show();
        }
    }


    /**
     * Deletes the provided Appointment
     * @param a Appointment to delete from the appointments table
     * @throws SQLException
     */
    public static void deleteAppointment(Appointment a) throws SQLException {
        StringBuilder sqlDelete = new StringBuilder();
        sqlDelete.append("DELETE FROM appointments WHERE Appointment_ID = ");
        sqlDelete.append(a.getId());
        Query.queryDb(String.valueOf(sqlDelete));
    }

    /**
     * Deletes all appointments with the provided Client's ID
     * @param c the Client to use for finding the appointments to delete
     * @throws SQLException
     */
    public static void deleteAppointments(Client c) throws SQLException {
        StringBuilder sqlDelete = new StringBuilder();
        sqlDelete.append("DELETE FROM appointments WHERE Client_ID = ");
        sqlDelete.append(c.getId());
        Query.queryDb(String.valueOf(sqlDelete));
    }

    /**
     * Check for overlapping appointment times based on <ul>
     *     <li>Start Time</li>
     *     <li>End Time</li>
     *     <li>Client ID</li>
     * </ul>
     * @param appointment The appointment to check
     * @return true if appointments overlap, false if not
     * @throws SQLException
     */
    public static boolean appointmentsOverlap(Appointment appointment) throws SQLException {
        boolean appOverlap;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT Appointment_ID FROM appointments WHERE (('");
        sb.append(appointment.getStart());
        sb.append("' BETWEEN Start AND END) OR ('");
        sb.append(appointment.getEnd());
        sb.append("' BETWEEN Start AND End)) AND Client_ID = ");
        sb.append(appointment.getClientId());
        String sql = String.valueOf(sb);

        Query.queryDb(sql);
        ResultSet apps = Query.getResults();
        appOverlap = apps.next();

        return appOverlap;
    }

    public static void createTestAppointments() throws SQLException {
        Query.queryDb("DELETE FROM appointments where Appointment_ID > 5");
        int count = 0;
        Calendar calendar = Calendar.getInstance();
        Timestamp tsnow = DateTimeHelper.localtoUtc(new Timestamp(calendar.getTime().getTime()));
        Timestamp tsstart = tsnow;
        calendar.add(Calendar.MINUTE, 14);
        Timestamp tsplus = DateTimeHelper.localtoUtc(new Timestamp(calendar.getTime().getTime()));

        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Client_ID, User_ID, Volunteer_ID) VALUES");
        Random r = new Random();
        int low = 1;
        int high = 6;
        int result;
        while(count < 5)
        {
            result = r.nextInt(high-low) + low;
            count++;
            String title = "Title " + count;
            String description = "Description " + result;
            String location = "Location " + result;
            String type = "Type " + result;
            Timestamp start = tsstart;
            Timestamp end = tsplus;
            String updatedBy = "Updated By " + count;
            Timestamp updatedDate = tsnow;
            String createdBy = "Created By " + count;
            Timestamp createdDate = tsnow;
            int clientId;
            int userId;
            int volunteerId;

            if(count > 0 && (count%2 == 0))
            {

                clientId = r.nextInt(3)+1;
                userId = 1;
                volunteerId = r.nextInt(5)+1;
            }
            else if(count > 0 && (count%3 == 1))
            {

                clientId = r.nextInt(3)+1;
                userId = 2;
                volunteerId = r.nextInt(5)+1;
            }
            else
            {
                clientId = 2;
                userId = 2;
                volunteerId = 2;
            }
            calendar.add(Calendar.MINUTE, 14);
            tsstart = tsplus;
            tsplus = DateTimeHelper.localtoUtc(new Timestamp(calendar.getTime().getTime()));
            sqlInsert.append("('");
            sqlInsert.append(title);
            sqlInsert.append("', '");
            sqlInsert.append(description);
            sqlInsert.append("', '");
            sqlInsert.append(location);
            sqlInsert.append("', '");
            sqlInsert.append(type);
            sqlInsert.append("', '");
            sqlInsert.append(start);
            sqlInsert.append("', '");
            sqlInsert.append(end);
            sqlInsert.append("', '");
            sqlInsert.append(createdDate);
            sqlInsert.append("', '");
            sqlInsert.append(createdBy);
            sqlInsert.append("', '");
            sqlInsert.append(updatedDate);
            sqlInsert.append("', '");
            sqlInsert.append(updatedBy);
            sqlInsert.append("', '");
            sqlInsert.append(clientId);
            sqlInsert.append("', '");
            sqlInsert.append(userId);
            sqlInsert.append("', '");
            sqlInsert.append(volunteerId);
            sqlInsert.append("'), ");

        }
        createTestAppointments(6, sqlInsert);

    }

    public static String createTestAppointments(int startCount, StringBuilder sb) throws SQLException {

        int count = startCount;
        Calendar calendar = Calendar.getInstance(); // this would default to now
        Timestamp tsnow = DateTimeHelper.localtoUtc(new Timestamp(calendar.getTime().getTime()));
        Timestamp tsstart = tsnow;
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Timestamp tsplus = DateTimeHelper.localtoUtc(new Timestamp(calendar.getTime().getTime()));

        StringBuilder sqlInsert = sb;

        Random r = new Random();
        int low = 1;
        int high = 6;
        int result;
        while(count < 10)
        {
            result = r.nextInt(high-low) + low;
            String title = "Title " + count;
            String description = "Description " + result;
            String location = "Location " + result;
            String type = "Type " + result;
            Timestamp start = tsstart;
            Timestamp end = tsplus;
            String updatedBy = "Updated By " + count;
            Timestamp updatedDate = tsnow;
            String createdBy = "Created By " + count;
            Timestamp createdDate = tsnow;
            int clientId;
            int userId;
            int volunteerId;

            if(count > 0 && (count%2 == 0))
            {

                clientId = r.nextInt(3)+1;
                userId = 1;
                volunteerId = r.nextInt(5)+1;
            }
            else if(count > 0 && (count%3 == 1))
            {

                clientId = r.nextInt(3)+1;
                userId = 2;
                volunteerId = r.nextInt(5)+1;
            }
            else
            {
                clientId = 2;
                userId = 2;
                volunteerId = 2;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            tsstart = tsplus;
            tsplus = DateTimeHelper.localtoUtc(new Timestamp(calendar.getTime().getTime()));
            sqlInsert.append("('");
            sqlInsert.append(title);
            sqlInsert.append("', '");
            sqlInsert.append(description);
            sqlInsert.append("', '");
            sqlInsert.append(location);
            sqlInsert.append("', '");
            sqlInsert.append(type);
            sqlInsert.append("', '");
            sqlInsert.append(start);
            sqlInsert.append("', '");
            sqlInsert.append(end);
            sqlInsert.append("', '");
            sqlInsert.append(createdDate);
            sqlInsert.append("', '");
            sqlInsert.append(createdBy);
            sqlInsert.append("', '");
            sqlInsert.append(updatedDate);
            sqlInsert.append("', '");
            sqlInsert.append(updatedBy);
            sqlInsert.append("', '");
            sqlInsert.append(clientId);
            sqlInsert.append("', '");
            sqlInsert.append(userId);
            sqlInsert.append("', '");
            sqlInsert.append(volunteerId);
            sqlInsert.append("')");
            count++;
            if(count != 10)
            {
                sqlInsert.append(", ");
            }

        }
        Query.queryDb(String.valueOf(sqlInsert));
        return String.valueOf(sqlInsert);
    }

    public static ObservableList<Appointment> searchAppointments(String q) throws SQLException {
        ObservableList<Appointment> appointments = getAppointments();
        ObservableList<Appointment> results = observableArrayList();
        for (Appointment a: appointments) {
            if(hasQuery(a, q))
            {
                results.add(a);
            }
        }
        return results;
    }

    public static boolean hasQuery(Appointment a, String query)
    {
        boolean found = false;
        String q = query.toUpperCase();
        if(String.valueOf(a.getId()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(a.getTitle().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(a.getDescription().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(a.getLocation().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(a.getType().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(String.valueOf(a.getStart()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(String.valueOf(a.getEnd()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(a.getUpdatedBy().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(String.valueOf(a.getUpdatedDate()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(a.getCreatedBy().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(String.valueOf(a.getCreatedDate()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(String.valueOf(a.getClientId()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(String.valueOf(a.getUserId()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(String.valueOf(a.getVolunteerId()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(a.getClientName().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(a.getVolName().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(a.getUserName().toUpperCase().indexOf(q) >= 0){found = true;}
        return found;
    }




}
