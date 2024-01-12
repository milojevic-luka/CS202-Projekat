package application.entities;

import java.util.Date;

public class Membership {
    private int membershipId;
    private int memberId;
    private Date startDate;
    private Date endDate;
    private String type;
    private double price;

    public Membership() {
        // Empty constructor
    }

    public Membership(int membershipId, int memberId, Date startDate, Date endDate, String type, double price) {
        this.membershipId = membershipId;
        this.memberId = memberId;
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
