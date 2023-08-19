package controller;

import database.appointments_table;
import helper.callDirectory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.appointments;
import helper.reportInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class displays the first available database report.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class report1_control extends callDirectory implements Initializable {

    public TextArea reportArea;

    /**
     * This method initializes the First Report screen of the application,
     * which shows a report of all appointments sorted by Type, with how many for each type, and then the month of the Start time.
     * @param url a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Appointment Types Report Page Initialized");

        ObservableList<appointments> allAppointments = appointments_table.getAppointmentsSortByTypeAndMonth();
        reportInterface appointmentReport = n -> {
            StringBuilder message = new StringBuilder("Currently showing " + allAppointments.size() + " appointment(s) scheduled in the client database.\n\n");
            String currentType = null;
            for(appointments appointment : n) {
                if(currentType == null) {
                    message.append("Showing ").append(appointments_table.getAppointmentTypes(appointment.getType()).size())
                            .append(" appointment(s) of type ").append(appointment.getType()).append(".\n\n");
                } else if(!currentType.equals(appointment.getType())){
                    message.append("\n--------------------------------------\n")
                            .append("Showing ").append(appointments_table.getAppointmentTypes(appointment.getType()).size())
                            .append(" appointment(s) of type ").append(appointment.getType()).append(".\n\n");
                }
                message.append("ID:  ").append(appointment.getId()).append("  |  TITLE:  ").append(appointment.getTitle()).append("  |  DESCRIPTION:  ")
                        .append(appointment.getDescription()).append("  |  LOCATION:  ").append(appointment.getLocation()).append("  |  TYPE:  ")
                        .append(appointment.getType()).append("\nSTART TIME:  ").append(appointment.getStart()).append("  |  END TIME:  ")
                        .append(appointment.getEnd()).append("\nCREATION DATE:  ").append(appointment.getCreateDate()).append("  |  CREATED BY:  ")
                        .append(appointment.getCreatedBy()).append("\nLAST UPDATED ON:  ").append(appointment.getLastUpdate()).append("  |  LAST UPDATED BY:  ")
                        .append(appointment.getLastUpdatedBy()).append("\nCUSTOMER ID:  ").append(appointment.getCustomerId()).append("  |  USER ID:  ")
                        .append(appointment.getUserId()).append("  |  CONTACT ID:  ").append(appointment.getContactId()).append("\n\n");
                currentType = appointment.getType();
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
