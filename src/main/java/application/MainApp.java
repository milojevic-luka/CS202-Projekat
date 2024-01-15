package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main entry point of the JavaFX application.
 * Extends the Application class, which is the base class for JavaFX applications.
 */
public class MainApp extends Application {

    /**
     * Initializes and launches the JavaFX application.
     * It is an entry point that loads the main FXML view and sets up the stage.
     *
     * @param stage The primary stage for the JavaFX application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setOnCloseRequest(event -> {
            event.consume();
            exitApp(stage);
        });

        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays a confirmation dialog before exiting the application.
     * If the user confirms, it closes the specified stage.
     *
     * @param stage The JavaFX stage to be closed if the user confirms the exit.
     */
    private void exitApp(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting");
        alert.setHeaderText("You are about to exit the app!");
        alert.setContentText("Are you sure you want to leave");

        if (alert.showAndWait().get() == ButtonType.OK) stage.close();
    }


    public static void main(String[] args) {
        launch();
    }
}