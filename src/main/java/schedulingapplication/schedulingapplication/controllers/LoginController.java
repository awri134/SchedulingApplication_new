package schedulingapplication.schedulingapplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedulingapplication.schedulingapplication.SchedulingApplication;
import schedulingapplication.schedulingapplication.models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controller for the login-view
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblTimeandZone;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    /**
     * Creates a User and attempts to authenticate with the username and password provided
     * @param event Log in Button clicked
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    public void onLoginButtonClick(ActionEvent event) throws SQLException, IOException {
        try
        {
            if(txtUsername.getText() != "" && txtPassword.getText() != "")
            {
                User user = new User(txtUsername.getText(), txtPassword.getText());
                user.auth();
                boolean userAuthed = user.isAuthenticated();
                if (userAuthed) {
                    this.showDashboard(event, user);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Credentials");
                    alert.setContentText("Login Information Not Found. Try Again");
                    alert.setHeaderText("Login Error");
                    alert.show();
                }
            }
            else if (txtUsername.getText() == "")
            {
                throw new Exception("Username not found");
            }
            else if (txtPassword.getText() == "")
            {
                throw new Exception("Check your password and try again");
            }
            else
            {
                throw new Exception("Username or Password not recognized.");
            }

        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getLocalizedMessage());
            e.printStackTrace();
            alert.show();
        }
    }

    /**
     * Displays the dahsboard view
     * @param event Log In Button clicked
     * @param user The authenticated User
     * @throws IOException
     * @throws SQLException
     */
    private void showDashboard(ActionEvent event, User user) throws IOException, SQLException {
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("dashboard-view.fxml"));*/
        FXMLLoader loader = new FXMLLoader(SchedulingApplication.class.getResource("dashboard-view.fxml"));
        Parent dashboardParent = loader.load();
        Scene dashboardScene = new Scene(dashboardParent);
        DashboardController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        controller.initData(user, window);
        window.setTitle("Dashboard");
        window.setScene(dashboardScene);
        window.setMaximized(true);
        window.show();
    }


    /**
     * <p>Initializes the login view</p>
     * <p>Gets the user's current time and timezone and displays it</p>
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
}