package application.db;

import application.entities.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO implements DAO<Member> {

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
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Member member) throws SQLException {
        try (Connection connection = new DatabaseConnection().connectToDb()) {
            String query = "DELETE FROM member WHERE member_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(member.getMemberId()));
            statement.executeUpdate();
        }
    }
}
