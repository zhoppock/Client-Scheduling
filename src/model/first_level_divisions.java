package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * This class creates a first level division with specified details.
 * @author Zachary Hoppock
 */
public class first_level_divisions {

    private int id;
    private String name;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryId;

    /**
     * This method is the constructor for a first level division.
     * @param id the division constructor ID
     * @param name the division constructor name
     * @param createDate the division constructor create date
     * @param createdBy the division constructor created by value
     * @param lastUpdate the division constructor last updated value
     * @param lastUpdatedBy the division constructor last updated by value
     * @param countryId the division constructor country ID
     */
    public first_level_divisions(int id, String name, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryId) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /**
     * This method retrieves the division's ID.
     * @return the division ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method assigns a value to the division's ID.
     * @param id the division ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method retrieves the division's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * This method assigns a value to the division's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
    public int getCountryId() {
        return countryId;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
