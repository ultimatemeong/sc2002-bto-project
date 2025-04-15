package Enquiries;

import java.sql.Date;

public class Reply {
    private Enquiry enquiry;
    private Date dateTime;
    private String replyString;

    public Reply(Enquiry enquiry, Date dateTime, String replyString) {
        this.enquiry = enquiry;
        this.dateTime = dateTime;
        this.replyString = replyString;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getReplyString() {
        return replyString;
    }

    public void writeReply(String replyString) {
        this.replyString = replyString;
        this.enquiry.addToReply(this); // Set the reply in the enquiry
    }

    public void editReply(String replyString) {
        this.replyString = replyString;
    }

    public void deleteReply() {
        this.enquiry.getReplies().remove(this); // Remove the reply from the enquiry
        this.enquiry = null; // Clear the reference to the enquiry
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(enquiry.getId()).append(",").append(dateTime).append(",").append(replyString).append(",");
        return sb.toString();
    }
}
