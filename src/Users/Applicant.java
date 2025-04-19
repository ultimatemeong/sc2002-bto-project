package Users;

import Enquiries.Enquiry;
import Misc.*;
import Projects.Application;
import Projects.Project;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Applicant extends User{
    private Application application; 
    private List<Enquiry> enquiryList = new ArrayList<>(); // List of enquiries made by the applicant

    // Constructor for applicant with no existing applications
    public Applicant(String name, String nric, Integer age, String maritalStatus, String password, List<Enquiry> enquiryList) {
        super(name, nric, age, maritalStatus, password);
        this.enquiryList = enquiryList;
    }

    // Constructor for applicant with existing application
    public Applicant(String name, String nric, Integer age, String maritalStatus, String password, Application application, List<Enquiry> enquiryList) {
        super(name, nric, age, maritalStatus, password);
        this.application = application;
        this.enquiryList = enquiryList;

    }

    public Application getApplication() {
        return application;
    }

    public static Applicant getApplicantByNric(List<Applicant> applicantList, String nric) {
        for (Applicant applicant : applicantList) {
            if (applicant.getNric().equals(nric)) {
                return applicant;
            }
        }
        return null; 
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    private boolean isProjectOfficer(Project project) {
        List<Officer> officerList = project.getOfficerList();
        for (Officer officer : officerList) {
            if (officer.getNric().equals(this.getNric())) {
                return true;
            }
        }
        return false;
    }

    public boolean applyForProject(Project project, String flatType) {
        /* applicant applies for a project */
        // user has no active application and is not an officer for this project
        if ((this.getApplication() == null) && !(isProjectOfficer(project))) {
            Application application = new Application(Application.getApplicationCounter(), project, flatType, this, LocalDate.now(), "Pending", "Null");
            this.setApplication(application);

            // allow user to read project
            AccessControl<Project> accessControl = new ProjectAccess();
            accessControl.add(project, this, "R");

            System.out.println("Application submitted! Pending approval.\n");
            return true;
        } else {
            System.out.println("Application failed. You already have an active application.\n");
            return false;
        }
    }

    //Application specific methods
    public void viewApplicationStatus() {
        // Logic for viewing application status
        Application application = this.getApplication();

        // user has an active application
        if (!(application == null)) {
            System.out.println("Project: " + application.getProject().getName());
            System.out.println("Neighbourhood: " + application.getProject().getNeighbourhood());
            System.out.println("Flat Type Applied: " + application.getFlatType());
            System.out.println("Application Status: " + application.getFormStatus() + "\n");
        } else {
            System.out.println("No active application to view!\n");
        }

    }

    public boolean withdrawApplication() {
        // Logic for withdrawing application
        Application application = this.getApplication();

        // user has an active application
        if (!(application == null)) {
            application.setWithdrawalStatus("PENDING");
            System.out.println("Withdrawal submitted! Pending approval.\n");
            return true;
        } else {
            System.out.println("Withdrawal failed. You do not have an active application to withdraw from.\n");
            return false;
        }
    }

    //Enquiries specific methods
    public void createEnquiry(Project project, String enquiryString) {
        // Logic for creating enquiry
        LocalDateTime dateTime = LocalDateTime.now(); // Get the current date and time
        Enquiry enquiry = new Enquiry(Enquiry.getEnquiryCounter(), this, enquiryString, dateTime, project);
        enquiryList.add(enquiry);
    }

    public boolean editEnquiry(Integer id, String newEnquiryString) {
        // Logic for editing enquiry
        for (Enquiry enquiry : enquiryList) {
            if (enquiry.getId().equals(id)) {
                enquiry.setEnquiryString(newEnquiryString);
                System.out.println("Enquiry successfully updated!\n");
                return true;
            }
        } 
        System.out.println("Failed to edit. Enquiry not found.\n");
        return false;
    }

    public boolean deleteEnquiry(Integer id) {
        // Logic for deleting enquiry
        for (Enquiry enquiry : enquiryList) {
            if (enquiry.getId().equals(id)) {
                enquiryList.remove(enquiry);
                System.out.println("Enquiry successfully deleted!\n");
                return true;
            }
        } 
        System.out.println("Failed to delete. Enquiry not found.\n");
        return false;
    }
    }

