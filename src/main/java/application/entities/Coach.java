package application.entities;

/**
 * Entity class representing a coach.
 * This class includes a parameterized constructor to initialize the coach attributes.
 * Also contains Getter, Setter and toString methods
 */
public class Coach {
    private int coachId;
    private String firstName;
    private String lastName;
    private String gender;
    private String status;

    /**
     * Default constructor for the Coach class.
     */
    public Coach() {
    }

    /**
     * Parameterized constructor for the Coach class with all fields.
     *
     * @param coachId   The unique identifier of the coach.
     * @param firstName The first name of the coach.
     * @param lastName  The last name of the coach.
     * @param gender    The gender of the coach.
     * @param status    The status of the coach.
     */
    public Coach(int coachId, String firstName, String lastName, String gender, String status) {
        this.coachId = coachId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.status = status;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "coachId=" + coachId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
