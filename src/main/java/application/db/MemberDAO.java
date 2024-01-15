package application.db;

import application.entities.Member;
import application.exceptions.MemberNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) implementation for handling Member entities in the database.
 */
public class MemberDAO implements DAO<Member> {

    /**
     * Retrieves a list of all members from the database.
     *
     * @return A list containing all members.
     * @throws SQLException If an error occurs during database access.
     */
    @Override
    public List<Member> getAll() throws SQLException {
        ArrayList<Member> members = new ArrayList<>();
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "SELECT * FROM member";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int coachId = resultSet.getInt("coach_id");
                int memberId = resultSet.getInt("member_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                String phoneNumber = resultSet.getString("phone_num");
                Member member = new Member(coachId, memberId, firstName, lastName, gender, phoneNumber);
                members.add(member);
            }
        }
        return members;
    }

    /**
     * Inserts a new member into the database.
     *
     * @param member The member to be inserted.
     * @throws SQLException If an error occurs during database access.
     */
    @Override
    public void insert(Member member) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "INSERT INTO member (coach_id, member_id, first_name, last_name, gender, phone_num) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(member.getCoachId()));
            statement.setString(2, String.valueOf(member.getMemberId()));
            statement.setString(3, member.getFirstName());
            statement.setString(4, member.getLastName());
            statement.setString(5, member.getGender());
            statement.setString(6, member.getPhoneNum());
            statement.executeUpdate();
        }
    }

    /**
     * Updates an existing member in the database.
     *
     * @param member The member to be updated.
     * @throws SQLException If an error occurs during database access.
     * @throws MemberNotFoundException If the specified member doesn't exist in the database.
     */
    @Override
    public void update(Member member) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "UPDATE member " +
                    "SET coach_id = ?, first_name = ?, last_name = ?, gender = ?, phone_num = ? " +
                    "WHERE member_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(member.getCoachId()));
            statement.setString(2, member.getFirstName());
            statement.setString(3, member.getLastName());
            statement.setString(4, member.getGender());
            statement.setString(5, member.getPhoneNum());
            statement.setString(6, String.valueOf(member.getMemberId()));

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected != 1) throw new MemberNotFoundException("Member with ID " + member.getMemberId() + " doesn't exist");
        }
    }

    /**
     * Deletes a member from the database.
     *
     * @param member The member to be deleted.
     * @throws SQLException If an error occurs during database access.
     * @throws MemberNotFoundException If the specified member doesn't exist in the database.
     */
    @Override
    public void delete(Member member) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "DELETE FROM member WHERE member_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(member.getMemberId()));

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected != 1) throw new MemberNotFoundException("Member with ID " + member.getMemberId() + " doesn't exist");
        }
    }
}
