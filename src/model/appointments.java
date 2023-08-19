package model;

import java.sql.Timestamp;

/**
 * This class creates an appointment with specified details.
 * @author Zachary Hoppock
 */
public class appointments {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * This method is the constructor for an appointment.
     * @param id the appointment constructor ID
     * @param title the appointment constructor title
     * @param description the appointment constructor description
     * @param location the appointment constructor location
     * @param type the appointment constructor type
     * @param start the appointment constructor start time
     * @param end the appointment constructor end time
     * @param createDate the appointment constructor create date
     * @param createdBy the appointment constructor created by value
     * @param lastUpdate the appointment constructor last update value
     * @param lastUpdatedBy the appointment constructor last updated by value
     * @param customerId the appointment constructor customer ID
     * @param userId the appointment constructor user ID
     * @param contactId the appointment constructor contact ID
     */
    public appointments(int id, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * This method retrieves the appointment's ID.
     * @return the appointment ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method assigns a value to the appointment's ID.
     * @param id the appointment ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method retrieves the appointment's title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method assigns a value to the appointment's title.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method retrieves the appointment's description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method assigns a value to the appointment's description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method retrieves the appointment's location.
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method assigns a value to the appointment's location.
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This method retrieves the appointment's type.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * This method assigns a value to the appointment's type.
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method retrieves the appointment's start time.
     * @return the start time
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * This method assigns a value to the appointment's start time.
     * @param start the start time to set
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * This method retrieves the appointment's end time.
     * @return the end time
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * This method assigns a value to the appointment's end time.
     * @param end the end time to set
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * This method retrieves the appointment's create date.
     * @return the create date
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * This method retrieves the appointment's created by value.
     * @return the created by value
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
     * This method retrieves the appointment's last update value.
     * @return the last update value
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
     * This method retrieves the appointment's last updated by value.
     * @return the last updated by value
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
     * This method retrieves the appointment's customer ID.
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * This method retrieves the appointment's user ID.
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * This method retrieves the appointment's contact ID.
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
