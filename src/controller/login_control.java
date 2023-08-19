package controller;
import database.appointments_table;
import database.users_table;
import helper.JDBC;
import helper.callDirectory;
import helper.timeZones;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.appointments;
import model.users;

import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class displays the login screen of the application.
 * The text and any error messages will display in either English or French based on the user's default system settings.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class login_control extends callDirectory implements Initializable {

    public PasswordField password;
    public TextField usernameInput;
    public Label locationTxt;
    public Button loginTxt;
    public Label headerTxt;
    public ResourceBundle rb;
    public static users current_user;
    public static timeZones currentZoneIds;
    public Button exitTxt;

    /**
     * This method initializes the Login screen of the application,
     * which includes displaying the user's general location/Zone ID in the world.
     *
     * @param url            a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login Page Initialized");

        currentZoneIds = new timeZones(ZoneId.systemDefault(), ZoneId.of("America/New_York"), ZoneId.of("UTC"));
        try {
            rb = ResourceBundle.getBundle("helper/Nat", Locale.getDefault());
            usernameInput.setPromptText(rb.getString("Username"));
            password.setPromptText(rb.getString("Password"));
            locationTxt.setText(rb.getString("Connectingfrom") + ": " + currentZoneIds.getMyZoneId());
            headerTxt.setText(rb.getString("LogintoProceed"));
            loginTxt.setText(rb.getString("Login"));
        } catch (MissingResourceException e) {
            System.out.println("Missing Resource Exception: " + e);
        }
        System.out.println("Current time zones: Local - " + currentZoneIds.getMyZoneId() + "; Eastern - " + currentZoneIds.getEasternZoneId()
                + "; UTC - " + currentZoneIds.getUtcZoneId());
    }

    /**
     * This method takes you directly back to the Main Menu screen of the application if your login is accepted.
     * You will receive an error message if the username and/or password are wrong.
     * Upon correct login, you will be told of as such and also receive a message if your user login has an upcoming appointment or not.
     * Any successful or failed login attempt will be recorded in a separate file.
     *
     * @param actionEvent the Action Event of pressing the Login button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        try {
            rb = ResourceBundle.getBundle("helper/Nat", Locale.getDefault());
            ResultSet resultSet = users_table.userValidation(usernameInput.getText(), password.getText());
            assert resultSet != null;
            if (resultSet.next()) {
                current_user = new users(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getTimestamp(4), resultSet.getString(5), resultSet.getTimestamp(6),
                        resultSet.getString(7));
                logUserAttempt("Success", usernameInput.getText());
                confirmation(rb.getString("Successfullogin") + "!", rb.getString("LoginSuccess"));
                upcomingAppointments();
                callUpDirectory(actionEvent, "main_menu.fxml");
            } else {
                logUserAttempt("Failure", usernameInput.getText());
                confirmation(rb.getString("Invalidusernameandorpassword") + "!", rb.getString("LoginFailure"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        } catch (MissingResourceException e) {
            System.out.println("Missing Resource Exception: " + e);
        }
    }

    /**
     * This method is used to displayed the message of either a successful or failed login.
     *
     * @param message the message to be displayed upon successful or failed login
     * @param label   the message's label to display at the top based on either successful or fails login
     */
    public static void confirmation(String message, String label) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.setTitle(label);
        alert.showAndWait();
    }

    /**
     * This method pulls up a record of a user's upcoming appointments, upon successful login,
     * and advises if there is an appointment within 15 minutes of login with info of which appointment it is.
     * Will simply welcome you in if not so.
     */
    public static void upcomingAppointments() {
        String appointmentText = "";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome!");
        ObservableList<appointments> userAppointments = appointments_table.getAppointmentsByUser(current_user.getId());
        for (appointments userAppointment : userAppointments) {
            LocalTime appointmentTime = userAppointment.getStart().toLocalDateTime().toLocalTime();
            LocalTime currentTime = LocalTime.now();
            long timeDifference = ChronoUnit.MINUTES.between(currentTime, appointmentTime);

            if (timeDifference > 0 && timeDifference <= 15) {
                appointmentText = "You have an appointment in approximately " + timeDifference + " minute(s):\n"
                + " - Appointment #" + userAppointment.getId() + " from " + userAppointment.getStart()
                        + "\n   to " + userAppointment.getEnd();
            } else {
                appointmentText = "You have no upcoming appointments at this time.";
            }
        }
        alert.setContentText(appointmentText);
        alert.showAndWait();
    }

    /**
     * This method opens and writes a login attempt into a separate text file for logging purposes.
     *
     * @param result    the result message of whether the login was successful or not
     * @param userInput the username that was entered in and attempted
     * @throws IOException an exception will be thrown if the application is unable to execute the file to write into
     */
    public static void logUserAttempt(String result, String userInput) throws IOException {
        String filename = "login_activity.txt";
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);
        outputFile.println(result + "! " + userInput + " attempted login on " + LocalDate.now() + " at " + LocalTime.now() + ", "
                + currentZoneIds.getMyZoneId() + " timezone.");
        outputFile.close();
        System.out.println(" - User login attempt recorded.");
    }

    /**
     * This method will allow you to exit the application if you are logged out.
     */
    public void exitApplication() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to exit the application?");
        alert.setTitle("Exit Warning");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            JDBC.closeConnection();
            System.exit(0);
        }
    }
}
