package application.controllers;

import application.db.CoachDAO;
import application.db.MembershipDAO;
import application.entities.Coach;
import application.entities.Membership;
import application.exceptions.CoachNotFoundException;
import application.exceptions.MembershipNotFoundException;
import application.ui.AlertUtil;
import application.ui.CheckFields;
import application.ui.ComboBoxPopulation;
import application.ui.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CoachController implements Initializable {
    @FXML
    private Button addCoach;

    @FXML
    private Button clearFields;

    @FXML
    private TextField coachIdInput;

    @FXML
    private TableView<Coach> coachTableView;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button deleteCoach;

    @FXML
    private TableColumn<Coach, String> firstNameColumn;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TableColumn<Coach, String> genderColumn;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TableColumn<Coach, String> idColumn;

    @FXML
    private TableColumn<Coach, String> lastNameColumn;

    @FXML
    private TextField lastNameInput;

    @FXML
    private Button logOutButton;

    @FXML
    private Button membersButton;

    @FXML
    private Button membershipsButton;

    @FXML
    private TableColumn<Coach, String> statusColumn;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Button supplementsButton;

    @FXML
    private Button updateCoach;

    private List<TextField> allFields;

    @FXML
    void logOut(ActionEvent event) throws IOException {
        boolean isConfirmed = AlertUtil.showConfirm("Confirmation message",
                "Are you sure you want to log out?");
        if (isConfirmed) SwitchScene.change("Log in", "main-view.fxml", event);
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
    void switchToMemberships(ActionEvent event) throws IOException {
        SwitchScene.change("Memberships", "membership-view.fxml", event);
    }

    @FXML
    void switchToSupplements(ActionEvent event) throws IOException {
        SwitchScene.change("Supplements", "supplement-view.fxml", event);
    }

    @FXML
    public void addCoach(ActionEvent actionEvent) throws SQLException {
        Coach coach = createCoach();
        if (coach != null) {
            try {
                new CoachDAO().insert(coach);
                populateTable();
            } catch (SQLIntegrityConstraintViolationException e) {
                AlertUtil.showError("Insert error", e.getMessage());
            }
        }
    }

    @FXML
    public void updateCoach(ActionEvent actionEvent) throws SQLException {
        Coach coach = createCoach();
        if (coach != null) {
            try {
                new CoachDAO().update(coach);
                AlertUtil.showInfo("Successful update", "You have successfully updated coach with ID "
                        + coach.getCoachId());
                populateTable();
            } catch (CoachNotFoundException e) {
                AlertUtil.showError("No such coach", e.getMessage());
            }
        }
    }

    @FXML
    public void deleteCoach(ActionEvent actionEvent) throws SQLException {
        Coach coach = createCoach();
        if (coach != null) {
            try {
                new CoachDAO().delete(coach);
                AlertUtil.showInfo("Successful update", "You have successfully updated coach with ID "
                        + coach.getCoachId());
                populateTable();
            } catch (CoachNotFoundException e) {
                AlertUtil.showError("No such coach", e.getMessage());
            }
        }
    }

    @FXML
    public void clearFields(ActionEvent actionEvent) {
        CheckFields.clearFields(allFields);
    }

    private Coach createCoach() {
        if (!CheckFields.areFieldsFilled(allFields))
            AlertUtil.showError("Empty fields", "Please fill all the fields");
        try {
            String coachId = coachIdInput.getText();
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            String gender = genderComboBox.getValue();
            String status = statusComboBox.getValue();
            return new Coach(Integer.parseInt(coachId), firstName, lastName, gender, status);
        } catch (NumberFormatException e) {
            AlertUtil.showError("Input error", "Please enter valid values");
        } catch (Exception e) {
            AlertUtil.showError("Error", e.getMessage());
        }
        return null;
    }

    private void populateTable() throws SQLException {
        coachTableView.getItems().clear();

        List<Coach> coaches = new CoachDAO().getAll();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        coachTableView.getItems().addAll(coaches);
    }

    private void tableSelection() {
        coachTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null) {
                coachIdInput.setText(String.valueOf(newVal.getCoachId()));
                firstNameInput.setText(newVal.getFirstName());
                lastNameInput.setText(newVal.getLastName());
                genderComboBox.setValue(newVal.getGender());
                statusComboBox.setValue(newVal.getStatus());
            }
        });
    }

    private void populateComboBox() {
        ComboBoxPopulation combo = new ComboBoxPopulation();
        combo.populate(genderComboBox, Arrays.asList("Male", "Female"), "Male");
        combo.populate(statusComboBox, Arrays.asList("Active", "Inactive"), "Active");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allFields = CheckFields.getFields(coachIdInput, firstNameInput, lastNameInput);
            populateComboBox();
            populateTable();
            tableSelection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
