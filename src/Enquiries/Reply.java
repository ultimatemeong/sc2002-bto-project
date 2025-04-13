package Enquiries;

import java.sql.Date;

public class Reply {
    private Enquiry enquiry;
    private Date dateTime;
    private String replyString;

    public Reply(Enquiry enquiry, Date dateTime) {
        this.enquiry = enquiry;
        this.dateTime = dateTime;
        this.replyString = null; // Initially, there is no reply string
    }

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
        this.enquiry.setReply(this); // Set the reply in the enquiry
    }

    public void editReply(String replyString) {
        this.replyString = replyString;
    }

    public void deleteReply() {
        enquiry.setReply(null);
        this.enquiry = null; // Clear the reference to the enquiry
    }
}
