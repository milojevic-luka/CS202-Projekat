package application.controllers;

import application.db.MembershipDAO;
import application.entities.Membership;
import application.ui.*;
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
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    private List<TextField> allFields;

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

    private Membership createMembership() {
        if (!CheckFields.areFieldsFilled(allFields))
            AlertUtil.showError("Empty fields", "Please fill all the fields");

        try {
            int membershipId = Integer.parseInt(membershipIdInput.getText());
            int memberId = Integer.parseInt(memberIdInput.getText());
            Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
            Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
            String type = typeComboBox.getValue();
            double price = Double.parseDouble(priceInput.getText());
            return new Membership(membershipId, memberId, startDate, endDate, type, price);
        } catch (NumberFormatException e) {
            AlertUtil.showError("Input error", "Please enter valid values");
        } catch (Exception e) {
            AlertUtil.showError("Error", e.getMessage());
        }
        return null;
    }

    @FXML
    void addMembership(ActionEvent event) throws SQLException {
        Membership membership = createMembership();
        if (membership != null) {
            try {
                new MembershipDAO().insert(membership);
            } catch (SQLIntegrityConstraintViolationException e) {
                AlertUtil.showError("Duplicate entry", "Membership with ID " + membership.getMembershipId() + " already exists");
            }
            populateTable();
        }
    }

    @FXML
    void updateMembership(ActionEvent event) throws SQLException {
        Membership membership = createMembership();
        if (membership != null) {
            new MembershipDAO().update(membership);
            populateTable();
        }
    }

    @FXML
    void deleteMembership(ActionEvent event) throws SQLException {
        Membership membership = createMembership();
        if (membership != null) {
            new MembershipDAO().delete(membership);
            populateTable();
        }
    }

    @FXML
    void clearFields() {
        CheckFields.clearFields(allFields);
    }

    private void tableSelection() {
        membershipTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null) {
                membershipIdInput.setText(String.valueOf(newVal.getMembershipId()));
                memberIdInput.setText(String.valueOf(newVal.getMemberId()));
                startDatePicker.setValue(LocalDate.parse(String.valueOf(newVal.getStartDate())));
                endDatePicker.setValue(LocalDate.parse(String.valueOf(newVal.getEndDate())));
                typeComboBox.setValue(newVal.getType());
                priceInput.setText(String.valueOf(newVal.getPrice()));
            }
        });
    }

    private void populateTable() throws SQLException {
        membershipTableView.getItems().clear();

        List<Membership> memberships = new MembershipDAO().getAll();

        membershipIdColumn.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberFullName"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        membershipTableView.getItems().addAll(memberships);

        clearFields();
    }

    private void populateComboBox() {
        new ComboBoxPopulation().populate(typeComboBox, Arrays.asList("Regular", "Student"), "Regular");
    }

    private void updateSuggestedPrice() {
        if (startDatePicker.getValue() == null ||
                endDatePicker.getValue() == null ||
                typeComboBox.getValue() == null)
            return;

        long numberOfDays = ChronoUnit.DAYS.between(startDatePicker.getValue(), endDatePicker.getValue());
        Boolean isStudent = typeComboBox.getValue().equals("Student") ? true : false;

        double suggestedPrice = calculateSuggestedPrice(numberOfDays, isStudent);
        suggestedPriceLabel.setText("Suggested Price: $" + suggestedPrice);
    }

    private void suggestPriceOnChange() {
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSuggestedPrice());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSuggestedPrice());
        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> updateSuggestedPrice());
    }

    private double calculateSuggestedPrice(long numberOfDays, Boolean isStudent) {
        final double PRICE_PER_DAY = 2.5;
        final double STUDENT_DISCOUNT = 0.8;
        double suggestedPrice = numberOfDays * PRICE_PER_DAY;
        if (isStudent)
            suggestedPrice *= STUDENT_DISCOUNT;
        return suggestedPrice;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allFields = CheckFields.getFields(memberIdInput, membershipIdInput, priceInput);
            suggestPriceOnChange();
            populateComboBox();
            populateTable();
            tableSelection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
