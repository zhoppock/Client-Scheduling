package model;

/**
 * This class creates a contact with specified details.
 * @author Zachary Hoppock
 */
public class contacts {

    private int id;
    private String name;
    private String email;

    /**
     * This method is the constructor for a contact.
     * @param id the contact constructor ID
     * @param name the contact constructor name
     * @param email the contact constructor email address
     */
    public contacts(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * This method retrieves the contact's ID.
     * @return the contact ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method assigns a value to the contact's ID.
     * @param id the contact ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method retrieves the contact name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * This method assigns a value to the contact's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Unused getter, kept for POJO purposes.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
