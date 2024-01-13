package application.db;

import application.entities.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements DAO<Admin> {
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

    @Override
    public void update(Admin admin) throws SQLException {

    }

    @Override
    public void delete(Admin admin) throws SQLException {

    }

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
