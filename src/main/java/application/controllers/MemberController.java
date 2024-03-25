package application.controllers;

import application.db.CoachDAO;
import application.db.MemberDAO;
import application.db.MembershipDAO;
import application.entities.Coach;
import application.entities.Member;
import application.exceptions.MemberNotFoundException;
import application.ui.AlertUtil;
import application.ui.CheckFields;
import application.ui.ComboBoxPopulation;
import application.ui.SwitchScene;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MemberController implements Initializable {
    @FXML
    private Button addMemberButton;

    @FXML
    private Button clearFieldsButton;

    @FXML
    private TableColumn<Coach, Integer> coachIdColumn;

    @FXML
    private TextField coachIdInput;

    @FXML
    private TableColumn<Coach, String> coachName;

    @FXML
    private TableView<Coach> coachTableView;

    @FXML
    private Button coachesButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button deleteMemberButton;

    @FXML
    private TableColumn<Member, String> firstNameColumn;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TableColumn<Member, String> genderColumn;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TableColumn<Member, String> lastNameColumn;

    @FXML
    private TextField lastNameInput;

    @FXML
    private Button logOutButton;

    @FXML
    private TableColumn<Member, Integer> memberCoachIdColumn;

    @FXML
    private TableColumn<Member, Integer> memberIdColumn;

    @FXML
    private TextField memberIdInput;

    @FXML
    private TableView<Member> memberTableView;

    @FXML
    private Button membershipsButton;

    @FXML
    private TextField phoneInput;

    @FXML
    private TableColumn<Member, String> phoneNumberColumn;

    @FXML
    private Button supplementsButton;

    @FXML
    private Button updateMemberButton;
    private List<TextField> allFields;

    /**
     * Logs out the user and prompts for confirmation.
     * Changes the scene to the main view if the user confirms.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the scene change.
     */
    @FXML
    void logOut(ActionEvent event) throws IOException {
        boolean isConfirmed = AlertUtil.showConfirm("Confirmation message",
                "Are you sure you want to log out?");
        if (isConfirmed) SwitchScene.change("Log in", "main-view.fxml", event);
    }

    /**
     * Switches the view to the coaches' view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the scene change.
     */
    @FXML
    void switchToCoaches(ActionEvent event) throws IOException {
        SwitchScene.change("Coaches", "coach-view.fxml", event);
    }

    /**
     * Switches the view to the dashboard.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the scene change.
     */
    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        SwitchScene.change("Dashboard", "dashboard-view.fxml", event);
    }

    /**
     * Switches the view to the memberships' view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the scene change.
     */
    @FXML
    void switchToMemberships(ActionEvent event) throws IOException {
        SwitchScene.change("Memberships", "membership-view.fxml", event);
    }

    /**
     * Switches the view to the supplements' view.
     *
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs during the scene change.
     */
    @FXML
    void switchToSupplements(ActionEvent event) throws IOException {
        SwitchScene.change("Supplements", "supplement-view.fxml", event);
    }

    /**
     * Adds a member using the provided information.
     * Shows an error message if the insertion fails.
     *
     * @param event The ActionEvent triggering the method.
     * @throws SQLException If an SQL exception occurs during database access.
     */
    @FXML
    void addMember(ActionEvent event) throws SQLException {
        Member member = createMember();
        if (member != null) {
            try {
                new MemberDAO().insert(member);
                populateMemberTable();
                AlertUtil.showInfo("Added user", "Member with ID " + member.getMemberId() + " successfully");
            } catch (SQLIntegrityConstraintViolationException e) {
                AlertUtil.showError("Insert error", e.getSQLState());
            }
        }
    }

    /**
     * Updates a member using the provided information.
     * Shows an error message if the update fails.
     *
     * @param event The ActionEvent triggering the method.
     * @throws SQLException If an SQL exception occurs during database access.
     */
    @FXML
    void updateMember(ActionEvent event) throws SQLException {
        Member member = createMember();
        if (member != null) {
            try {
                new MemberDAO().update(member);
                AlertUtil.showInfo("Successful update", "You have updated member with ID " + member.getMemberId());
                populateMemberTable();
            } catch (SQLIntegrityConstraintViolationException e) {
                AlertUtil.showError("No coach", "Coach with ID " + member.getCoachId() + " doesn't exist");
            } catch (MemberNotFoundException e) {
                AlertUtil.showError("No such member", e.getMessage());
            }
        }
    }

    /**
     * Deletes a member using the provided information.
     * Shows an error message if the deletion fails.
     *
     * @param event The ActionEvent triggering the method.
     * @throws SQLException If an SQL exception occurs during database access.
     */
    @FXML
    void deleteMember(ActionEvent event) throws SQLException {
        Member member = createMember();
        if (member != null) {
            try {
                new MemberDAO().delete(member);
                AlertUtil.showInfo("Successful deletion", "You have delete " + member.getFirstName() + " " + member.getLastName());
                populateMemberTable();
            } catch (MemberNotFoundException e) {
                AlertUtil.showError("No such user", e.getMessage());
            }
        }
    }

    /**
     * Clears all input fields.
     *
     * @param event The ActionEvent triggering the method.
     */
    @FXML
    void clearFields(ActionEvent event) {
        CheckFields.clearFields(allFields);
    }


    /**
     * Creates a Member object using the input field values.
     *
     * @return The created Member object, or null if input validation fails.
     */
    private Member createMember() {
        if (!CheckFields.areFieldsFilled(allFields))
            AlertUtil.showError("Empty fields", "Please fill all the fields");

        try {
            int id = Integer.parseInt(memberIdInput.getText());
            int coachId = Integer.parseInt(coachIdInput.getText());
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            String gender = genderComboBox.getValue();
            String phoneNum = phoneInput.getText();

            return new Member(coachId, id, firstName, lastName, gender, phoneNum);
        } catch (NumberFormatException e) {
            AlertUtil.showError("Input error", "Please enter valid values");
        } catch (Exception e) {
            AlertUtil.showError("Error", e.getMessage());
        }
        return null;
    }

    /**
     * Populates the gender ComboBox with predefined values and sets the default value.
     */
    private void populateComboBox() {
        new ComboBoxPopulation().populate(genderComboBox, Arrays.asList("Male", "Female"), "Male");
    }

    /**
     * Sets up listeners for table selection events, updating input fields accordingly.
     */
    private void tableSelection() {
        memberTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null) {
                coachIdInput.setText(String.valueOf(newVal.getCoachId()));
                memberIdInput.setText(String.valueOf(newVal.getMemberId()));
                firstNameInput.setText(newVal.getFirstName());
                lastNameInput.setText(newVal.getLastName());
                genderComboBox.setValue(newVal.getGender());
                phoneInput.setText(newVal.getPhoneNum());
            }
        });

        coachTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null) coachIdInput.setText(String.valueOf(newVal.getCoachId()));
        });
    }

    /**
     * Populates the member table with data from the database.
     *
     * @throws SQLException If an SQL exception occurs during database access.
     */
    private void populateMemberTable() throws SQLException {
        memberTableView.getItems().clear();

        List<Member> members = new MemberDAO().getAll();

        memberCoachIdColumn.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));

        memberTableView.getItems().addAll(members);
    }

    /**
     * Populates the coach table with data from the database.
     *
     * @throws SQLException If an SQL exception occurs during database access.
     */
    private void populateCoachTable() throws SQLException {
        coachTableView.getItems().clear();

        List<Coach> coaches = new CoachDAO().getAll();

        coachIdColumn.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        coachName.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getFirstName() + " " + data.getValue().getLastName()));

        coachTableView.getItems().addAll(coaches);
    }

    /**
     * Initializes the controller, populating ComboBoxes and tables, and setting up listeners.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allFields = CheckFields.getFields(coachIdInput, memberIdInput, firstNameInput, lastNameInput, phoneInput);
            populateComboBox();
            populateMemberTable();
            populateCoachTable();
            tableSelection();
            addTextLimiter(phoneInput, 9);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
                tf.setBorder(Border.stroke(Paint.valueOf("")));
                AlertUtil.showError("Input error","Can't have more than " + maxLength + " characters");
            }
        });
    }
}
