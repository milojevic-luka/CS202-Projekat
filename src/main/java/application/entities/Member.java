package application.entities;

/**
 * Entity class representing a member.
 * This class includes a parameterized constructor to initialize the member attributes.
 * Also contains Getter, Setter and toString methods
 */
public class Member {
    private int coachId;
    private int memberId;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNum;

    /**
     * Default constructor for the Member class.
     */
    public Member() {
    }

    /**
     * Parameterized constructor for the Member class with all fields.
     *
     * @param coachId   The unique identifier of the coach associated with the member.
     * @param memberId  The unique identifier of the member.
     * @param firstName The first name of the member.
     * @param lastName  The last name of the member.
     * @param gender    The gender of the member.
     * @param phoneNum  The phone number of the member.
     */
    public Member(int coachId, int memberId, String firstName, String lastName, String gender, String phoneNum) {
        this.coachId = coachId;
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNum = phoneNum;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Member{" +
                "coachId=" + coachId +
                ", memberId=" + memberId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
