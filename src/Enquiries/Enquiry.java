package Enquiries;

import Users.Applicant;
import Projects.*;

public class Enquiry {
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
    }

    public Integer getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }
}
