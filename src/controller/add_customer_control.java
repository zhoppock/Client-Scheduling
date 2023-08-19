package controller;

import database.*;
import helper.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class allows you to create a customer with specified details.
 * It implements the Initializable interface and extends the callDirectory class from the helper package
 * @author Zachary Hoppock
 */
public class add_customer_control extends callDirectory implements Initializable {

    public ComboBox<String> countryCombo;
    public ComboBox<String> divisionCombo;
    public TextField idTxt;
    public TextField nameTxt;
    public TextField addressTxt;
    public TextField postalTxt;
    public TextField phoneTxt;
    public static users current_user;
    public static timeZones currentZoneIds;

    /**
     * This method initializes the Add Customer screen of the application,
     * which includes displaying selectable country and division.
     * @param url a standard parameter when running JavaFX initialization
     * @param resourceBundle a standard parameter when running JavaFX initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Customer Page Initialized");
        current_user = users_table.getCurrentUser();
        ObservableList<countries> countryList = countries_table.getAllCountries();
        for (countries country : countryList) {
            countryCombo.getItems().add(country.getName());
        }
        currentZoneIds = timesAndZoneIds.getZoneIds();
        System.out.println("Current time zones: Local - " + currentZoneIds.getMyZoneId() + "; Eastern - " + currentZoneIds.getEasternZoneId()
                + "; UTC - " + currentZoneIds.getUtcZoneId());
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
     * This method will save your newly made customer to the database and take you back to the Customers screen of the application.
     * A warning message will appear if you have not filled in all the parameters.
     * @param actionEvent the Action Event of pressing the Save button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void saveNewCustomer(ActionEvent actionEvent) throws IOException {
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
        } else {
            Timestamp systemTimestamp = Timestamp.valueOf(timesAndZoneIds.convertZDTs(LocalDateTime.now(), currentZoneIds.getMyZoneId(),currentZoneIds.getUtcZoneId()));
            int rowsAffected = customers_table.addCustomer(nameTxt.getText(), addressTxt.getText(), postalTxt.getText(),
                    phoneTxt.getText(), systemTimestamp, current_user.getName(),
                    systemTimestamp, current_user.getName(), divisions_table.getDivisionId(divisionCombo.getValue()));
            if (rowsAffected > 0) {
                System.out.println(" - Customer Added Successfully!");
                callUpDirectory(actionEvent, "customers.fxml");
            } else {
                System.out.println(" - Failure Adding Customer!");
            }
        }
    }

    /**
     * This method takes you back to the Customers screen of the application.
     * A warning message will appear if you had filled in any of the parameters.
     * @param actionEvent the Action Event of pressing the Cancel button in order to change the scene
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void cancelNewCustomer(ActionEvent actionEvent) throws IOException {
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
        if(nameTxt.getText().isEmpty() && addressTxt.getText().isEmpty() && postalTxt.getText().isEmpty() && phoneTxt.getText().isEmpty()
                && countryCombo.getSelectionModel().isEmpty() && divisionCombo.getSelectionModel().isEmpty()) {
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
     * This method enables the Division combo box once a selection has been made in the Country combo box.
     * Also, whenever the selection in the Country combo box is changed,
     * it will reset the selection in the Division combo box to values associated with the selected Country.
     */
    public void countrySelected() {
        if(!(countryCombo.getSelectionModel().isEmpty())) {
            divisionCombo.setDisable(false);
            divisionCombo.getItems().clear();
            divisionCombo.setPromptText("Select Division");
            ObservableList<first_level_divisions> divisionList = divisions_table.getDivisionsByCountry(countries_table.getCountryId(countryCombo.getValue()));
            for (first_level_divisions divisions : divisionList) {
                divisionCombo.getItems().add(divisions.getName());
            }
        }
    }
}
