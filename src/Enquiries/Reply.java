package Enquiries;

import java.time.LocalDateTime;

public class Reply {
    private Enquiry enquiry;
    private LocalDateTime dateTime;
    private String replyString;

    public Reply(Enquiry enquiry, LocalDateTime dateTime, String replyString) {
        this.enquiry = enquiry;
        this.dateTime = dateTime;
        this.replyString = replyString;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getReplyString() {
        return replyString;
    }

    public void writeReply(String replyString) {
        this.replyString = replyString;
        this.enquiry.setReply(this); // Set the reply in the enquiry
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
        sb.append(enquiry.getId()).append(",").append(dateTime).append(",").append(replyString).append(",");
        return sb.toString();
    }
}
