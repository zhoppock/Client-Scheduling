package database;

import helper.JDBC;
import model.users;
import controller.login_control;

import java.sql.*;

/**
 * This class handles all the database queries and such regarding the Users table.
 * @author Zachary Hoppock
 */
public class users_table {

    /**
     * This method runs a database query to take selected Start and End times from the Add or Update Appointment screens
     * and compares them to the Start and End times from a list of the Customer's existing appointments.
     * @param selectedCustomerId the ID of the Customer associated with the appointment
     * @param startTime the selected Start time for the appointment (either being added or updated)
     * @param endTime the selected End time for the appointment (either being added or updated)
     * @return returns True if the selected times overlaps a timeframe from the list and False if it does not
     */
    /**
     * This method runs a database query to take an inputted Username and Password from the login screen
     * and compares them to the Usernames and Passwords from a list of the database's Users.
     * @param username the inputted username
     * @param password the inputted password
     * @return a resultSet that will be null if the username and password do not match and a value if they do
     */
    public static ResultSet userValidation(String username, String password) {
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? and Password = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return resultSet;
    }

    /**
     * This method gets the current user that logged into the application.
     * @return the user that logged in
     */
    public static users getCurrentUser() {
        return login_control.current_user;
    }
}
