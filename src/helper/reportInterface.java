package helper;

import javafx.collections.ObservableList;
import model.appointments;

/**
 * This interface is used to help generate database reports in the application.
 * @author Zachary Hoppock
 */
public interface reportInterface {

    /**
     * This method will be used by multiple report classes to help generate database reports.
     * @param list a list of appointments for the report
     * @return a report string
     */
    String displayReport(ObservableList<appointments> list);
}
