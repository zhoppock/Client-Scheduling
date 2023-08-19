package controller;
import database.users_table;
import helper.callDirectory;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.users;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class brings up the Main Menu of the application, with selections to view customers, appointments, and reports.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class main_menu_control extends callDirectory implements Initializable {

    public static users current_user;

    /**
     * This method initializes the Main Menu screen of the application,
     * which includes buttons to go to the Customers screen, Appointments screen, and the 3 database reports.
     * @param url a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Menu Page Initialized");

        current_user = users_table.getCurrentUser();
    }

    /**
     * This method takes you to the Appointments display screen.
     * @param actionEvent the Action Event of pressing the Appointments button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToAppointments(ActionEvent actionEvent) throws IOException {
        callUpDirectory(actionEvent,"appointments.fxml");
    }

    /**
     * This method takes you to the Customers display screen.
     * @param actionEvent the Action Event of pressing the Customers button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToCustomers(ActionEvent actionEvent) throws IOException {
        callUpDirectory(actionEvent,"customers.fxml");
    }

    /**
     * This method takes you back to the login screen as a way of logging out of the application.
     * @param actionEvent the Action Event of pressing the Logout button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToLogin(ActionEvent actionEvent) throws IOException {
        callUpDirectory(actionEvent,"login.fxml");
    }

    /**
     * This method takes you to the Appointment Types Report screen.
     * @param actionEvent the Action Event of pressing the Appointment Types Report button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToAppointmentTypesReport(ActionEvent actionEvent) throws IOException {
        callUpDirectory(actionEvent,"report1.fxml");
    }

    /**
     * This method takes you to the Contact Appointments Report screen.
     * @param actionEvent the Action Event of pressing the Contact Appointments Report button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToContactAppointmentsReport(ActionEvent actionEvent) throws IOException {
        callUpDirectory(actionEvent,"report2.fxml");
    }

    /**
     * This method takes you to the Customer Appointments Report screen.
     * @param actionEvent the Action Event of pressing the Customer Appointments Report button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToCustomerAppointmentsReport(ActionEvent actionEvent) throws IOException {
        callUpDirectory(actionEvent,"report3.fxml");
    }
}
