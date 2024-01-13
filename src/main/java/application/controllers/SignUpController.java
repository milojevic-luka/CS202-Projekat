package application.controllers;

import application.db.AdminDAO;
import application.entities.Admin;
import application.ui.AlertUtil;
import application.ui.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

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
    void signUp(ActionEvent event) {

        String email = emailInput.getText();
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            AlertUtil.showError("Empry fields", "Please fill in the blank fields");
            return;
        }
        if (password.length() < 8) {
            AlertUtil.showError("Password length", "Password must be at least 8 characters long");
            return;
        }

        try {
            new AdminDAO().insert(new Admin(username, email, password));

            usernameInput.setText("");
            emailInput.setText("");
            passwordInput.setText("");

            AlertUtil.showInfo("Created new user", "Successfully created " + username + " user");

            SwitchScene.change("Log In", "main-view.fxml", event);
        } catch (Exception e) {
            AlertUtil.showError("Error", e.getMessage());
        }

    }
}
