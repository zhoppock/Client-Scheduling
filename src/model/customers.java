package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * This class creates a customer with specified details.
 * @author Zachary Hoppock
 */
public class customers {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;

    /**
     * This method is the constructor for a customer.
     * @param id the customer constructor ID
     * @param name the customer constructor name
     * @param address the customer constructor address
     * @param postalCode the customer constructor postal code
     * @param phoneNumber the customer constructor phone number
     * @param createDate the customer constructor create date
     * @param createdBy the customer constructor created by value
     * @param lastUpdate the customer constructor last update value
     * @param lastUpdatedBy the customer constructor last updated by value
     * @param divisionId the customer constructor division ID
     */
    public customers(int id, String name, String address, String postalCode, String phoneNumber, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /**
     * This method retrieves the customer's ID.
     * @return the customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method assigns a value to the customer's ID.
     * @param id the customer ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method retrieves the customer's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * This method assigns a value to the customer's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method retrieves the customer's address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method retrieves the customer's postal code.
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * This method retrieves the customer's phone number.
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Unused getter, kept for POJO purposes.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Unused getter, kept for POJO purposes.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Unused getter, kept for POJO purposes.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Unused getter, kept for POJO purposes.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Unused getter, kept for POJO purposes.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
