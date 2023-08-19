package helper;

import java.sql.Timestamp;

/**
 * This class creates an appointment with specified details and a Contact name.
 * It extends the appointments class from the model package.
 * @author Zachary Hoppock
 */
public class appointmentContact extends model.appointments{

    private String contactName;

    /**
     * This method is the constructor for an appointmentContact.
     * @param id the appointmentContact constructor ID
     * @param title the appointmentContact constructor title
     * @param description the appointmentContact constructor description
     * @param location the appointmentContact constructor location
     * @param type the appointmentContact constructor type
     * @param start the appointmentContact constructor start time
     * @param end the appointmentContact constructor end time
     * @param createDate the appointmentContact constructor create date
     * @param createdBy the appointmentContact constructor created by
     * @param lastUpdate the appointmentContact constructor last update
     * @param lastUpdatedBy the appointmentContact constructor last updated by
     * @param customerId the appointmentContact constructor customer ID
     * @param userId the appointmentContact constructor user ID
     * @param contactId the appointmentContact constructor contact ID
     * @param contactName the appointmentContact constructor contact name
     */
    public appointmentContact(int id, String title, String description, String location, String type, Timestamp start,
                              Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                              int customerId, int userId, int contactId, String contactName) {
        super(id, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId,
                userId, contactId);
        this.contactName = contactName;
    }

    /**
     * This method retrieves the appointmentContact's contact name.
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
