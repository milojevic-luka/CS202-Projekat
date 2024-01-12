package application.controllers;

import application.MainApp;
import application.db.DatabaseConnection;
import application.ui.ErrorAlert;
import application.ui.InfoAlert;
import application.ui.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {
    @FXML
    private Button logInButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameInput;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void logIn(ActionEvent event) throws SQLException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (username.isEmpty() || password.isEmpty()) {
            ErrorAlert.show("Empty Fields", "Please fill all the blank fields");
            return;
        }

        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            Alert alert;
            if (resultSet.next()) {
                InfoAlert.show("Login Message", "Successful login");

                logInButton.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(MainApp.class.getResource("dashboard-view.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Dashboard");
                stage.setScene(scene);
                stage.show();
            } else {
                ErrorAlert.show("Error Message", "Wrong Username/Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToSignUp(ActionEvent event) throws IOException {
        SwitchScene.change("Sign Up", "sign-up-view.fxml", event);
    }

}