package application.controllers;

import application.db.CoachDAO;
import application.entities.Coach;
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
import java.util.ArrayList;
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
    void switchToMembers(ActionEvent event) {

    }

    @FXML
    void switchToMemberships(ActionEvent event) {

    }

    @FXML
    void switchToSupplements(ActionEvent event) {

    }

    private void populateTable() throws SQLException {
        List<Coach> coaches = new CoachDAO().getAll();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        coachTableView.getItems().addAll(coaches);
    }

    private void populateComboBox() {
        // Gender
        genderComboBox.getItems().add("Male");
        genderComboBox.getItems().add("Female");

        //Status
        statusComboBox.getItems().add("Active");
        statusComboBox.getItems().add("Inactive");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateComboBox();
            populateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
