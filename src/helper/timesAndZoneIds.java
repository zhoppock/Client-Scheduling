package helper;

import controller.login_control;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class handles the transfer of specified Zone IDS and the conversions of ZonedDateTimes.
 * @author Zachary Hoppock
 */
public class timesAndZoneIds {

    /**
     * This method returns the timeZones bundle of specified Zone IDs declared at the login screen.
     * @return the timeZones bundle of Zone IDs needed throughout the application
     */
    public static timeZones getZoneIds() {
        return login_control.currentZoneIds;
    }

    /**
     * This method takes a DateTime value converts it from one specified ZonedDateTime to another specified ZonedDateTime.
     * @param zonedDateTIme the DateTime value to be converted from the firstZID
     * @param firstZID the first Zone ID to use in the conversion
     * @param secondZID the second Zone ID to use in the conversion
     * @return the DateTime value that was converted to with the secondZID
     */
    public static LocalDateTime convertZDTs(LocalDateTime zonedDateTIme, ZoneId firstZID, ZoneId secondZID) {
        ZonedDateTime originalZDT= ZonedDateTime.of(zonedDateTIme, firstZID);
        ZonedDateTime newZDT = ZonedDateTime.ofInstant(originalZDT.toInstant(), secondZID);
        return newZDT.toLocalDateTime();
    }
}
