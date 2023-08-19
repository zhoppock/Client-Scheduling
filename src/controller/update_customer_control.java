package controller;

import database.*;
import helper.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.countries;
import model.first_level_divisions;
import model.users;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class allows you to update the details of a selected customer.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class update_customer_control extends callDirectory implements Initializable {

    public ComboBox<String> countryCombo;
    public ComboBox<String> divisionCombo;
    public TextField idTxt;
    public TextField nameTxt;
    public TextField addressTxt;
    public TextField postalTxt;
    public TextField phoneTxt;
    public static users current_user;
    public static customerDivision selectedCustomer;
    public static timeZones currentZoneIds;

    /**
     * This method initializes the Update Customer screen of the application,
     * which includes displaying all the details from the selected customer.
     * @param url a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Update Customer Page Initialized");
        current_user = users_table.getCurrentUser();
        selectedCustomer = customers_table.getSelectedCustomer();

        idTxt.setText(String.valueOf(selectedCustomer.getId()));
        nameTxt.setText(selectedCustomer.getName());
        addressTxt.setText(selectedCustomer.getAddress());
        postalTxt.setText(selectedCustomer.getPostalCode());
        phoneTxt.setText(selectedCustomer.getPhoneNumber());

        ObservableList<countries> countryList = countries_table.getAllCountries();
        for (countries country : countryList) {
            countryCombo.getItems().add(country.getName());
        }
        countryCombo.setValue(selectedCustomer.getCountry());
        ObservableList<first_level_divisions> divisionList = divisions_table.getDivisionsByCountry(countries_table.getCountryId(countryCombo.getValue()));
        for (first_level_divisions divisions : divisionList) {
            divisionCombo.getItems().add(divisions.getName());
        }
        divisionCombo.setValue(selectedCustomer.getDivision());
        currentZoneIds = timesAndZoneIds.getZoneIds();
        System.out.println("Current time zones: Local - " + currentZoneIds.getMyZoneId() + "; Eastern - " + currentZoneIds.getEasternZoneId()
                + "; UTC - " + currentZoneIds.getUtcZoneId());
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
     * This method will save your updates of the selected customer to the database
     * and take you back to the Customers screen of the application.
     * A warning message will appear if any of the parameters are not filled in.
     * A message will also appear if you haven't made any changes and will send you back to the Customers screen of the application.
     * @param actionEvent the Action Event of pressing the Save button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void saveCustomer(ActionEvent actionEvent) throws IOException {
        if (nameTxt.getText().isEmpty() || addressTxt.getText().isEmpty() || postalTxt.getText().isEmpty() || phoneTxt.getText().isEmpty()
                || divisionCombo.getSelectionModel().isEmpty()) {
            String inputWarning = "Please fill in the following parameters:";
            if(nameTxt.getText().isEmpty()){inputWarning += "\n - Enter a name";}
            if(addressTxt.getText().isEmpty()){inputWarning += "\n - Enter an address";}
            if(postalTxt.getText().isEmpty()){inputWarning += "\n - Enter a postal code";}
            if(phoneTxt.getText().isEmpty()){inputWarning += "\n - Enter a phone number";}
            if(divisionCombo.getSelectionModel().isEmpty()){inputWarning += "\n - Select a division";}
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Warning");
            alert.setContentText(inputWarning);
            alert.showAndWait();
        } else if (nameTxt.getText().equals(selectedCustomer.getName()) && addressTxt.getText().equals(selectedCustomer.getAddress())
                && postalTxt.getText().equals(selectedCustomer.getPostalCode()) && phoneTxt.getText().equals(selectedCustomer.getPhoneNumber())
                && divisionCombo.getValue().equals(selectedCustomer.getDivision())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Warning");
            alert.setContentText("No changes were made.");
            alert.showAndWait();
            callUpDirectory(actionEvent, "customers.fxml");
        } else {
            Timestamp systemTimestamp = Timestamp.valueOf(timesAndZoneIds.convertZDTs(LocalDateTime.now(), currentZoneIds.getMyZoneId(),currentZoneIds.getUtcZoneId()));
            int rowsAffected = customers_table.updateCustomer(nameTxt.getText(), addressTxt.getText(), postalTxt.getText(),
                    phoneTxt.getText(), systemTimestamp, current_user.getName(),
                    divisions_table.getDivisionId(divisionCombo.getValue()), selectedCustomer.getId());
            if (rowsAffected > 0) {
                System.out.println(" - Customer Updated Successfully!");
                callUpDirectory(actionEvent, "customers.fxml");
            } else {
                System.out.println(" - Failure Updating Customer!");
            }
        }
    }

    /**
     * This method takes you back to the Customers screen of the application.
     * A warning message will appear if you had updated any of the parameters.
     * @param actionEvent the Action Event of pressing the Cancel button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void cancelCustomer(ActionEvent actionEvent) throws IOException {
        warningMessage(actionEvent,"customers.fxml");
    }

    /**
     * This method displays a warning message if any of the parameters of the window have been changed
     * and you choose to exit the window without changing them back (excluding the Save button of course).
     * @param event the Action Event of pressing the respective method button in order to change the scene
     * @param directory the directory of the file that the application will change scenes to
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void warningMessage(ActionEvent event, String directory) throws IOException {
        if (nameTxt.getText().equals(selectedCustomer.getName()) && addressTxt.getText().equals(selectedCustomer.getAddress())
                && postalTxt.getText().equals(selectedCustomer.getPostalCode()) && phoneTxt.getText().equals(selectedCustomer.getPhoneNumber())
                && divisionCombo.getValue().equals(selectedCustomer.getDivision())) {
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
     * This method will reset the selection in the Division combo box if the selection in the Country combo box is changed.
     * Division values are displayed based of the selected Country.
     */
    public void countrySelected() {
        if(!(countryCombo.getSelectionModel().isEmpty())) {
            divisionCombo.getItems().clear();
            divisionCombo.setPromptText("Select Division");
            ObservableList<first_level_divisions> divisionList = divisions_table.getDivisionsByCountry(countries_table.getCountryId(countryCombo.getValue()));
            for (first_level_divisions divisions : divisionList) {
                divisionCombo.getItems().add(divisions.getName());
            }
        }
    }
}
