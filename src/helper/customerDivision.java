package helper;

import java.sql.Timestamp;
import java.util.Date;

/**
 * This class creates a customer with specified details and a Division name.
 * It extends the appointments class from the model package.
 * @author Zachary Hoppock
 */
public class customerDivision extends model.customers {

    private String division;
    private String country;

    /**
     * This method is the constructor for a customerDivision.
     * @param id the customerDivision constructor ID
     * @param name the customerDivision constructor name
     * @param address the customerDivision constructor address
     * @param postalCode the customerDivision constructor postal code
     * @param phoneNumber the customerDivision constructor phone number
     * @param createDate the customerDivision constructor create date
     * @param createdBy the customerDivision constructor created by value
     * @param lastUpdate the customerDivision constructor last update value
     * @param lastUpdatedBy the customerDivision constructor last updated value
     * @param divisionId the customerDivision constructor division ID
     * @param division the customerDivision constructor division name
     * @param country the customerDivision constructor country name
     */
    public customerDivision(int id, String name, String address, String postalCode, String phoneNumber, Date createDate,
                            String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId, String division, String country) {
        super(id, name, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
        this.division = division;
        this.country = country;
    }

    /**
     * This method retrieves the countryDivision's division name.
     * @return the division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * This method retrieves the countryDivision's country name.
     * @return the division name
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method assigns a value to the customerDivision's country name.
     * @param country the country name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
