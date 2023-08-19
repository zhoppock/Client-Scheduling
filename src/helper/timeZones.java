package helper;

import java.time.ZoneId;

/**
 * This class creates an bundle of specified Zone IDs.
 * @author Zachary Hoppock
 */
public class timeZones {

    private ZoneId myZoneId;
    private ZoneId easternZoneId;
    private ZoneId utcZoneId;

    /**
     * This method is the constructor for a timeZones bundle.
     * @param myZoneId the user's Zone ID that is their system default
     * @param easternZoneId the US/Eastern Zone ID
     * @param utcZoneId the standard UTC Zone ID
     */
    public timeZones(ZoneId myZoneId, ZoneId easternZoneId, ZoneId utcZoneId) {
        this.myZoneId = myZoneId;
        this.easternZoneId = easternZoneId;
        this.utcZoneId = utcZoneId;
    }

    /**
     * This method retrieves the timeZones bundle's system default Zone ID.
     * @return the system default Zone ID
     */
    public ZoneId getMyZoneId() {
        return myZoneId;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setMyZoneId(ZoneId myZoneId) {
        this.myZoneId = myZoneId;
    }

    /**
     * This method retrieves the timeZones bundle's US/Eastern Zone ID.
     * @return the US/Eastern Zone ID
     */
    public ZoneId getEasternZoneId() {
        return easternZoneId;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setEasternZoneId(ZoneId easternZoneId) {
        this.easternZoneId = easternZoneId;
    }

    /**
     * This method retrieves the timeZones bundle's standard UTC Zone ID.
     * @return the standard UTC Zone ID
     */
    public ZoneId getUtcZoneId() {
        return utcZoneId;
    }

    /**
     * Unused setter, kept for POJO purposes.
     */
    public void setUtcZoneId(ZoneId utcZoneId) {
        this.utcZoneId = utcZoneId;
    }
}
