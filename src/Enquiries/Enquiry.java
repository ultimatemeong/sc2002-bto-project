package Enquiries;

import Misc.AccessControl;
import Misc.EnquiryAccess;
import Projects.*;
import Users.Applicant;
import Users.User;
import java.time.LocalDateTime;
import java.util.List;

public class Enquiry {
    private static int enquiryCounter = 1; // Static counter to generate unique IDs for enquiries
    private Integer id;
    private Applicant applicant;
    private String enquiryString;
    private Reply reply; // List of replies to this enquiry
    private LocalDateTime dateTime; // Date and time of the enquiry
    
    private Project project; // The project related to this enquiry

    public Enquiry(Integer id, Applicant applicant, String enquiryString, LocalDateTime dateTime, Project project) {
        this.id = id;
        this.applicant = applicant;
        this.enquiryString = enquiryString;
        this.reply = null; // Initially, there is no reply
        this.dateTime = dateTime; // Set the date and time of the enquiry
        this.project = project; // Set the project related to this enquiry
        enquiryCounter = id + 1; // Increment the counter for the next enquiry
        
    }

    public static Integer getEnquiryCounter() {
        return enquiryCounter;
    }

    public Integer getId() {
        return id;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public String getEnquiryString() {
        return enquiryString;
    }

    public Reply getReply() {
        return reply;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Project getProject() {
        return project;
    }

    public void setEnquiryString(String enquiryString) {
        this.enquiryString = enquiryString;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(applicant.getNric()).append(",").append(enquiryString).append(",");
        sb.append(dateTime).append(",").append(project.getName());
        return sb.toString();
    }

    public static List<Enquiry> viewEnquiries(List<Enquiry> enquiryList, User user) {
        AccessControl<Enquiry> accessControl = new EnquiryAccess();
        List<Enquiry> readibleEnquiries = enquiryList.stream()
                .filter(enquiry -> accessControl.check(enquiry, user).contains("R"))
                .toList();

        return readibleEnquiries;
    }

    public static Enquiry getEnquiryById(List<Enquiry> enquiryList, Integer id) {
        for (Enquiry enquiry : enquiryList) {
            if (enquiry.getId().equals(id)) {
                return enquiry;
            }
        }
        return null; // Return null if no matching enquiry is found
    }
}
