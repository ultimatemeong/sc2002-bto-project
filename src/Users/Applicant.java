package Users;

import Projects.Application;
import Projects.Project;
import Enquiries.Enquiry;

import java.util.ArrayList;
import java.util.List;

public class Applicant extends User{
    private Application application; 
    private List<Enquiry> enquiryList = new ArrayList<>(); // List of enquiries made by the applicant

    public Applicant(String name, String nric, int age, String maritalStatus, String password, Application application, List<Enquiry> enquiryList) {
        super(name, nric, age, maritalStatus, password);
        this.application = application;
        this.enquiryList = enquiryList;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String toString() {
        return "Applicant: " + super.toString();
    }

    protected void viewAllProjects() {
        // Logic for viewing all projects
        // If single only 2-room flat, if married both 2-room & 3-room flat
    }

    protected void applyForProject(Project project) {
        /* applicant applies for a project */
        // Application application = new Application();
    }

    //Application specific methods
    public void viewApplicationStatus() {
        // Logic for viewing application status
    }

    public void withdrawApplication() {
        // Logic for withdrawing application
    }

    //Enquiries specific methods
    protected List<Enquiry> viewEnquiries() {
        return enquiryList;
    }

    protected void createEnquiry() {
        // Logic for creating enquiry
    }

    protected void editEnquiry() {
        // Logic for editing enquiry
    }

    protected void deleteEnquiry() {
        // Logic for deleting enquiry
    }
}
