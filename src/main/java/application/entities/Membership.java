package application.entities;

import java.util.Date;
/**
 * Entity class representing a membership.
 * This class includes a parameterized constructor to initialize the membership attributes.
 * Also contains Getter, Setter and toString methods
 */
public class Membership {
    private int membershipId;
    private int memberId;
    private String memberFullName;
    private Date startDate;
    private Date endDate;
    private String type;
    private double price;

    /**
     * Default constructor for the Membership class.
     */
    public Membership() {
    }

    /**
     * Parameterized constructor for the Membership class with essential fields.
     *
     * @param membershipId The unique identifier of the membership.
     * @param memberId     The unique identifier of the member associated with the membership.
     * @param startDate    The start date of the membership.
     * @param endDate      The end date of the membership.
     * @param type         The type of the membership.
     * @param price        The price of the membership.
     */
    public Membership(int membershipId, int memberId, Date startDate, Date endDate, String type, double price) {
        this.membershipId = membershipId;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.price = price;
    }

    /**
     * Parameterized constructor for the Membership class with additional memberFullName field.
     *
     * @param membershipId   The unique identifier of the membership.
     * @param memberId       The unique identifier of the member associated with the membership.
     * @param memberFullName The full name of the member associated with the membership.
     * @param startDate      The start date of the membership.
     * @param endDate        The end date of the membership.
     * @param type           The type of the membership.
     * @param price          The price of the membership.
     */
    public Membership(int membershipId, int memberId, String memberFullName, Date startDate, Date endDate, String type, double price) {
        this.membershipId = membershipId;
        this.memberId = memberId;
        this.memberFullName = memberFullName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.price = price;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMemberFullName() {
        return memberFullName;
    }

    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + membershipId +
                ", memberId=" + memberId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
