package Enquiries;

import java.time.LocalDateTime;

import Users.User;

public class Reply {
    private Enquiry enquiry;
    private User user;
    private LocalDateTime dateTime;
    private String replyString;

    public Reply(Enquiry enquiry, User user,LocalDateTime dateTime, String replyString) {
        this.enquiry = enquiry;
        this.user = user;
        this.dateTime = dateTime;
        this.replyString = replyString;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getReplyString() {
        return replyString;
    }

    public void editReply(String replyString) {
        this.replyString = replyString;
    }

    public void deleteReply() {
        this.enquiry.setReply(null); // Remove the reply from the enquiry
        this.enquiry = null; // Clear the reference to the enquiry
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(enquiry.getId()).append(",").append(user.getNric()).append(",").append(dateTime).append(",").append(replyString).append(",");
        return sb.toString();
    }
}
