package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * This class creates a country with specified details.
 * @author Zachary Hoppock
 */
public class countries {

    private int id;
    private String name;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    /**
     * This method is the constructor for a country.
     * @param id the country constructor ID
     * @param name the country constructor name
     * @param createDate the country constructor create date
     * @param createdBy the country constructor created by value
     * @param lastUpdate the country constructor last update value
     * @param lastUpdatedBy the country constructor last updated by value
     */
    public countries(int id, String name, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method retrieves the country's ID.
     * @return the country ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method assigns a value to the country's ID.
     * @param id the country ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method retrieves the country's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * This method assigns a value to the country's name.
     * @param name the country name to set
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
}
