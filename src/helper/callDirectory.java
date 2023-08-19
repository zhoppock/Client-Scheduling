package helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * This class is used to change from one scene of the application to another.
 * @author Zachary Hoppock
 */
public abstract class callDirectory {

    /**
     * This method is used in the controller classes to change from one screen to another by calling to a specified file.
     * @param event the Action Event of pressing the respective method button in order to change the scene
     * @param directory the directory of the file that the application will change scenes to
     * @throws IOException an exception will be thrown if the application is unable to execute the file to change scenes
     */
    public void callUpDirectory(ActionEvent event, String directory) throws IOException {

        Stage stage;
        Parent scene;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(directory)));
        stage.setScene(new Scene(scene));
        stage.show();

    }
}
