package application.controllers;

import application.MainApp;
import application.ui.ErrorAlert;
import application.ui.InfoAlert;
import application.db.DatabaseConnection;
import application.ui.SwitchScene;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private TextField emailInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private Button signUpButton;
    @FXML
    private Button backButton;

    @FXML
    private TextField usernameInput;

    @FXML
    void backToLogIn(ActionEvent event) throws IOException {
        SwitchScene.change("Log in", "main-view.fxml", event);
    }


    @FXML
    void signUp(ActionEvent event) throws IOException, SQLException {

        String email = emailInput.getText();
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            ErrorAlert.show("Empry fields", "Please fill in the blank fields");
            return;
        }
        if (password.length() < 8) {
            ErrorAlert.show("Password length", "Password must be at least 8 characters long");
            return;
        }

        try (Connection connection = DatabaseConnection.connectToDb()) {
            String sql = "INSERT INTO admin (username, email, password)" +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.executeUpdate();

            usernameInput.setText("");
            emailInput.setText("");
            passwordInput.setText("");

            InfoAlert.show("Created new user", "Successfully created " + username + " user");

            SwitchScene.change("Log In", "main-view.fxml", event);
        }

    }
}
