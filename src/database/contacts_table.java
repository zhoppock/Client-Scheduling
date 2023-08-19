package database;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.contacts;

import java.sql.*;

/**
 * This class handles all the database queries and such regarding the Contacts table.
 * @author Zachary Hoppock
 */
public class contacts_table {

    /**
     * This method runs a database query to pull all contacts from the Contacts table in the database.
     * @return an ObservableList of all contacts
     */
    public static ObservableList<contacts> getAllContacts() {

        ObservableList<contacts> contactList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM contacts";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");
                contacts contact = new contacts(contactId, contactName, contactEmail);
                contactList.add(contact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contactList;
    }

    /**
     * This method runs a database query to get the Contact ID of a specified Contact Name.
     * Used in the Add and Update Appointment screens.
     * @param contactName the name of the specified contact
     * @return the ID of the specified contact
     */
    public static int getContactId(String contactName) {
        int contactId = 0;
        try {
            ResultSet resultSet;
            String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, contactName);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            contactId = resultSet.getInt("Contact_ID");
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return contactId;
    }

    /**
     * This method runs a database query to get the Contact Name of a specified Contact ID.
     * Used in the Contact Appointments Report screen.
     * @param contactId the ID of the specified contact
     * @return the name of the specified contact
     */
    public static String getContactName(int contactId) {
        String contactName = null;
        try {
            ResultSet resultSet;
            String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            contactName = resultSet.getString("Contact_Name");
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return contactName;
    }
}
