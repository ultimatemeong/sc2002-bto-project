package Enquiries;

import java.time.LocalDateTime;

import Users.User;

/**
 * The Reply class represents a reply to an enquiry made by an applicant.
 * Each reply is associated with a specific enquiry and is made by a user.
 * @author Ang Qi Xuan Evan
 * @version 1.0
 * @since 2025-04-24
 */
public class Reply {
    private Enquiry enquiry;
    private User user;
    private LocalDateTime dateTime;
    private String replyString;

    /**
     * Constructor for the Reply class.
     * @param enquiry the enquiry to which this reply is associated.
     * @param user the user who made the reply.
     * @param dateTime the date and time when the reply was made.
     * @param replyString the content of the reply.
     */
    public Reply(Enquiry enquiry, User user,LocalDateTime dateTime, String replyString) {
        this.enquiry = enquiry;
        this.user = user;
        this.dateTime = dateTime;
        this.replyString = replyString;
    }

    /**
     * Get the enquiry associated with this reply.
     * @return the enquiry associated with this reply.
     */
    public Enquiry getEnquiry() {
        return enquiry;
    }

    /**
     * Get the user who made this reply.
     * @return the user who made this reply.
     */
    public User getUser() {
        return user;
    }

    /**
     * Get the date and time when this reply was made.
     * @return the date and time when this reply was made.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Get the content of this reply.
     * @return the content of this reply.
     */
    public String getReplyString() {
        return replyString;
    }

    /**
     * Set the content of this reply.
     * @param replyString the new content of this reply.
     */
    public void writeReply(String replyString) {
        this.replyString = replyString;
    }

    /**
     * This method removes the reply from the enquiry and clears the reference to the enquiry.
     */
    public void deleteReply() {
        this.enquiry.setReply(null); // Remove the reply from the enquiry
        this.enquiry = null; // Clear the reference to the enquiry
    }

    /**
     * This method returns a string representation of the reply.
     * @return a string representation of the reply.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(enquiry.getId()).append(",").append(user.getNric()).append(",").append(dateTime).append(",").append(replyString);
        return sb.toString();
    }
}
