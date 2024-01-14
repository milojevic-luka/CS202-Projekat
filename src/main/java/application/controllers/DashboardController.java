package application.controllers;

import application.db.DatabaseConnection;
import application.ui.AlertUtil;
import application.ui.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
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
        boolean isConfirmed = AlertUtil.showConfirm("Confirmation message",
                "Are you sure you want to log out?");
        if (isConfirmed) SwitchScene.change("Log in", "main-view.fxml", event);
    }

    @FXML
    void switchToCoaches(ActionEvent event) throws IOException {
        SwitchScene.change("Coaches", "coach-view.fxml", event);
    }

    @FXML
    void switchToMembers(ActionEvent event) throws IOException {
        SwitchScene.change("Members", "member-view.fxml", event);
    }

    @FXML
    void switchToMemberships(ActionEvent event) throws IOException {
        SwitchScene.change("Log in", "membership-view.fxml", event);
    }

    @FXML
    void switchToSupplements(ActionEvent event) throws IOException {
        SwitchScene.change("Supplements", "supplement-view.fxml", event);
    }

    private void getDashboardData() {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String sql = "SELECT " +
                    "(SELECT COUNT(*) FROM MEMBER) AS member_count, " +
                    "(SELECT COUNT(*) FROM COACH) AS coach_count, " +
                    "(SELECT COUNT(*) FROM MEMBERSHIP) AS membership_count, " +
                    "(SELECT SUM(PRICE) FROM MEMBERSHIP) AS revenue";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                int memberCount = result.getInt("member_count");
                int coachCount = result.getInt("coach_count");
                int membershipCount = result.getInt("membership_count");
                double totalRevenue = result.getDouble("revenue");

                totalActiveMembershipsLabel.setText(totalActiveMembershipsLabel.getText() + membershipCount);
                totalCoachesLabel.setText(totalCoachesLabel.getText() + coachCount);
                totalMembersLabel.setText(totalMembersLabel.getText() + memberCount);
                totalRevenuLabel.setText(totalRevenuLabel.getText() + String.format("%.2f", totalRevenue) + "$");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDashboardData();
    }
}
