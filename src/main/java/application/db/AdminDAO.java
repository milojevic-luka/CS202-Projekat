package application.db;

import application.entities.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) implementation for handling Admin entities in the database.
 */
public class AdminDAO implements DAO<Admin> {

    /**
     * Retrieves a list of all admins from the database.
     *
     * @return A list containing all admins.
     * @throws SQLException If an error occurs during database access.
     */
    @Override
    public List<Admin> getAll() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "SELECT * FROM admin";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("admin_id");
                String username = result.getString("username");
                String email = result.getString("email");
                String password = result.getString("password");
                Admin admin = new Admin(id, username, email, password);
                admins.add(admin);
            }
        }
        return admins;
    }

    /**
     * Inserts a new admin into the database.
     *
     * @param admin The admin to be inserted.
     * @throws SQLException If an error occurs during database access.
     */
    @Override
    public void insert(Admin admin) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String sql = "INSERT INTO admin (username, email, password)" +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, admin.getUsername());
            statement.setString(2, admin.getEmail());
            statement.setString(3, admin.getPassword());
            statement.executeUpdate();
        }
    }

    /**
     * Updates an existing admin in the database.
     *
     * @param admin The admin to be updated.
     * @throws SQLException If an error occurs during database access.
     */
    @Override
    public void update(Admin admin) throws SQLException {

    }

    /**
     * Deletes an admin from the database.
     *
     * @param admin The admin to be deleted.
     * @throws SQLException If an error occurs during database access.
     */
    @Override
    public void delete(Admin admin) throws SQLException {

    }

    /**
     * Checks the credentials of an admin in the database.
     *
     * @param admin The admin whose credentials are to be checked.
     * @return True if the credentials are valid, false otherwise.
     * @throws SQLException If an error occurs during database access.
     */
    public boolean checkCredentials(Admin admin) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, admin.getUsername());
            statement.setString(2, admin.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) return true;
        }
        return false;
    }
}
