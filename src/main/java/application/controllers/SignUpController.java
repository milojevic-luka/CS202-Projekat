package application.controllers;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField emailInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameInput;

    @FXML
    void signUp(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));

        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
