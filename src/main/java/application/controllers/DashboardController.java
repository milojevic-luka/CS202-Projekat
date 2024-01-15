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

    /**
     * Logs out the user and switches to the login view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the switch of the scene.
     */
    @FXML
    void logOut(ActionEvent event) throws IOException {
        boolean isConfirmed = AlertUtil.showConfirm("Confirmation message",
                "Are you sure you want to log out?");
        if (isConfirmed) SwitchScene.change("Log in", "main-view.fxml", event);
    }

    /**
     * Switches to the coaches view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the switch of the scene.
     */
    @FXML
    void switchToCoaches(ActionEvent event) throws IOException {
        SwitchScene.change("Coaches", "coach-view.fxml", event);
    }

    /**
     * Switches to the members view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the switch of the scene.
     */
    @FXML
    void switchToMembers(ActionEvent event) throws IOException {
        SwitchScene.change("Members", "member-view.fxml", event);
    }

    /**
     * Switches to the memberships view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the switch of the scene.
     */
    @FXML
    void switchToMemberships(ActionEvent event) throws IOException {
        SwitchScene.change("Log in", "membership-view.fxml", event);
    }

    /**
     * Switches to the supplements view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the switch of the scene.
     */
    @FXML
    void switchToSupplements(ActionEvent event) throws IOException {
        SwitchScene.change("Supplements", "supplement-view.fxml", event);
    }

    /**
     * Retrieves data for the dashboard, such as member count, coach count,
     * membership count, and total revenue.
     * Populates labels with the obtained data.
     */
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

    /**
     * Initializes the controller and retrieves data for the dashboard.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDashboardData();
    }
}
