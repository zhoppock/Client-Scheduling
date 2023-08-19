package database;

import controller.appointments_control;
import helper.JDBC;
import helper.appointmentContact;
import helper.timeZones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointments;
import helper.timesAndZoneIds;

import java.sql.*;

/**
 * This class handles all the database queries and such regarding the Appointments table.
 * @author Zachary Hoppock
 */
public class appointments_table {

    public static timeZones currentZoneIds;

    /**
     * This method runs a database query to pull all appointments and sort them by Type and then the month of the Start time.
     * @return returns an ObservableList of appointments based on the given selection query
     */
    public static ObservableList<appointments> getAppointmentsSortByTypeAndMonth() {

        ObservableList<appointments> appointmentList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments ORDER BY Type, MONTH(Start)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            appointmentList = appointments_table.grabAppointments(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * This method runs a database query to pull all appointments and sort them by Contact ID and then Start time.
     * @return returns an ObservableList of appointments based on the given selection query
     */
    public static ObservableList<appointments> getAppointmentsSortByContactAndStart() {

        ObservableList<appointments> appointmentList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments ORDER BY Contact_ID, Start";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            appointmentList = appointments_table.grabAppointments(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * This method runs a database query to pull all appointments and sort them by Customer ID and then the year of the Start time.
     * @return returns an ObservableList of appointments based on the given selection query
     */
    public static ObservableList<appointments> getAppointmentsSortByCustomerAndYear() {

        ObservableList<appointments> appointmentList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments ORDER BY Customer_ID, YEAR(Start)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            appointmentList = appointments_table.grabAppointments(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * This method runs a database query to pull all appointments with specified columns and associated Contact name.
     * @return returns an ObservableList of appointmentContacts based on the given selection query
     */
    public static ObservableList<appointmentContact> getAppointmentsWithContacts() {

        ObservableList<appointmentContact> appointmentList = FXCollections.observableArrayList();
        try {
            String sqlStatement = """
                    SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_by, \s
                                        Last_Update, Last_Updated_By, Customer_ID, User_ID, appointments.Contact_ID, Contact_Name
                                        FROM appointments
                                        JOIN contacts
                                        ON appointments.Contact_ID = contacts.Contact_ID""";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    int appointmentId = resultSet.getInt("Appointment_ID");
                    String appointmentTitle = resultSet.getString("Title");
                    String appointmentDescription = resultSet.getString("Description");
                    String appointmentLocation = resultSet.getString("Location");
                    String appointmentType = resultSet.getString("Type");

                    currentZoneIds = timesAndZoneIds.getZoneIds();
                    Timestamp appointmentStart = Timestamp.valueOf(timesAndZoneIds.convertZDTs(resultSet.getTimestamp("Start").toLocalDateTime(), currentZoneIds.getUtcZoneId(), currentZoneIds.getMyZoneId()));
                    Timestamp appointmentEnd = Timestamp.valueOf(timesAndZoneIds.convertZDTs(resultSet.getTimestamp("End").toLocalDateTime(), currentZoneIds.getUtcZoneId(), currentZoneIds.getMyZoneId()));

                    Timestamp appointmentCreateDate = resultSet.getTimestamp("Create_Date");
                    String appointmentCreatedBy = resultSet.getString("Created_By");
                    Timestamp appointmentLastUpdate = resultSet.getTimestamp("Last_Update");
                    String appointmentLastUpdatedBy = resultSet.getString("Last_Updated_By");
                    int customerId = resultSet.getInt("Customer_ID");
                    int userId = resultSet.getInt("User_ID");
                    int contactId = resultSet.getInt("Contact_ID");
                    String contactName = resultSet.getString("Contact_Name");
                    appointmentContact appointmentContact = new appointmentContact(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation,
                            appointmentType, appointmentStart, appointmentEnd, appointmentCreateDate, appointmentCreatedBy, appointmentLastUpdate,
                            appointmentLastUpdatedBy, customerId, userId, contactId, contactName);
                    appointmentList.add(appointmentContact);
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * This method runs a database query to add an appointment with specified details to its respective table in the database.
     * @param title the title of the appointment
     * @param description a description of the appointment
     * @param location the location of the appointment
     * @param type the type of appointment
     * @param startTime the starting date and time of the appointment
     * @param endTime the ending date and time of the appointment
     * @param createDate the date and time the appointment was created
     * @param createdBy the user who created the appointment
     * @param lastUpdate the date and time the appointment was last updated
     * @param lastUpdatedBy the user who last updated the appointment
     * @param customerId the ID of the customer associated with the appointment
     * @param userId the ID of the user associated with the appointment
     * @param contactId the ID of the contact associated with the appointment
     * @return the number of rows affected in the query update, used to confirm if the appointment was added or not
     */
    public static int addAppointment(String title, String description, String location, String type, Timestamp startTime,
                                     Timestamp endTime, Timestamp createDate, String createdBy, Timestamp lastUpdate,
                                     String lastUpdatedBy, int customerId, int userId, int contactId) {
        int rowsAffected = 0;
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, " +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startTime);
            ps.setTimestamp(6, endTime);
            ps.setTimestamp(7, createDate);
            ps.setString(8, createdBy);
            ps.setTimestamp(9, lastUpdate);
            ps.setString(10, lastUpdatedBy);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);
            rowsAffected = ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return rowsAffected;
    }

    /**
     * This method runs a database query to delete a selected appointment from its respective table in the database.
     * @param appointmentId the ID of the appointment selected for deletion
     * @return the number of rows affected in the query update, used to confirm if the appointment was deleted or not
     */
    public static int deleteAppointment(int appointmentId){
        int rowsAffected = 0;
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            rowsAffected = ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return rowsAffected;
    }

    /**
     * This method runs a database query to take the Customer ID of a customer from the Customers table
     * and returns its associated Customer ID from the Appointments table.
     * This is used in the Customers screen to assist when deleting Customers to show if they have appointments or not.
     * @param selectCustomerId the Customer ID from a Customers Table entry
     * @return the Customer ID from an Appointments Table entry
     */
    public static int getAppointmentCustomerId(int selectCustomerId) {
        int customerId = 0;
        try {
            ResultSet resultSet;
            String sql = "SELECT Customer_ID FROM appointments WHERE Customer_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, selectCustomerId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            customerId = resultSet.getInt("Customer_ID");
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return customerId;
    }

    /**
     * This method takes a selected appointment (with contact name) from the Appointments screen and takes it and its details to the Update Appointments screen.
     * @return the selected appointmentContact from the Appointments screen
     */
    public static appointmentContact getSelectedAppointment() {
        return appointments_control.selectedAppointment;
    }

    /**
     * This method runs a database query to update a selected appointment with specified details to its respective table in the database.
     * @param title the title of the appointment
     * @param description a description of the appointment
     * @param location the location of the appointment
     * @param type the type of appointment
     * @param startTime the starting date and time of the appointment
     * @param endTime the ending date and time of the appointment
     * @param lastUpdate the date and time the appointment was last updated
     * @param lastUpdatedBy the user who last updated the appointment
     * @param customerId the ID of the customer associated with the appointment
     * @param userId the ID of the user associated with the appointment
     * @param contactId the ID of the contact associated with the appointment
     * @param appointmentId the ID of the selected Appointment to be updated
     * @return the number of rows affected in the query update, used to confirm if the appointment was updated or not
     */
    public static int updateAppointment(String title, String description, String location, String type, Timestamp startTime,
                                        Timestamp endTime, Timestamp lastUpdate, String lastUpdatedBy, int customerId,
                                        int userId, int contactId, int appointmentId) {
        int rowsAffected = 0;
        try {
            String sql = "UPDATE appointments " +
                    "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                    "WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startTime);
            ps.setTimestamp(6, endTime);
            ps.setTimestamp(7, lastUpdate);
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            ps.setInt(12, appointmentId);
            rowsAffected = ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return rowsAffected;
    }

    /**
     * This method runs a database query to pull up a list of appointment for a specified customer.
     * @param selectedCustomerId the ID of the Customer associated with the appointment
     * @return an ObservableList of appointments for the specified customer
     */
    public static ObservableList<appointments> getAppointmentsByCustomer(int selectedCustomerId) {

        ObservableList<appointments> appointmentList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments WHERE Customer_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, selectedCustomerId);
            appointmentList = appointments_table.grabAppointments(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * This method runs a database query to pull up a list of appointment for a specified user.
     * @param selectedUserId the ID of the user to check appointments for
     * @return an ObservableList of appointments for the specified user
     */
    public static ObservableList<appointments> getAppointmentsByUser(int selectedUserId) {

        ObservableList<appointments> appointmentList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments WHERE User_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, selectedUserId);
            appointmentList = appointments_table.grabAppointments(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * This method runs a database query to pull up a list of appointments with a specific type.
     * @param selectedType the type of appointment to be queried for
     * @return an ObservableList of a specific appointment type.
     */
    public static ObservableList<appointments> getAppointmentTypes(String selectedType) {

        ObservableList<appointments> appointmentList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM appointments WHERE Type = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setString(1, selectedType);
            appointmentList = appointments_table.grabAppointments(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * This method is used by certain other methods of this class to pull up a list of appointments based of a specified query.
     * @param ps the prepared statement to execute the query with
     * @return an ObservableList of appointments specified by the prepared statement
     */
    public static ObservableList<appointments> grabAppointments(PreparedStatement ps) {
        ObservableList<appointments> appointmentList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()) {
                int appointmentId = resultSet.getInt("Appointment_ID");
                String appointmentTitle = resultSet.getString("Title");
                String appointmentDescription = resultSet.getString("Description");
                String appointmentLocation = resultSet.getString("Location");
                String appointmentType = resultSet.getString("Type");

                currentZoneIds = timesAndZoneIds.getZoneIds();
                Timestamp appointmentStart = Timestamp.valueOf(timesAndZoneIds.convertZDTs(resultSet.getTimestamp("Start").toLocalDateTime(), currentZoneIds.getUtcZoneId(), currentZoneIds.getMyZoneId()));
                Timestamp appointmentEnd = Timestamp.valueOf(timesAndZoneIds.convertZDTs(resultSet.getTimestamp("End").toLocalDateTime(), currentZoneIds.getUtcZoneId(), currentZoneIds.getMyZoneId()));

                Timestamp appointmentCreateDate = resultSet.getTimestamp("Create_Date");
                String appointmentCreatedBy = resultSet.getString("Created_By");
                Timestamp appointmentLastUpdate = resultSet.getTimestamp("Last_Update");
                String appointmentLastUpdatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");
                appointments appointment = new appointments(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation,
                        appointmentType, appointmentStart, appointmentEnd, appointmentCreateDate, appointmentCreatedBy, appointmentLastUpdate,
                        appointmentLastUpdatedBy, customerId, userId, contactId);
                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }
}
