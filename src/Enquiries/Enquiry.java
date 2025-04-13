package Enquiries;

import Users.Applicant;

import java.util.List;

import Misc.AccessControl;
import Projects.*;
import Users.*;

public class Enquiry {
    private static int enquiryCounter = 0; // Static counter to generate unique IDs for enquiries
    private Integer id;
    private Applicant applicant;
    private String enquiryString;
    private Reply reply;
    
    private Project project; // The project related to this enquiry

    public Enquiry(Integer id, Applicant applicant, String enquiryString, Project project) {
        this.id = id;
        this.applicant = applicant;
        this.enquiryString = enquiryString;
        this.reply = null; // Initially, there is no reply
        this.project = project; // Set the project related to this enquiry
        enquiryCounter = id + 1; // Increment the counter for the next enquiry
    }

    public Integer getEnquiryCounter() {
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

    public Project getProject() {
        return project;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public List<Enquiry> viewEnquiries(User user) {
        List<Enquiry> all_enquiries = project.getEnquiryList();
        
        AccessControl<Enquiry> accessControl = new AccessControl<>();
        List<Enquiry> readibleEnquiries = all_enquiries.stream()
                .filter(enquiry -> accessControl.checkAccess(enquiry, user).contains("R"))
                .toList();

        return readibleEnquiries;
    }

}
