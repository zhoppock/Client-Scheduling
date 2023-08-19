package controller;
import database.appointments_table;
import helper.callDirectory;

import database.customers_table;
import helper.customerDivision;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class displays all customers from the database.
 * There are options to add, update, or delete customers and to add an appointment for a customer.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class customers_control extends callDirectory implements Initializable {

    public TableColumn<customerDivision, Integer> idColumn;
    public TableColumn<customerDivision, String> nameColumn;
    public TableColumn<customerDivision, String> addressColumn;
    public TableColumn<customerDivision, String> postalCodeColumn;
    public TableColumn<customerDivision, String> phoneColumn;
    public TableColumn<customerDivision, String> countryColumn;
    public TableColumn<customerDivision, String> divisionColumn;
    public TableView<customerDivision> dataTable;
    public static customerDivision selectedCustomer;

    /**
     * This method initializes the Customers screen of the application,
     * which includes displaying all customers sorted by ID number.
     * @param url a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Customers Page Initialized");

        dataTable.setItems(customers_table.getCustomersWithDivisions());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        idColumn.setSortType(TableColumn.SortType.ASCENDING);
        ObservableList<TableColumn<customerDivision, ?>> sortOrder = dataTable.getSortOrder();
        sortOrder.clear();
        sortOrder.add(idColumn);
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
     * This method takes you the Add Customer screen.
     * @param actionEvent the Action Event of pressing the Add Customer button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToAddCustomer(ActionEvent actionEvent) throws IOException {
        callUpDirectory(actionEvent,"add_customer.fxml");
    }

    /**
     * This method takes you the Update Customer screen with the details of a selected customer.
     * A warning message will appear if you have not selected a customer from the TableView.
     * @param actionEvent the Action Event of pressing the Update Customer button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToUpdateCustomer(ActionEvent actionEvent) throws IOException {
        selectedCustomer = dataTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            callUpDirectory(actionEvent, "update_customer.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Warning");
            alert.setContentText("No customer has been selected to update.");
            alert.showAndWait();
        }
    }

    /**
     * This method allows you to delete a selected customer, asking you if you are sure you want it deleted.
     * The window will refresh to reflect the the customer is no longer there.
     * A warning message will appear if you have not selected a customer from the TableView.
     * A warning message will also appear if the customer has any associated appointments and stop the deletion process.
     * @param actionEvent the Action Event of pressing the Delete Customer button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void deleteCustomer(ActionEvent actionEvent) throws IOException {
        selectedCustomer = dataTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deletion Warning");
            alert.setContentText("Are you sure you want to delete this Customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK && selectedCustomer.getId() != appointments_table.getAppointmentCustomerId(selectedCustomer.getId())) {
                int rowsAffected  = customers_table.deleteCustomer(selectedCustomer.getId());
                if(rowsAffected > 0) {
                    System.out.println("Customer Successfully Deleted.");
                    callUpDirectory(actionEvent,"customers.fxml");
                }
                else {
                    System.out.println("Customer Failed To Delete.");
                }
            } else {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Deletion Warning");
                warning.setContentText("Customer has existing appointments.  Deletion aborted.");
                warning.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Warning");
            alert.setContentText("No customer has been selected for deletion.");
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
     * This method takes you the Add Appointment screen based on a selected customer.
     * A warning message will appear if you have not selected a customer from the TableView.
     * @param actionEvent the Action Event of pressing the Add Appointment button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void goToAddAppointment(ActionEvent actionEvent) throws IOException {
        selectedCustomer = dataTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null) {
            callUpDirectory(actionEvent,"add_appointment.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Warning");
            alert.setContentText("Please select a customer to make an appointment for.");
            alert.showAndWait();
        }
    }
}
