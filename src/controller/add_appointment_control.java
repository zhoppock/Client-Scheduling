package controller;

import helper.*;
import database.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;


import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class allows you to create an appointment, with specified details, based on a selected customer.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class add_appointment_control extends callDirectory implements Initializable {

    public ComboBox<String> contactCombo;
    public TextField idTxt;
    public TextField titleTxt;
    public TextField descriptionTxt;
    public TextField locationTxt;
    public TextField typeTxt;
    public TextField customerTxt;
    public DatePicker datePicker;
    public ComboBox<LocalTime> startCombo;
    public ComboBox<LocalTime> endCombo;
    public static users current_user;
    public static timeZones currentZoneIds;

    /**
     * This method initializes the Add Appointment screen of the application,
     * which includes displaying the selected customer and selectable date, times, and contact.
     * @param url a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Appointment Page Initialized");
        current_user = users_table.getCurrentUser();
        customerTxt.setText(customers_table.getSelectedCustomer().getName());
        ObservableList<contacts> contactList = contacts_table.getAllContacts();
        for (contacts contact : contactList) {
            contactCombo.getItems().add(contact.getName());
        }

        currentZoneIds = timesAndZoneIds.getZoneIds();
        System.out.println("Current time zones: Local - " + currentZoneIds.getMyZoneId() + "; Eastern - " + currentZoneIds.getEasternZoneId()
        + "; UTC - " + currentZoneIds.getUtcZoneId());
        LocalTime startTime = LocalTime.of(8, 0);
        LocalDateTime myZDT = timesAndZoneIds.convertZDTs(LocalDateTime.of(LocalDate.now(), startTime), currentZoneIds.getEasternZoneId(),currentZoneIds.getMyZoneId());
        LocalTime myZonedTime = myZDT.toLocalTime();
        LocalTime fixedTime = myZonedTime;
        while (myZonedTime.isBefore(fixedTime.plusHours(12).plusMinutes(30))) {
            startCombo.getItems().add(myZonedTime);
            myZonedTime = myZonedTime.plusMinutes(30);
        }
    }

    /**
     * This method takes you back to the login screen as a way of logging out of the application.
     * A warning message will appear if you had filled in any of the parameters.
     * @param actionEvent the Action Event of pressing the Logout button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToLogin(ActionEvent actionEvent) throws IOException {
        warningMessage(actionEvent,"login.fxml");
    }

    /**
     * This method takes you directly back to the Main Menu screen of the application.
     * A warning message will appear if you had filled in any of the parameters.
     * @param actionEvent the Action Event of pressing the Main Menu button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        warningMessage(actionEvent,"main_menu.fxml");
    }

    /**
     * This method will save your newly made appointment to the database and take you back to the Customers screen of the application.
     * A warning message will appear if you have not filled in all the parameters
     * or if you scheduled a date and time that interferes with another appointment for the selected customer
     * with info on which appointment that is.
     * @param actionEvent the Action Event of pressing the Save button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void saveNewAppointment(ActionEvent actionEvent) throws IOException {
        if (titleTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty() || locationTxt.getText().isEmpty() || typeTxt.getText().isEmpty()
                || contactCombo.getSelectionModel().isEmpty() || startCombo.getSelectionModel().isEmpty() || endCombo.getSelectionModel().isEmpty()
                || datePicker.getValue() == null) {
            String inputWarning = "Please fill in the following parameters:";
            if(titleTxt.getText().isEmpty()){inputWarning += "\n - Enter a title";}
            if(descriptionTxt.getText().isEmpty()){inputWarning += "\n - Enter a description";}
            if(locationTxt.getText().isEmpty()){inputWarning += "\n - Enter a location";}
            if(typeTxt.getText().isEmpty()){inputWarning += "\n - Enter a type";}
            if(contactCombo.getSelectionModel().isEmpty()){inputWarning += "\n - Select a contact";}
            if(datePicker.getValue() == null){inputWarning += "\n - Choose a date";}
            if(startCombo.getSelectionModel().isEmpty()){inputWarning += "\n - Select a start time";}
            if(endCombo.getSelectionModel().isEmpty()){inputWarning += "\n - Select an end time";}
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Warning");
            alert.setContentText(inputWarning);
            alert.showAndWait();
        } else {
            Timestamp startTimestamp = Timestamp.valueOf(timesAndZoneIds.convertZDTs(LocalDateTime.of(datePicker.getValue(), startCombo.getValue()), currentZoneIds.getMyZoneId(), currentZoneIds.getUtcZoneId()));
            Timestamp endTimestamp = Timestamp.valueOf(timesAndZoneIds.convertZDTs(LocalDateTime.of(datePicker.getValue(), endCombo.getValue()), currentZoneIds.getMyZoneId(), currentZoneIds.getUtcZoneId()));
            Timestamp systemTimestamp = Timestamp.valueOf(timesAndZoneIds.convertZDTs(LocalDateTime.now(), currentZoneIds.getMyZoneId(), currentZoneIds.getUtcZoneId()));

            Timestamp localStartTime = Timestamp.valueOf(LocalDateTime.of(datePicker.getValue(), startCombo.getValue()));
            Timestamp localEndTime = Timestamp.valueOf(LocalDateTime.of(datePicker.getValue(), endCombo.getValue()));
            appointments overlappedAppointment = null;
            ObservableList<appointments> appointmentList = appointments_table.getAppointmentsByCustomer(customers_table.getSelectedCustomer().getId());
            for(appointments appointment : appointmentList) {
                if ((localStartTime.after(appointment.getStart()) && localStartTime.before(appointment.getEnd())) ||
                        (localEndTime.after(appointment.getStart()) && localEndTime.before(appointment.getEnd())) ||
                        localStartTime.equals(appointment.getStart()) || localEndTime.equals(appointment.getEnd())) {
                    overlappedAppointment = appointment;
                }
            }
            if(overlappedAppointment != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Time Overlap");
                alert.setContentText("Appointment times overlap with another appointment for this customer:\n" +
                        " - Appointment #" + overlappedAppointment.getId() + " from " + overlappedAppointment.getStart() +
                        "\n   to " + overlappedAppointment.getEnd());
                alert.showAndWait();
            } else {
                int rowsAffected = appointments_table.addAppointment(titleTxt.getText(), descriptionTxt.getText(), locationTxt.getText(),
                        typeTxt.getText(), startTimestamp, endTimestamp, systemTimestamp, current_user.getName(),
                        systemTimestamp, current_user.getName(), customers_table.getSelectedCustomer().getId(),
                        current_user.getId(), contacts_table.getContactId(contactCombo.getValue()));
                if (rowsAffected > 0) {
                    System.out.println(" - Appointment Added Successfully!");
                    callUpDirectory(actionEvent, "customers.fxml");
                } else {
                    System.out.println(" - Failure Adding Appointment!");
                }
            }
        }
    }

    /**
     * This method takes you back to the Customers screen of the application.
     * A warning message will appear if you had filled in any of the parameters.
     * @param actionEvent the Action Event of pressing the Cancel button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void cancelNewAppointment(ActionEvent actionEvent) throws IOException {
        warningMessage(actionEvent,"customers.fxml");
    }

    /**
     * This method displays a warning message if any of the parameters of the window have been filled
     * and you choose to exit the window without clearing them (excluding the Save button of course).
     * @param event the Action Event of pressing the respective method button in order to change the scene
     * @param directory the directory of the file that the application will change scenes to
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void warningMessage(ActionEvent event, String directory) throws IOException {
        if(titleTxt.getText().isEmpty() && descriptionTxt.getText().isEmpty() && locationTxt.getText().isEmpty() && typeTxt.getText().isEmpty()
                && datePicker.getValue() == null && contactCombo.getSelectionModel().isEmpty()
                && startCombo.getSelectionModel().isEmpty() && endCombo.getSelectionModel().isEmpty()) {
            callUpDirectory(event, directory);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Warning");
            alert.setContentText("Are you sure you want to exit?  Your information will not be saved.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                callUpDirectory(event, directory);
            }
        }
    }

    /**
     * This method enables the End Time combo box once a selection has been made in the Start Time combo box.
     * Also, whenever the selection in the Start Time combo box is changed,
     * it will reset the selection in the End Time combo box to values associated with the selected Start Time.
     */
    public void startSelected() {
        if(!(startCombo.getSelectionModel().isEmpty())) {
            endCombo.setDisable(false);
            endCombo.getItems().clear();
            endCombo.setPromptText("Select Timeframe");
            LocalTime end = startCombo.getValue();
            while (end.isBefore(startCombo.getValue().plusHours(2))) {
                end = end.plusMinutes(30);
                endCombo.getItems().add(end);
            }
        }
    }

    /**
     * This method checks the datePicker field to see if the input is empty.
     * If it is empty, it ensures that the value is set to null.
     * In regards to the lambda expression used, the parameters keep track of the observable and old values inside the datePicker field.
     * Acting as a listener, the lambda can immediately determine if the datePicker's new value is empty.
     * It will set the datePicker value to null and end its usage until the datePicker field is changed again.
     */
    public void checkDate() {
        datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                datePicker.setValue(null);
            }
        });
    }
}
