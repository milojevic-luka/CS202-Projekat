package application.db;

import application.entities.Coach;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoachDAO implements DAO<Coach> {


    @Override
    public List<Coach> getAll() throws SQLException {
        ArrayList<Coach> coaches = new ArrayList<>();
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM coach";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("coach_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                String status = resultSet.getString("status");
                Coach coach = new Coach(Integer.parseInt(id), firstName, lastName, gender, status);
                coaches.add(coach);
            }
        }
        return coaches;
    }

    @Override
    public void insert(Coach coach) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "INSERT INTO coach (coach_id, first_name, last_name, gender, status)" +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, String.valueOf(coach.getCoachId()));
            statement.setString(2, coach.getFirstName());
            statement.setString(3, coach.getLastName());
            statement.setString(4, coach.getGender());
            statement.setString(5, coach.getStatus());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Coach coach) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "UPDATE Coach" +
                    "SET first_name = ?, last_name = ?, gender = ?, status = ?" +
                    "WHERE coach_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, coach.getFirstName());
            statement.setString(2, coach.getLastName());
            statement.setString(3, coach.getGender());
            statement.setString(4, coach.getStatus());
            statement.setString(5, String.valueOf(coach.getCoachId()));
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Coach coach) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "DELETE FROM coach WHERE coach_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, String.valueOf(coach.getCoachId()));
            statement.executeUpdate();
        }
    }
}
