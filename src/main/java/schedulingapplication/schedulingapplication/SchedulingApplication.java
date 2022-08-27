package schedulingapplication.schedulingapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import schedulingapplication.schedulingapplication.utilities.AppointmentsHelper;
import schedulingapplication.schedulingapplication.utilities.JDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * This is the main class for the Scheduling Application for management of customers, schedules, and obtaining reports.
 */
public class SchedulingApplication extends Application {

    public static Locale locale = Locale.getDefault();

    //public static ResourceBundle rb = ResourceBundle.getBundle("messages", Locale.getDefault());
    public static ResourceBundle rb;
    public static TimeZone tz;
    public static String dtz;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("JavaFX Version: " + System.getProperty("javafx.version"));
        System.out.println("JavaFX Runtime Version: " + System.getProperty("javafx.runtime.version"));
        //Locale.setDefault(new Locale("fr"));
        //System.setProperty("user.timezone", "Asia/Kolkata");
        tz = TimeZone.getDefault();
        dtz = tz.getDisplayName();
        locale = Locale.getDefault();
        //rb = ResourceBundle.getBundle("messages", locale);
        JDBC.openConnection();
        AppointmentsHelper.createTestAppointments();
        FXMLLoader fxmlLoader = new FXMLLoader(SchedulingApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.setTitle("Appointment Scheduling");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
