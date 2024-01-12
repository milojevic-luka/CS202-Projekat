package application.controllers;

import application.ui.ConfirmAlert;
import application.ui.InfoAlert;
import application.ui.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class DashboardController {
    @FXML
    private Button coachesButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button membersButton;

    @FXML
    private Button membershipsButton;

    @FXML
    private Button supplementsButton;

    @FXML
    private Label totalCoachesLabel;

    @FXML
    private Label totalMembersLabel;

    @FXML
    private Label totalRevenuLabel;

    @FXML
    private Label totalActiveMembershipsLabel;

    @FXML
    void logOut(ActionEvent event) throws IOException {
        boolean isConfirmed = ConfirmAlert.show("Confirmation message",
                "Are you sure you want to log out?");
        if(isConfirmed) SwitchScene.change("Log in", "main-view.fxml", event);
    }

    @FXML
    void switchToCoaches(ActionEvent event) {

    }

    @FXML
    void switchToMembers(ActionEvent event) {

    }

    @FXML
    void switchToMemberships(ActionEvent event) {

    }

    @FXML
    void switchToSupplements(ActionEvent event) {

    }
}
