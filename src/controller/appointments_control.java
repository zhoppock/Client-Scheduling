package controller;

import database.appointments_table;
import helper.callDirectory;
import helper.appointmentContact;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class displays all appointments based on either the upcoming week or month.
 * There are options to update or deleted a selected appointment.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class appointments_control extends callDirectory implements Initializable {

    public TableColumn<appointmentContact, Integer> idColumn;
    public TableColumn<appointmentContact, String> titleColumn;
    public TableColumn<appointmentContact, String> descriptionColumn;
    public TableColumn<appointmentContact, String> locationColumn;
    public TableColumn<appointmentContact, String> contactNameColumn;
    public TableColumn<appointmentContact, String> typeColumn;
    public TableColumn<appointmentContact, Timestamp> startColumn;
    public TableColumn<appointmentContact, Timestamp> endColumn;
    public TableColumn<appointmentContact, Integer> customerIdColumn;
    public TableColumn<appointmentContact, Integer> userIdColumn;
    public TableView<appointmentContact> dataTable;
    public RadioButton allRadio;
    public RadioButton weekRadio;
    public RadioButton monthRadio;
    public ToggleGroup timeframe;
    public static appointmentContact selectedAppointment;

    /**
     * This method initializes the Appointments screen of the application,
     * which includes displaying all appointments for the coming week (selected by default).
     * @param url a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Appointments Page Initialized");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        allSelected();
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
     * This method takes you the Update Appointment screen with the details of a selected appointment.
     * A warning message will appear if you have not selected an appointment from the TableView.
     * @param actionEvent the Action Event of pressing the Update Appointment button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToUpdateAppointment(ActionEvent actionEvent) throws IOException {
        selectedAppointment = dataTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            callUpDirectory(actionEvent,"update_appointment.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Warning");
            alert.setContentText("No appointment has been selected to update.");
            alert.showAndWait();
        }
    }

    /**
     * This method allows you to delete a selected appointment, asking you if you are sure you want it deleted.
     * The window will refresh to reflect the the appointment is no longer there.
     * A warning message will appear if you have not selected an appointment from the TableView.
     * @param actionEvent the Action Event of pressing the Delete Appointment button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void deleteAppointment(ActionEvent actionEvent) throws IOException {
        selectedAppointment = dataTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deletion Warning");
            alert.setContentText("Are you sure you want to cancel this Appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int appointmentId = selectedAppointment.getId();
                String appointmentType = selectedAppointment.getType();
                int rowsAffected  = appointments_table.deleteAppointment(selectedAppointment.getId());
                if(rowsAffected > 0) {
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Deletion Warning");
                    confirmation.setContentText("Appointment #" + appointmentId + " of type " + appointmentType + " has been cancelled.");
                    confirmation.showAndWait();
                    System.out.println("Appointment Successfully Cancelled.");
                    callUpDirectory(actionEvent,"appointments.fxml");
                }
                else {
                    System.out.println("Appointment Failed To Be Cancelled.");
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Warning");
            alert.setContentText("No appointment has been selected for cancellation.");
            alert.showAndWait();
        }
    }

    /**
     * This method takes you back to the Main Menu screen of the application.
     * @param actionEvent the Action Event of pressing the Main Menu button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToMainMenu(ActionEvent actionEvent) throws IOException {
        callUpDirectory(actionEvent,"main_menu.fxml");
    }

    /**
     * This method displays the TableView with appointments for all times.  Sorted by Start Time.
    */
    public void allSelected() {
        dataTable.setItems(appointments_table.getAppointmentsWithContacts());
        startColumn.setSortType(TableColumn.SortType.ASCENDING);
        ObservableList<TableColumn<appointmentContact, ?>> sortOrder = dataTable.getSortOrder();
        sortOrder.clear();
        sortOrder.add(startColumn);
    }

    /**
     * This method displays the TableView with appointments that appear for the upcoming week when selected.  Sorted by Start Time.
     * In regards to the lambda expression used, it is used to set up the filter for showing appointments only for the next week.
     * It returns a conditional statement that checks for appointments between the current date and time and 7 days from the current date and time.
     */
    public void weekSelected() {
        FilteredList<appointmentContact> weekDataTable = new FilteredList<>(appointments_table.getAppointmentsWithContacts());
        weekDataTable.setPredicate(entry -> {
            LocalDateTime startTime = entry.getStart().toLocalDateTime();
            LocalDateTime timeNow = LocalDateTime.now();
            LocalDateTime weekAhead = timeNow.plusDays(7);
            return startTime.isAfter(timeNow) && startTime.isBefore(weekAhead);
        });
        dataTable.setItems(weekDataTable);
        SortedList<appointmentContact> sortById = new SortedList<>(weekDataTable);
        sortById.comparatorProperty().bind(dataTable.comparatorProperty());
        dataTable.setItems(sortById);
        startColumn.setSortType(TableColumn.SortType.ASCENDING);
        ObservableList<TableColumn<appointmentContact, ?>> sortOrder = dataTable.getSortOrder();
        sortOrder.clear();
        sortOrder.add(startColumn);
    }

    /**
     * This method displays the TableView with appointments that appear for the upcoming month when selected.  Sorted by Start Time.
     * In regards to the lambda expression used, it is used to set up the filter for showing appointments only for the next month.
     * It returns a conditional statement that checks for appointments between the current date and time and 30 days (average month) from the current date and time.
     */
    public void monthSelected() {
        FilteredList<appointmentContact> monthDataTable = new FilteredList<>(appointments_table.getAppointmentsWithContacts());
        monthDataTable.setPredicate(entry -> {
            LocalDateTime startTime = entry.getStart().toLocalDateTime();
            LocalDateTime timeNow = LocalDateTime.now();
            LocalDateTime monthAhead = timeNow.plusDays(30);
            return startTime.isAfter(timeNow) && startTime.isBefore(monthAhead);
        });
        SortedList<appointmentContact> sortById = new SortedList<>(monthDataTable);
        sortById.comparatorProperty().bind(dataTable.comparatorProperty());
        dataTable.setItems(sortById);
        startColumn.setSortType(TableColumn.SortType.ASCENDING);
        ObservableList<TableColumn<appointmentContact, ?>> sortOrder = dataTable.getSortOrder();
        sortOrder.clear();
        sortOrder.add(startColumn);
    }
}
