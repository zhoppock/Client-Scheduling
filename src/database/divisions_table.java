package database;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.first_level_divisions;

import java.sql.*;

/**
 * This class handles all the database queries and such regarding the Divisions table.
 * @author Zachary Hoppock
 */
public class divisions_table {

    /**
     * This method runs a database query to get a list of Divisions based on a specified Country.
     * @param selectedCountryId the ID of the specified Country
     * @return an ObservableList of Divisions associated with the specified Country
     */
    public static ObservableList<first_level_divisions> getDivisionsByCountry(int selectedCountryId) {

        ObservableList<first_level_divisions> divisionList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, selectedCountryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                Date divisionCreateDate = resultSet.getDate("Create_Date");
                String divisionCreatedBy = resultSet.getString("Created_By");
                Timestamp divisionLastUpdate = resultSet.getTimestamp("Last_Update");
                String divisionLastUpdatedBy = resultSet.getString("Last_Updated_By");
                int countryId = resultSet.getInt("Country_ID");
                first_level_divisions division = new first_level_divisions(divisionId, divisionName, divisionCreateDate, divisionCreatedBy, divisionLastUpdate, divisionLastUpdatedBy, countryId);
                divisionList.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionList;
    }

    /**
     * This method runs a database query to get the Division ID of a specified Division Name.
     * Used in the Add and Update Customer screens.
     * @param divisionName the name of the specified division
     * @return the ID of the specified division
     */
    public static int getDivisionId(String divisionName) {
        int divisionId = 0;
        try {
            ResultSet resultSet;
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, divisionName);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            divisionId = resultSet.getInt("Division_ID");
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return divisionId;
    }
}
