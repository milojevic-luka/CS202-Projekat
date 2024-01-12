package application.controllers;

import application.db.CoachDAO;
import application.entities.Coach;
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

    @FXML
    void logOut(ActionEvent event) throws IOException {
        SwitchScene.change("Log in", "main-view.fxml", event);
    }

    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        SwitchScene.change("Dashboard", "dashboard-view.fxml", event);
    }

    @FXML
    void switchToMembers(ActionEvent event) throws IOException {
        SwitchScene.change("Members","member-view.fxml", event);
    }

    @FXML
    void switchToMemberships(ActionEvent event) {

    }

    @FXML
    void switchToSupplements(ActionEvent event) {

    }

    @FXML
    public void addCoach(ActionEvent actionEvent) throws SQLException {
        Coach coach = createCoach();
        CoachDAO dao = new CoachDAO();
        dao.insert(coach);
        populateTable();
    }

    @FXML
    public void updateCoach(ActionEvent actionEvent) throws SQLException {
        Coach coach = createCoach();
        CoachDAO dao = new CoachDAO();
        dao.update(coach);
        populateTable();
    }

    @FXML
    public void deleteCoach(ActionEvent actionEvent) throws SQLException {
        Coach coach = createCoach();
        CoachDAO dao = new CoachDAO();
        dao.delete(coach);
        populateTable();
    }

    @FXML
    public void clearFields(ActionEvent actionEvent) {
        coachIdInput.setText("");
        firstNameInput.setText("");
        lastNameInput.setText("");
    }

    private Coach createCoach() {
        String coachId = coachIdInput.getText();
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String gender = genderComboBox.getValue();
        String status = statusComboBox.getValue();
        return new Coach(Integer.parseInt(coachId), firstName, lastName, gender, status);
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
        combo.populate(statusComboBox, Arrays.asList("Active","Inactive"), "Active");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateComboBox();
            populateTable();
            tableSelection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
