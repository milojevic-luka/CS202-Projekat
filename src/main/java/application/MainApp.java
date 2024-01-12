package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
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