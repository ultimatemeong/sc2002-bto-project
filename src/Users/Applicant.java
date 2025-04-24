package Users;

import Enquiries.Enquiry;
import Misc.*;
import Projects.Application;
import Projects.Project;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Applicant class represents an applicant in the system.
 * It extends the User class and includes attributes such as application and enquiry list.
 * @author Ang Qile Dora
 * @version 1.0
 * @since 2025-04-23
 */
public class Applicant extends User{
    private Application application; 
    private List<Enquiry> enquiryList = new ArrayList<>(); // List of enquiries made by the applicant

    /**
     * Constructor for Applicant class with no existing application
     * @param name
     * @param nric
     * @param age
     * @param maritalStatus
     * @param password
     * @param enquiryList
     */
    public Applicant(String name, String nric, Integer age, String maritalStatus, String password, List<Enquiry> enquiryList) {
        super(name, nric, age, maritalStatus, password);
        this.enquiryList = enquiryList;
    }

    /**
     * Constructor for Applicant class with existing application and enquiry list
     * @param name
     * @param nric
     * @param age
     * @param maritalStatus
     * @param password
     * @param application
     * @param enquiryList
     */
    public Applicant(String name, String nric, Integer age, String maritalStatus, String password, Application application, List<Enquiry> enquiryList) {
        super(name, nric, age, maritalStatus, password);
        this.application = application;
        this.enquiryList = enquiryList;
    }

    /**
     * Get the application for the applicant
     * @return application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Finds the applicant by NRIC in the list of applicants
     * @param applicantList
     * @param nric
     * @return applicant
     */
    public static Applicant getApplicantByNric(List<Applicant> applicantList, String nric) {
        for (Applicant applicant : applicantList) {
            if (applicant.getNric().equals(nric)) {
                return applicant;
            }
        }
        return null; 
    }

    public List<Enquiry> getEnquiryList() {
        return this.enquiryList;
    }

    /**
     * Set the application for the applicant
     * @param application
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /** 
     * Checks if the applicant is an officer for the project
     * @param project
     * @return true if the applicant is an officer for the project, false otherwise
     */
    private boolean isProjectOfficer(Project project) {
        List<Officer> officerList = project.getOfficerList();
        for (Officer officer : officerList) {
            if (officer.getNric().equals(this.getNric())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Applicant applies for a project
     * Checks if the applicant has an active application and if they are an officer for the project.
     * If not, creates a new application and adds it to the applicant's application list.
     * Also adds user to the project's access control list with read permission.
     * @param project
     * @param flatType
     * @return True if application is successful, false otherwise
     */
    public boolean applyForProject(Project project, String flatType) {
        /* applicant applies for a project */
        // user has no active application and is not an officer for this project
        if ((this.getApplication() == null) && !(isProjectOfficer(project))) {
            Application application = new Application(Application.getApplicationCounter(), project, flatType, this, LocalDate.now(), "Pending", "Null");
            this.setApplication(application);
            project.addToApplicationList(application); 

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

    /**
     * Applicant views their application status
     * If the applicant has an active application, it displays the project name, neighbourhood, flat type applied, and application status.
     * If not, it informs the user that there is no active application to view.
     */
    public void viewApplicationStatus() {
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

    /**
     * Withdraws the application for the applicant
     * Checks if the applicant has an active application. If so, sets the withdrawal status to pending.
     * If not, informs the user that there is no active application to withdraw from.
     * @return
     */
        public boolean withdrawApplication() {
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

    /**
     * Add an enquiry to the enquiry list of the applicant.
     * @param enquiry
     */
    public void addToEnquiryList(Enquiry enquiry) {
        this.enquiryList.add(enquiry);
    }

    /**
     * Removes an enquiry from the enquiry list of the applicant.
     * @param enquiry
     */
    public void removeFromEnquiryList(Enquiry enquiry) {
        this.enquiryList.remove(enquiry);
    }

    /**
     * Creates an enquiry for the applicant
     * * Checks if the applicant has an active application. If so, creates a new enquiry and adds it to the enquiry list for both applicant and project.
     * * If not, informs the user that there is no active application to create an enquiry for.
     * @param project
     * @param enquiryString
     */
    public void createEnquiry(Project project, String enquiryString) {
        LocalDateTime dateTime = LocalDateTime.now(); // Get the current date and time
        Enquiry enquiry = new Enquiry(Enquiry.getEnquiryCounter(), this, enquiryString, dateTime, project);
        this.addToEnquiryList(enquiry);
        project.addToEnquiryList(enquiry);
        System.out.println("Enquiry successfully submitted!\n");
    }

    /**
     * Edits an existing enquiry for the applicant
     * @param id
     * @param newEnquiryString
     * @return true if the enquiry is successfully edited, false otherwise
     */
    public boolean editEnquiry(Integer id, String newEnquiryString) {
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

    /**
     * Deletes an existing enquiry for the applicant
     * @param id
     * @return true if the enquiry is successfully deleted, false otherwise
     */
    public boolean deleteEnquiry(Integer id) {
        for (Enquiry enquiry : enquiryList) {
            if (enquiry.getId().equals(id)) {
                Project project = enquiry.getProject();
                this.removeFromEnquiryList(enquiry);
                project.removeFromEnquiryList(enquiry);
                System.out.println("Enquiry successfully deleted!\n");
                return true;
            }
        } 
        System.out.println("Failed to delete. Enquiry not found.\n");
        return false;
    }
}

