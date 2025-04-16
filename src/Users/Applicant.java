package Users;

import Enquiries.Enquiry;
import Misc.*;
import Projects.Application;
import Projects.Project;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Applicant extends User{
    private Application application; 
    private List<Enquiry> enquiryList = new ArrayList<>(); // List of enquiries made by the applicant

    // Constructor for applicant with no existing applications
    public Applicant(String name, String nric, int age, String maritalStatus, String password, List<Enquiry> enquiryList) {
        super(name, nric, age, maritalStatus, password);
        this.enquiryList = enquiryList;
    }

    // Constructor for applicant with existing application
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

    protected boolean applyForProject(Project project, String flatType) {
        /* applicant applies for a project */
        if (this.getApplication() == null) {
            Application application = new Application(Application.getApplicationCounter(), project, flatType, this, new Date(), "Pending", "Null");
            this.setApplication(application);

            // allow user to read project
            AccessControl<Project> accessControl = new ProjectAccess();
            accessControl.add(project, this, "R");

            System.out.println("Application submitted! Pending approval.");
            return true;
        } else {
            System.out.println("Application failed. You already have an active application.");
            return false;
        }
    }

    //Application specific methods
    public void viewApplicationStatus() {
        // Logic for viewing application status
        Application application = this.getApplication();

        // user has an active application
        if (!(application == null)) {
            System.out.println(application.getFormStatus());
        } else {
            System.out.println("No active application to view!");
        }

    }

    public boolean withdrawApplication() {
        // Logic for withdrawing application
        Application application = this.getApplication();

        // user has an active application
        if (!(application == null)) {
            application.setWithdrawalStatus("PENDING");
            System.out.println("Withdrawal submitted! Pending approval.");
            return true;
        } else {
            System.out.println("Withdrawal failed. You do not have an active application to withdraw from.");
            return false;
        }
    }

    //Enquiries specific methods
    protected void createEnquiry(Project project, String enquiryString) {
        // Logic for creating enquiry
        Enquiry enquiry = new Enquiry(Enquiry.getEnquiryCounter(), this, enquiryString, project);
        enquiryList.add(enquiry);
    }

    protected boolean editEnquiry(Integer id, String newEnquiryString) {
        // Logic for editing enquiry
        for (Enquiry enquiry : enquiryList) {
            if (enquiry.getId().equals(id)) {
                enquiry.setEnquiryString(newEnquiryString);
                System.out.println("Enquiry successfully updated!");
                return true;
            }
        } 
        System.out.println("Failed to edit. Enquiry not found.");
        return false;
    }

    protected boolean deleteEnquiry(Integer id) {
        // Logic for deleting enquiry
        for (Enquiry enquiry : enquiryList) {
            if (enquiry.getId().equals(id)) {
                enquiryList.remove(enquiry);
                System.out.println("Enquiry successfully deleted!");
                return true;
            }
        } 
        System.out.println("Failed to delete. Enquiry not found.");
        return false;
    }
    }

