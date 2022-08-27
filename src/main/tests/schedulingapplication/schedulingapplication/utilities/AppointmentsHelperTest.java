package schedulingapplication.schedulingapplication.utilities;

import static org.junit.jupiter.api.Assertions.*;

import javafx.collections.ObservableList;
import junit.framework.TestCase;
import org.junit.Test;
import schedulingapplication.schedulingapplication.models.Appointment;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class AppointmentsHelperTest extends TestCase {

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

    @Test
    public void testAppointmentsOverlap() throws SQLException {
        ObservableList<Appointment> apps = AppointmentsHelper.getAppointments();
        Appointment app = apps.get(apps.size()-1);
        Timestamp timestamp = app.getStart();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        // add 30 seconds
        cal.add(Calendar.SECOND, 30);
        Timestamp newTimestamp = new Timestamp(cal.getTime().getTime());
        app.setStart(newTimestamp);
        assertEquals(true, AppointmentsHelper.appointmentsOverlap(app));
    }
}