package database;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.countries;

import java.sql.*;

/**
 * This class handles all the database queries and such regarding the Countries table.
 * @author Zachary Hoppock
 */
public class countries_table {

    /**
     * This method runs a database query to pull all countries from the Countries table in the database.
     * @return an ObservableList of all countries
     */
    public static ObservableList<countries> getAllCountries() {

        ObservableList<countries> countryList = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT * FROM countries";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int countryId = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                Date countryCreateDate = resultSet.getDate("Create_Date");
                String countryCreatedBy = resultSet.getString("Created_By");
                Timestamp countryLastUpdate = resultSet.getTimestamp("Last_Update");
                String countryLastUpdatedBy = resultSet.getString("Last_Updated_By");
                countries country = new countries(countryId, countryName, countryCreateDate, countryCreatedBy, countryLastUpdate, countryLastUpdatedBy);
                countryList.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countryList;
    }

    /**
     * This method runs a database query to get the Country ID of a specified Country Name.
     * Used in the Add and Update Customer screens.
     * @param countryName the name of the specified country
     * @return the ID of the specified country
     */
    public static int getCountryId(String countryName) {
        int countryId = 0;
        try {
            ResultSet resultSet;
            String sql = "SELECT Country_ID FROM countries WHERE Country = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setString(1, countryName);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            countryId = resultSet.getInt("Country_ID");
        } catch(SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return countryId;
    }
}
