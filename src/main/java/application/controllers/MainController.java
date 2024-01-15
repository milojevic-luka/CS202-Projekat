package application.controllers;

import application.db.AdminDAO;
import application.db.DatabaseConnection;
import application.entities.Admin;
import application.ui.AlertUtil;
import application.ui.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
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

    /**
     * Logs in the user using provided credentials and switches to the dashboard view if successful.
     * Shows an error message if the login fails.
     *
     * @param event The ActionEvent triggering the method.
     * @throws SQLException If an SQL exception occurs during database access.
     */
    @FXML
    void logIn(ActionEvent event) throws SQLException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showError("Empty Fields", "Please fill all the blank fields");
            return;
        }

        try (Connection connection = new DatabaseConnection().connectToDb()) {
            Admin adminCredentials = new Admin(username, password);
            Boolean isLogged = new AdminDAO().checkCredentials(adminCredentials);
            if (isLogged) {
                AlertUtil.showInfo("Login Message", "Successful login");

                logInButton.getScene().getWindow().hide();

                SwitchScene.change("Dashboard", "dashboard-view.fxml", event);
            } else {
                AlertUtil.showError("Error Message", "Wrong Username/Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to the sign-up view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the switch of the scene.
     */
    @FXML
    void switchToSignUp(ActionEvent event) throws IOException {
        SwitchScene.change("Sign Up", "sign-up-view.fxml", event);
    }


}