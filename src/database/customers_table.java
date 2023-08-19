package database;

import helper.JDBC;
import helper.customerDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import controller.customers_control;

import java.sql.*;

/**
 * This class handles all the database queries and such regarding the Customers table.
 * @author Zachary Hoppock
 */
public class customers_table {

    /**
     * This method runs a database query to pull all customers with specified columns and associated Division name.
     * @return returns an ObservableList of customerDivisions based on the given selection query
     */
    public static ObservableList<customerDivision> getCustomersWithDivisions() {

        ObservableList<customerDivision> customerList = FXCollections.observableArrayList();
        try {
            String sqlStatement = """
                    SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Create_Date,\s
                                        customers.Created_by, customers.Last_Update, customers.Last_Updated_By, customers.Division_ID, Division, Country
                                        FROM customers
                                        JOIN first_level_divisions
                                        ON customers.Division_ID = first_level_divisions.Division_ID
                                        JOIN countries
                                        ON first_level_divisions.Country_ID = countries.Country_ID""";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String customerAddress = resultSet.getString("Address");
                String customerPostalCode = resultSet.getString("Postal_Code");
                String customerPhone = resultSet.getString("Phone");
                Date customerCreateDate = resultSet.getDate("Create_Date");
                String customerCreatedBy = resultSet.getString("Created_By");
                Timestamp customerLastUpdate = resultSet.getTimestamp("Last_Update");
                String customerLastUpdatedBy = resultSet.getString("Last_Updated_By");
                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                String countryName = resultSet.getString("Country");
                customerDivision customerDivision = new customerDivision(customerId, customerName, customerAddress, customerPostalCode, customerPhone,
                        customerCreateDate, customerCreatedBy, customerLastUpdate, customerLastUpdatedBy, divisionId, divisionName, countryName);
                customerList.add(customerDivision);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }

    /**
     * This method runs a database query to add a customer with specified details to its respective table in the database.
     * @param name the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone the phone number of the customer
     * @param createDate the date and time the customer was created
     * @param createdBy the user who created the customer
     * @param lastUpdate the date and time the customer was last updated
     * @param lastUpdatedBy the user who last updated the customer
     * @param divisionId the ID of the division associated with the customer
     * @return the number of rows affected in the query update, used to confirm if the customer was added or not
     */
    public static int addCustomer(String name, String address, String postalCode, String phone, Timestamp createDate,
                                  String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        int rowsAffected = 0;
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, Division_ID)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, createDate);
            ps.setString(6, createdBy);
            ps.setTimestamp(7, lastUpdate);
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, divisionId);
            rowsAffected = ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return rowsAffected;
    }

    /**
     * This method runs a database query to delete a selected customer from its respective table in the database.
     * @param customerId the ID of the customer selected for deletion
     * @return the number of rows affected in the query update, used to confirm if the customer was deleted or not
     */
    public static int deleteCustomer(int customerId){
        int rowsAffected = 0;
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            rowsAffected = ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return rowsAffected;
    }

    /**
     * This method runs a database query to get the Customer Name of a specified Customer ID.
     * Used in the Customer Appointments Report screen.
     * @param customerId the ID of the specified customer
     * @return the name of the specified customer
     */
    public static String getCustomerName(int customerId) {
        String customerName = null;
        try {
            ResultSet resultSet;
            String sql = "SELECT Customer_Name FROM customers WHERE Customer_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            customerName = resultSet.getString("Customer_Name");
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return customerName;
    }

    /**
     * This method takes a selected customer (with division name) from the Customers screen and takes it and its details to the Update Customers screen.
     * @return the selected customerDivision from the Customers screen
     */
    public static customerDivision getSelectedCustomer() {
        return customers_control.selectedCustomer;
    }

    /**
     * This method runs a database query to update a selected customer with specified details to its respective table in the database.
     * @param name the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone the phone number of the customer
     * @param lastUpdate the date and time the customer was last updated
     * @param lastUpdatedBy the user who last updated the customer
     * @param divisionId the ID of the division associated with the customer
     * @param customerId the ID of the selected Customer to be updated
     * @return the number of rows affected in the query update, used to confirm if the customer was updated or not
     */
    public static int updateCustomer(String name, String address, String postalCode, String phone,
                                  Timestamp lastUpdate, String lastUpdatedBy, int divisionId, int customerId) {
        int rowsAffected = 0;
        try {
            String sql = "UPDATE customers " +
                    "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, " +
                    "Last_Updated_By = ?, Division_ID = ? " +
                    "WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, lastUpdate);
            ps.setString(6, lastUpdatedBy);
            ps.setInt(7, divisionId);
            ps.setInt(8, customerId);
            rowsAffected = ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return rowsAffected;
    }
}
