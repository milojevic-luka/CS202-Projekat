package application.controllers;

import application.entities.Member;
import application.entities.Membership;
import application.ui.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MembershipController implements Initializable {
    @FXML
    private Button addMembershipButton;

    @FXML
    private Button clearFieldsButton;

    @FXML
    private Button coachesButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button deleteMembershipButton;

    @FXML
    private TableColumn<Membership, Date> endDateColumn;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField memberIdInput;

    @FXML
    private TableColumn<Membership, String> memberNameColumn;

    @FXML
    private TableView<Membership> membershipTableView;

    @FXML
    private Button membersButton;

    @FXML
    private TableColumn<Membership, Integer> membershipIdColumn;

    @FXML
    private TextField membershipIdInput;

    @FXML
    private TableColumn<Membership, Double> priceColumn;

    @FXML
    private TextField priceInput;

    @FXML
    private TableColumn<Membership, Date> startDateColumn;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private Label suggestedPriceLabel;

    @FXML
    private Button supplementsButton;

    @FXML
    private TableColumn<Membership, String> typeColumn;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Button updateMembershipButton;


    @FXML
    void logOut(ActionEvent event) throws IOException {
        SwitchScene.change("Log in", "main-view.fxml", event);
    }

    @FXML
    void switchToCoaches(ActionEvent event) throws IOException {
        SwitchScene.change("Coaches", "coach-view.fxml", event);
    }

    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        SwitchScene.change("Dashboard", "dashboard-view.fxml", event);
    }

    @FXML
    void switchToMembers(ActionEvent event) throws IOException {
        SwitchScene.change("Members", "member-view.fxml", event);
    }

    @FXML
    void switchToSupplements(ActionEvent event) throws IOException {
        SwitchScene.change("Supplements", "supplement-view.fxml", event);
    }

    @FXML
    void addMembership(ActionEvent event) {

    }

    @FXML
    void updateMembership(ActionEvent event) {

    }

    @FXML
    void deleteMembership(ActionEvent event) {

    }

    @FXML
    void clearFields(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
