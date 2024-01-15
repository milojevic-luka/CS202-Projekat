package application.db;

import application.entities.Membership;
import application.exceptions.MembershipNotFoundException;

import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * Data Access Object (DAO) implementation for handling Membership entities in the database.
 */
public class MembershipDAO implements DAO<Membership> {

    /**
     * Retrieves a list of all memberships from the database, including member full names.
     *
     * @return A list containing all memberships.
     * @throws SQLException If an error occurs during database access.
     */
    @Override
    public List getAll() throws SQLException {
        List<Membership> memberships = new ArrayList<>();
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "SELECT m.*, CONCAT(first_name, ' ', last_name) AS full_name FROM membership m " +
                    "JOIN member mb ON m.member_id = mb.member_id";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int membershipId = result.getInt("membership_id");
                int memberId = result.getInt("member_id");
                String memberFullName = result.getString("full_name");
                Date startDate = result.getDate("start_date");
                Date endDate = result.getDate("end_date");
                String type = result.getString("type");
                Double price = result.getDouble("price");

                Membership membership = new Membership(membershipId, memberId, memberFullName, startDate, endDate, type, price);
                memberships.add(membership);
            }
        }
        return memberships;
    }

    /**
     * Inserts a new membership into the database.
     *
     * @param membership The membership to be inserted.
     * @throws SQLException If an error occurs during database access.
     */
    @Override
    public void insert(Membership membership) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "INSERT INTO membership (membership_id, member_id, start_date, end_date, type, price) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, membership.getMembershipId());
            statement.setInt(2, membership.getMemberId());

            Date startDate = membership.getStartDate();
            java.sql.Date sqlDate = new java.sql.Date(startDate.getTime());
            statement.setDate(3, sqlDate);

            Date endDate = membership.getEndDate();
            sqlDate = new java.sql.Date(endDate.getTime());
            statement.setDate(4, sqlDate);

            statement.setString(5, membership.getType());
            statement.setDouble(6, membership.getPrice());

            statement.executeUpdate();
        }
    }

    /**
     * Updates an existing membership in the database.
     *
     * @param membership The membership to be updated.
     * @throws SQLException If an error occurs during database access.
     * @throws MembershipNotFoundException If the specified membership doesn't exist in the database.
     */
    @Override
    public void update(Membership membership) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "UPDATE membership " +
                    "SET member_id = ?, start_date = ?, end_date = ?, type = ?, price = ? " +
                    "WHERE membership_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, membership.getMemberId());

            Date startDate = membership.getStartDate();
            java.sql.Date sqlDate = new java.sql.Date(startDate.getTime());
            statement.setDate(2, sqlDate);

            Date endDate = membership.getEndDate();
            sqlDate = new java.sql.Date(endDate.getTime());
            statement.setDate(3, sqlDate);

            statement.setString(4, membership.getType());
            statement.setDouble(5, membership.getPrice());
            statement.setInt(6, membership.getMembershipId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected != 1) throw new MembershipNotFoundException("Membership with ID " +
                    membership.getMembershipId() + " doesn't exist");
        }
    }

    /**
     * Deletes a membership from the database.
     *
     * @param membership The membership to be deleted.
     * @throws SQLException If an error occurs during database access.
     * @throws MembershipNotFoundException If the specified membership doesn't exist in the database.
     */
    @Override
    public void delete(Membership membership) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "DELETE FROM membership WHERE membership_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, membership.getMembershipId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected != 1) throw new MembershipNotFoundException("Membership with ID " +
                    membership.getMembershipId() + " doesn't exist");

        }
    }
}
