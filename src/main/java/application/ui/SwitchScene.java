package application.ui;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Utility class for switching scenes in a JavaFX application.
 */
public class SwitchScene {

    /**
     * Changes the scene to the specified FXML view with the given title.
     *
     * @param title      The title to be set for the new scene.
     * @param fxmlView   The path to the FXML file representing the new scene.
     * @param event      The ActionEvent that triggered the scene change.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public static void change(String title, String fxmlView, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlView));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
