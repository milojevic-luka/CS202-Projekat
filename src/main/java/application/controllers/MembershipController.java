package application.controllers;

import application.db.MembershipDAO;
import application.entities.Membership;
import application.exceptions.MemberNotFoundException;
import application.exceptions.MembershipNotFoundException;
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

    /**
     * Logs the user out of the application after displaying a confirmation message.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs while changing the scene.
     */
    @FXML
    void logOut(ActionEvent event) throws IOException {
        boolean isConfirmed = AlertUtil.showConfirm("Confirmation message",
                "Are you sure you want to log out?");
        if (isConfirmed) SwitchScene.change("Log in", "main-view.fxml", event);
    }

    /**
     * Switches the scene to the Coach view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs while changing the scene.
     */
    @FXML
    void switchToCoaches(ActionEvent event) throws IOException {
        SwitchScene.change("Coaches", "coach-view.fxml", event);
    }

    /**
     * Switches the scene to the "Dashboard" view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs while changing the scene.
     */
    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        SwitchScene.change("Dashboard", "dashboard-view.fxml", event);
    }

    /**
     * Switches the scene to the Member view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs while changing the scene.
     */
    @FXML
    void switchToMembers(ActionEvent event) throws IOException {
        SwitchScene.change("Members", "member-view.fxml", event);
    }

    /**
     * Switches the scene to the Supplement view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs while changing the scene.
     */
    @FXML
    void switchToSupplements(ActionEvent event) throws IOException {
        SwitchScene.change("Supplements", "supplement-view.fxml", event);
    }

    /**
     * Creates a Membership object based on user input.
     *
     * @return The created Membership object, or null if creation fails.
     */
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

    /**
     * Adds a new Membership to the database and updates the UI.
     *
     * @param event The ActionEvent triggering the method.
     * @throws SQLException If a database access error occurs.
     */
    @FXML
    void addMembership(ActionEvent event) throws SQLException {
        Membership membership = createMembership();
        if (membership != null) {
            try {
                new MembershipDAO().insert(membership);
                populateTable();
            } catch (SQLIntegrityConstraintViolationException e) {
                AlertUtil.showError("Insert error", e.getMessage());
            }
        }
    }

    /**
     * Updates an existing Membership in the database and updates the UI.
     *
     * @param event The ActionEvent triggering the method.
     * @throws SQLException If a database access error occurs.
     */
    @FXML
    void updateMembership(ActionEvent event) throws SQLException {
        Membership membership = createMembership();
        if (membership != null) {
            try {
                new MembershipDAO().update(membership);
                AlertUtil.showInfo("Successful update", "You have successfully updated membership with ID "
                        + membership.getMembershipId());
                populateTable();
            } catch (SQLIntegrityConstraintViolationException e) {
                AlertUtil.showError("No member", "Member with ID "
                        + membership.getMemberId() + " doesn't exist");
            } catch (MembershipNotFoundException e) {
                AlertUtil.showError("No such membership", e.getMessage());
            }
        }
    }

    /**
     * Deletes an existing Membership from the database and updates the UI.
     *
     * @param event The ActionEvent triggering the method.
     * @throws SQLException If a database access error occurs.
     */
    @FXML
    void deleteMembership(ActionEvent event) throws SQLException {
        Membership membership = createMembership();
        if (membership != null) {
            try {
                new MembershipDAO().delete(membership);
                AlertUtil.showInfo("Successful deletion", "You have successfully delete membership with ID"
                        + membership.getMembershipId());
                populateTable();
            }catch (MembershipNotFoundException e) {
                AlertUtil.showError("No such membership", e.getMessage());
            }
        }
    }

    /**
     * Clears input fields in the UI.
     */
    @FXML
    void clearFields() {
        CheckFields.clearFields(allFields);
    }

    /**
     * Configures the selection listener for the membershipTableView.
     */
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

    /**
     * Populates the membershipTableView with data from the database.
     *
     * @throws SQLException If a database access error occurs.
     */
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

    /**
     * Populates the typeComboBox with predefined values.
     */
    private void populateComboBox() {
        new ComboBoxPopulation().populate(typeComboBox, Arrays.asList("Regular", "Student"), "Regular");
    }

    /**
     * Updates the suggested price label based on user input changes.
     */
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

    /**
     * Configures listeners for the suggested price calculation when input values change.
     */
    private void suggestPriceOnChange() {
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSuggestedPrice());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSuggestedPrice());
        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> updateSuggestedPrice());
    }


    /**
     * Calculates the suggested price for a membership based on the number of days and membership type.
     *
     * @param numberOfDays The number of days for the membership.
     * @param isStudent     True if the member is a student; false otherwise.
     * @return The suggested price for the membership.
     */
    private double calculateSuggestedPrice(long numberOfDays, Boolean isStudent) {
        final double PRICE_PER_DAY = 2.5;
        final double STUDENT_DISCOUNT = 0.8;
        double suggestedPrice = numberOfDays * PRICE_PER_DAY;
        if (isStudent)
            suggestedPrice *= STUDENT_DISCOUNT;
        return suggestedPrice;
    }

    /**
     * Initializes the controller, populates UI elements, and configures event listeners.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object, or null if none.
     */
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
