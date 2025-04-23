package Enquiries;

import Misc.AccessControl;
import Misc.EnquiryAccess;
import Projects.*;
import Users.Applicant;
import Users.User;
import java.time.LocalDateTime;
import java.util.List;
/**
 * The Enquiry class represents an enquiry made by an applicant regarding a project.
 * An apllicant can make multiple enquiries, buy each enquiry will have 0 or 1 reply.
 * Each enquiry is related to a specific project
 * @author Ang Qi Xuan Evan
 * @version 1.0
 * @since 2025-04-24
 */
public class Enquiry {
    private static int enquiryCounter = 1; // Static counter to generate unique IDs for enquiries
    private Integer id;
    private Applicant applicant;
    private String enquiryString;
    private Reply reply; // List of replies to this enquiry
    private LocalDateTime dateTime; // Date and time of the enquiry
    
    private Project project; // The project related to this enquiry

    /**
     * Constructor to create an Enquiry object.
     * @param id The unique ID of the enquiry.
     * @param applicant The applicant who made the enquiry.
     * @param enquiryString The content of the enquiry.
     * @param dateTime The date and time when the enquiry was made.
     * @param project The project related to this enquiry.
     */
    public Enquiry(Integer id, Applicant applicant, String enquiryString, LocalDateTime dateTime, Project project) {
        this.id = id;
        this.applicant = applicant;
        this.enquiryString = enquiryString;
        this.reply = null; // Initially, there is no reply
        this.dateTime = dateTime; // Set the date and time of the enquiry
        this.project = project; // Set the project related to this enquiry
        enquiryCounter = id + 1; // Increment the counter for the next enquiry
        
    }

    /**
     * Gets the unique ID for the next Enquiry.
     * @return the Counter for number of Enquiries.
     */
    public static Integer getEnquiryCounter() {
        return enquiryCounter;
    }

    /**
     * Sets the unique ID for the thiis Enquiry.
     * @return the ID of this enquiry.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the applicant who made the enquiry.
     * @return the applicant who made the enquiry.
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Gets the content of the enquiry.
     * @return the content of the enquiry.
     */
    public String getEnquiryString() {
        return enquiryString;
    }

    /**
     * Gets the reply to the enquiry.
     * @return the reply to the enquiry.
     */
    public Reply getReply() {
        return reply;
    }

    /**
     * Gets the date and time when the enquiry was made.
     * @return the date and time of the enquiry.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Gets the project related to the enquiry.
     * @return the project related to the enquiry.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project related to the enquiry.
     * @param project The project related to the enquiry.
     */
    public void setEnquiryString(String enquiryString) {
        this.enquiryString = enquiryString;
    }

    /**
     * Sets the reply to the enquiry.
     * @param reply The reply to the enquiry.
     */
    public void setReply(Reply reply) {
        this.reply = reply;
    }

    /**
     * Convers the enquiry to a string representation.
     * * @return the string representation of the enquiry.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(applicant.getNric()).append(",").append(enquiryString).append(",");
        sb.append(dateTime).append(",").append(project.getName());
        return sb.toString();
    }

    /** 
     * This method filters the list of enquiries based on the access control rules.
     * @param enquiryList The list of enquiries to filter.
     * @param user The user whose permissions are being checked.
     * @return A list of enquiries that the user has permission to view.
     */
    public static List<Enquiry> viewEnquiries(List<Enquiry> enquiryList, User user) {
        AccessControl<Enquiry> accessControl = new EnquiryAccess();
        List<Enquiry> readibleEnquiries = enquiryList.stream()
                .filter(enquiry -> accessControl.check(enquiry, user).contains("R"))
                .toList();

        return readibleEnquiries;
    }

    /**
     * This method searches for an enquiry by its ID in a list of enquiries.
     * @param enquiryList
     * @param id
     * @return The enquiry object with the specified ID, or null if not found.
     */
    public static Enquiry getEnquiryById(List<Enquiry> enquiryList, Integer id) {
        for (Enquiry enquiry : enquiryList) {
            if (enquiry.getId().equals(id)) {
                return enquiry;
            }
        }
        return null; // Return null if no matching enquiry is found
    }
}
