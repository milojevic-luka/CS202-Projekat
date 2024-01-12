package application.entities;

public class Coach {
    private int coachId;
    private String firstName;
    private String lastName;
    private String gender;
    private String status;

    public Coach() {
    }

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
