package application.db;

import application.entities.Membership;
import application.exceptions.MembershipNotFoundException;

import java.sql.*;
import java.util.Date;
import java.util.*;


public class MembershipDAO implements DAO<Membership> {

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

    public List<Map<String, Object>> getAllWithMember() throws SQLException {
        List<Map<String, Object>> memberships = new ArrayList<>();

        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "SELECT " +
                    "    M.MEMBERSHIP_ID, " +
                    "    M.MEMBER_ID, " +
                    "    CONCAT(MF.FIRST_NAME, ' ', MF.LAST_NAME) AS MEMBER_NAME, " +
                    "    M.START_DATE, " +
                    "    M.END_DATE, " +
                    "    M.TYPE, " +
                    "    M.PRICE " +
                    "FROM " +
                    "    MEMBERSHIP M " +
                    "JOIN MEMBER MF ON M.MEMBER_ID = MF.MEMBER_ID;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Map<String, Object> membership = new HashMap<>();
                membership.put("MEMBERSHIP_ID", result.getInt("MEMBERSHIP_ID"));
                membership.put("MEMBER_ID", result.getInt("MEMBER_ID"));
                membership.put("MEMBER_NAME", result.getString("MEMBER_NAME"));
                membership.put("START_DATE", result.getDate("START_DATE"));
                membership.put("END_DATE", result.getDate("END_DATE"));
                membership.put("TYPE", result.getString("TYPE"));
                membership.put("PRICE", result.getDouble("PRICE"));

                memberships.add(membership);

            }
        }
        return memberships;
    }
}
