package application.db;

import application.entities.Coach;
import application.exceptions.CoachNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) implementation for handling Coach entities in the database.
 */
public class CoachDAO implements DAO<Coach> {

    /**
     * Retrieves a list of all coaches from the database.
     *
     * @return A list containing all coaches.
     * @throws SQLException If an error occurs during database access.
     */
    @Override
    public List<Coach> getAll() throws SQLException {
        ArrayList<Coach> coaches = new ArrayList<>();
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM coach";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("coach_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                String status = resultSet.getString("status");
                Coach coach = new Coach(id, firstName, lastName, gender, status);
                coaches.add(coach);
            }
        }
        return coaches;
    }

    /**
     * Inserts a new coach into the database.
     *
     * @param coach The coach to be inserted.
     * @throws SQLException If an error occurs during database access.
     */
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

    /**
     * Updates an existing coach in the database.
     *
     * @param coach The coach to be updated.
     * @throws SQLException If an error occurs during database access.
     * @throws CoachNotFoundException If the specified coach doesn't exist in the database.
     */
    @Override
    public void update(Coach coach) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "UPDATE Coach " +
                    "SET first_name = ?, last_name = ?, gender = ?, status = ? " +
                    "WHERE coach_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, coach.getFirstName());
            statement.setString(2, coach.getLastName());
            statement.setString(3, coach.getGender());
            statement.setString(4, coach.getStatus());
            statement.setString(5, String.valueOf(coach.getCoachId()));

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected != 1) throw new CoachNotFoundException("Coach with ID " +
                    coach.getCoachId() + " doesn't exist");
        }
    }

    /**
     * Deletes a coach from the database.
     *
     * @param coach The coach to be deleted.
     * @throws SQLException If an error occurs during database access.
     * @throws CoachNotFoundException If the specified coach doesn't exist in the database.
     */
    @Override
    public void delete(Coach coach) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "DELETE FROM coach WHERE coach_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, String.valueOf(coach.getCoachId()));

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected != 1) throw new CoachNotFoundException("Coach with ID " +
                    coach.getCoachId() + " doesn't exist");
        }
    }
}
