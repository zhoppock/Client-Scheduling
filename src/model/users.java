package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * This class creates a user with specified details.
 * @author Zachary Hoppock
 */
public class users {

    private int id;
    private String name;
    private String password;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    /**
     * This method is the constructor for a user.
     * @param id the user constructor ID
     * @param name the user constructor name
     * @param password the user constructor password
     * @param createDate the user constructor create date
     * @param createdBy the user constructor created by value
     * @param lastUpdate the user constructor last update value
     * @param lastUpdatedBy the user constructor last updated by value
     */
    public users(int id, String name, String password, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method retrieves the user's ID.
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method assigns a value to the user's ID.
     * @param id the user ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method retrieves the user's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * This method assigns a value to the user's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method retrieves the user's password.
     * @return the user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method assigns a value to the user's password.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
