package controller;

import database.*;
import helper.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.appointments;
import model.contacts;
import model.users;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class allows you to update the details of a selected appointment.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class update_appointment_control extends callDirectory implements Initializable {

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
    public static appointmentContact selectedAppointment;
    public LocalDate selectedDate;
    public LocalTime selectedStartTime;
    public LocalTime selectedEndTime;
    public static timeZones currentZoneIds;

    /**
     * This method initializes the Update Appointment screen of the application,
     * which includes displaying all the details from the selected appointment.
     * @param url a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Update Appointment Page Initialized");
        current_user = users_table.getCurrentUser();
        selectedAppointment = appointments_table.getSelectedAppointment();
        selectedDate = selectedAppointment.getStart().toLocalDateTime().toLocalDate();
        selectedStartTime = selectedAppointment.getStart().toLocalDateTime().toLocalTime();
        selectedEndTime = selectedAppointment.getEnd().toLocalDateTime().toLocalTime();

        idTxt.setText(String.valueOf(selectedAppointment.getId()));
        titleTxt.setText(selectedAppointment.getTitle());
        descriptionTxt.setText(selectedAppointment.getDescription());
        locationTxt.setText(selectedAppointment.getLocation());
        typeTxt.setText(selectedAppointment.getType());
        customerTxt.setText(customers_table.getCustomerName(selectedAppointment.getCustomerId()));

        ObservableList<contacts> contactList = contacts_table.getAllContacts();
        for (contacts contact : contactList) {
            contactCombo.getItems().add(contact.getName());
        }
        contactCombo.setValue(selectedAppointment.getContactName());

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
        startCombo.setValue(selectedStartTime);

        LocalTime end = startCombo.getValue();
        while (end.isBefore(startCombo.getValue().plusHours(2))) {
            end = end.plusMinutes(30);
            endCombo.getItems().add(end);
        }
        endCombo.setValue(selectedEndTime);
        datePicker.setValue(selectedDate);
    }

    /**
     * This method takes you back to the login screen as a way of logging out of the application.
     * A warning message will appear if you had updated any of the parameters.
     * @param actionEvent the Action Event of pressing the Logout button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToLogin(ActionEvent actionEvent) throws IOException {
        warningMessage(actionEvent,"login.fxml");
    }

    /**
     * This method takes you directly back to the Main Menu screen of the application.
     * A warning message will appear if you had updated any of the parameters.
     * @param actionEvent the Action Event of pressing the Main Menu button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        warningMessage(actionEvent,"main_menu.fxml");
    }

    /**
     * This method will save your updates of the selected appointment to the database
     * and take you back to the Appointments screen of the application.
     * A warning message will appear if any of the parameters are not filled in
     * or if you changed the scheduled date and time to one that interferes with another appointment for the same customer
     * with info on which appointment that is.
     * A message will also appear if you haven't made any changes and will send you back to the Appointments screen of the application.
     * @param actionEvent the Action Event of pressing the Save button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void saveAppointment(ActionEvent actionEvent) throws IOException {
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
        } else if (titleTxt.getText().equals(selectedAppointment.getTitle()) && descriptionTxt.getText().equals(selectedAppointment.getDescription())
                && locationTxt.getText().equals(selectedAppointment.getLocation()) && typeTxt.getText().equals(selectedAppointment.getType())
                && contactCombo.getValue().equals(selectedAppointment.getContactName()) && datePicker.getValue().equals(selectedDate)
                && startCombo.getValue().equals(selectedStartTime) && endCombo.getValue().equals(selectedEndTime)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Warning");
            alert.setContentText("No changes were made.");
            alert.showAndWait();
            callUpDirectory(actionEvent, "appointments.fxml");
        }else {
            Timestamp startTimestamp = Timestamp.valueOf(timesAndZoneIds.convertZDTs(LocalDateTime.of(datePicker.getValue(), startCombo.getValue()), currentZoneIds.getMyZoneId(),currentZoneIds.getUtcZoneId()));
            Timestamp endTimestamp = Timestamp.valueOf(timesAndZoneIds.convertZDTs(LocalDateTime.of(datePicker.getValue(), endCombo.getValue()), currentZoneIds.getMyZoneId(),currentZoneIds.getUtcZoneId()));
            Timestamp systemTimestamp = Timestamp.valueOf(timesAndZoneIds.convertZDTs(LocalDateTime.now(), currentZoneIds.getMyZoneId(),currentZoneIds.getUtcZoneId()));

            Timestamp localStartTime = Timestamp.valueOf(LocalDateTime.of(datePicker.getValue(), startCombo.getValue()));
            Timestamp localEndTime = Timestamp.valueOf(LocalDateTime.of(datePicker.getValue(), endCombo.getValue()));
            appointments overlappedAppointment = null;
            ObservableList<appointments> appointmentList = appointments_table.getAppointmentsByCustomer(selectedAppointment.getCustomerId());
            for(appointments appointment : appointmentList) {
                if (((localStartTime.after(appointment.getStart()) && localStartTime.before(appointment.getEnd())) ||
                        (localEndTime.after(appointment.getStart()) && localEndTime.before(appointment.getEnd())) ||
                        localStartTime.equals(appointment.getStart()) || localEndTime.equals(appointment.getEnd())) &&
                        (selectedAppointment.getId() != appointment.getId())) {
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
                int rowsAffected = appointments_table.updateAppointment(titleTxt.getText(), descriptionTxt.getText(), locationTxt.getText(),
                        typeTxt.getText(), startTimestamp, endTimestamp, systemTimestamp, current_user.getName(),
                        selectedAppointment.getCustomerId(), current_user.getId(), contacts_table.getContactId(contactCombo.getValue()),
                        selectedAppointment.getId());
                if (rowsAffected > 0) {
                    System.out.println(" - Appointment Updated Successfully!");
                    callUpDirectory(actionEvent, "appointments.fxml");
                } else {
                    System.out.println(" - Failure Updating Appointment!");
                }
            }
        }
    }

    /**
     * This method takes you back to the Appointments screen of the application.
     * A warning message will appear if you had updated any of the parameters.
     * @param actionEvent the Action Event of pressing the Cancel button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void cancelAppointment(ActionEvent actionEvent) throws IOException {
        warningMessage(actionEvent,"appointments.fxml");
    }

    /**
     * This method displays a warning message if any of the parameters of the window have been changed
     * and you choose to exit the window without changing them back (excluding the Save button of course).
     * @param event the Action Event of pressing the respective method button in order to change the scene
     * @param directory the directory of the file that the application will change scenes to
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void warningMessage(ActionEvent event, String directory) throws IOException {
        if (titleTxt.getText().equals(selectedAppointment.getTitle()) && descriptionTxt.getText().equals(selectedAppointment.getDescription())
                && locationTxt.getText().equals(selectedAppointment.getLocation()) && typeTxt.getText().equals(selectedAppointment.getType())
                && contactCombo.getValue().equals(selectedAppointment.getContactName()) && datePicker.getValue() != null
                && datePicker.getValue().equals(selectedDate) && startCombo.getValue().equals(selectedStartTime) && endCombo.getValue().equals(selectedEndTime)) {
            callUpDirectory(event, directory);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Warning");
            alert.setContentText("Are you sure you want to exit?  Your changes will not be saved.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                callUpDirectory(event, directory);
            }
        }
    }

    /**
     * This method will reset the selection in the End Time combo box if the selection in the Start Time combo box is changed.
     * End Time values are displayed based of the selected Start Time.
     */
    public void startSelected() {
        if(!(startCombo.getSelectionModel().isEmpty())) {
            endCombo.getItems().clear();
            endCombo.setPromptText("Select Timeframe");
            LocalTime end = startCombo.getValue();
            while (end.isBefore(startCombo.getValue().plusMinutes(120))) {
                end = end.plusMinutes(30);
                endCombo.getItems().add(end);
            }
        }
    }

    /**
     * This method checks the datePicker field to see if the input is empty.
     * If it is empty, it ensures that the value is set to null.
     * Disclaimer: This works in 'add_appointment', but not here for some reason and I was unable to fix it, no matter what I searched.
     * The application will only recognize the empty field if you hit the enter key after clearing it.
     */
    public void checkDate() {
        datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                datePicker.setValue(null);
            }
        });
    }
}
