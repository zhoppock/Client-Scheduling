package controller;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class is where the application gets booted up from.
 * It extends the Application class.
 * @author Zachary Hoppock
 */
public class MainLoader extends Application {

    /**
     * This method executes the boot up of the application, starting from the login screen.
     * @param primaryStage a standard parameter when running JavaFX FXMLLoader
     * @throws Exception  an exception will be thrown if the application is unable to execute the file to start the application
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        primaryStage.setTitle("Client Schedules");
        primaryStage.setScene(new Scene(root, 985.0, 589.0));
        primaryStage.show();
    }

    /**
     * This is the main method.  It calls a connection to the needed database and executes the start up for the application.
     * @param args a standard parameter when running your main method
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
