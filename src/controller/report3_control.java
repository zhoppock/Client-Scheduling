package controller;

import database.appointments_table;
import database.customers_table;
import helper.callDirectory;
import helper.reportInterface;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.appointments;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class displays the third available database report.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class report3_control extends callDirectory implements Initializable {

    public TextArea reportArea;

    /**
     * This method initializes the Third Report screen of the application,
     * which shows a report of each Customer list of their respective appointments by year.
     * @param url a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Customer Appointments Report Page Initialized");

        ObservableList<appointments> allAppointments = appointments_table.getAppointmentsSortByCustomerAndYear();
        reportInterface appointmentReport = n -> {
            StringBuilder message = new StringBuilder("Currently showing " + allAppointments.size() + " appointment(s) scheduled in the client database.\n\n");
            int current_customer_id = 0;
            for(appointments appointment : n) {
                if(current_customer_id == 0) {
                    message.append(customers_table.getCustomerName(appointment.getCustomerId())).append("'s Appointment History:\n\n");
                } else if(current_customer_id != appointment.getCustomerId()){
                    message.append("\n--------------------------------------\n")
                            .append(customers_table.getCustomerName(appointment.getCustomerId())).append("'s Appointment History:\n\n");
                }

                message.append("APPOINTMENT ID:  ").append(appointment.getId()).append("  |  TITLE:  ").append(appointment.getTitle()).append("  |  DESCRIPTION:  ")
                        .append(appointment.getDescription()).append("  |  LOCATION:  ").append(appointment.getLocation()).append("  |  TYPE:  ")
                        .append(appointment.getType()).append("\nSTART TIME:  ").append(appointment.getStart()).append("  |  END TIME:  ")
                        .append(appointment.getEnd()).append("\nUSER ID:  ").append(appointment.getUserId())
                        .append(appointment.getEnd()).append("  |  CONTACT ID:  ").append(appointment.getContactId()).append("\n\n");
                current_customer_id = appointment.getCustomerId();
            }
            message.append("END OF REPORT");
            return message.toString();
        };
        reportArea.setText(appointmentReport.displayReport(allAppointments));
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
     * This method takes you back to the Main Menu screen of the application.
     * @param actionEvent the Action Event of pressing the Main Menu button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        callUpDirectory(actionEvent,"main_menu.fxml");
    }
}
