package application.controllers;

import application.db.CoachDAO;
import application.db.MemberDAO;
import application.entities.Coach;
import application.entities.Member;
import application.ui.ComboBoxPopulation;
import application.ui.SwitchScene;
import javafx.beans.property.SimpleStringProperty;
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
    void switchToMemberships(ActionEvent event) throws IOException {
        SwitchScene.change("Memberships", "membership-view.fxml", event);
    }

    @FXML
    void switchToSupplements(ActionEvent event) throws IOException {
        SwitchScene.change("Supplements", "supplement-view.fxml", event);
    }

    @FXML
    void addMember(ActionEvent event) throws SQLException {
        new MemberDAO().insert(createMember());
        populateMemberTable();
    }

    @FXML
    void updateMember(ActionEvent event) throws SQLException {
        new MemberDAO().update(createMember());
        populateMemberTable();
    }

    @FXML
    void deleteMember(ActionEvent event) throws SQLException {
        new MemberDAO().delete(createMember());
        populateMemberTable();
    }

    @FXML
    void clearFields(ActionEvent event) {
        memberIdInput.setText("");
        coachIdInput.setText("");
        firstNameInput.setText("");
        lastNameInput.setText("");
        phoneInput.setText("");
    }

    private Member createMember() {
        int id = Integer.parseInt(memberIdInput.getText());
        int coachId = Integer.parseInt(coachIdInput.getText());
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String gender = genderComboBox.getValue();
        String phoneNum = phoneInput.getText();

        return new Member(coachId, id, firstName, lastName, gender, phoneNum);
    }

    private void populateComboBox() {
        new ComboBoxPopulation().populate(genderComboBox, Arrays.asList("Male", "Female"), "Male");
    }

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

    private void populateCoachTable() throws SQLException {
        coachTableView.getItems().clear();

        List<Coach> coaches = new CoachDAO().getAll();

        coachIdColumn.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        coachName.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getFirstName() + " " + data.getValue().getLastName()));
        coachTableView.getItems().addAll(coaches);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateComboBox();
            populateMemberTable();
            populateCoachTable();
            tableSelection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
