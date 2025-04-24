package Users;

import Enquiries.Enquiry;
import Projects.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Officer class represents an officer in the system.
 * It extends the User class and includes attributes such as application and enquiry list.
 * @author Ang Qile Dora
 * @version 1.0
 * @since 2025-04-23
 */
public class Officer extends Applicant{
    private Registration registration;

    /**
     * Constructor for Officer class with no existing application and registration
     * @param name
     * @param nric
     * @param age
     * @param maritalStatus
     * @param password
     * @param enquiryList
     */
    public Officer(String name, String nric, Integer age, String maritalStatus, String password, List<Enquiry> enquiryList) {
        super(name, nric, age, maritalStatus, password, enquiryList);
    }

    /**
     * Constructor for Officer class with existing application and registration
     * @param name
     * @param nric
     * @param age
     * @param maritalStatus
     * @param password
     * @param application
     * @param enquiryList
     * @param registration
     */
    public Officer(String name, String nric, Integer age, String maritalStatus, String password, Application application, List<Enquiry> enquiryList, Registration registration) {
        super(name, nric, age, maritalStatus, password, application, enquiryList);
        this.registration = registration;
    }

    /**
     * Get Registration for Officer class
     * @return registration
     */
    public Registration getRegistration() {
        return registration;
    }

    /**
     * Search for officer by NRIC in the list of officers
     * @param officerList
     * @param nric
     * @return
     */
    public static Officer getOfficerByNric(List<Officer> officerList, String nric) {
        for (Officer officer : officerList) {
            if (officer.getNric().equals(nric)) {
                return officer;
            }
        }
        return null; 
    }

    /**
     * Set Registration for Officer class
     * @param registration
     */
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    /**
     * Check if the officer is in charge of the project
     * @param project
     * @return true if the officer is in charge of the project, false otherwise
     */
    private Application retrieveApplication(Project project, String applicantNric) {
        for (Application application : project.getApplicationList()) {
            if (application.getUser().getNric().equals(applicantNric)) {
                return application;
            }
        }
        System.out.println("Application not found.");
        return null;
    }

    /**
     * Check if the officer is not an applicant for the project
     * @param project
     * @return true if the officer is not an applicant for the project, false otherwise
     */
    private boolean notApplicant(Project project) {
        for (Application application : project.getApplicationList()) {
            if (application.getUser().getNric().equals(this.getNric())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the officer is not an applicant for the project
     * @param project
     * @return true if the officer is not an applicant for the project, false otherwise
     */
    private void flatSelectionReceipt(Application application) {
        Applicant applicant = (Applicant) application.getUser();
        Project project = application.getProject();

        System.out.println("Applicant Details:");
        System.out.println("\tName: " + applicant.getName());
        System.out.println("\tNRIC: " + applicant.getNric());
        System.out.println("\tAge: " + applicant.getAge());
        System.out.println("\tMarital Status: " + applicant.getMaritalStatus());
        System.out.println("\tFlat Type Applied: " + application.getFlatType());

        System.out.println("\nProject Details:");
        System.out.println("\tName: " + project.getName());
        System.out.println("\tNeighbourhood: " + project.getNeighbourhood());
    }

    /**
     * Create a new registration for the officer for a project
     * @param project
     * @return True if registration is successful, false otherwise
     */
    public boolean registerForProject(Project project) {
        if ((this.registration == null) && notApplicant(project)) {
            Registration registration = new Registration(Registration.getRegistrationCounter(), project, this, LocalDate.now(), "PENDING");
            project.addToRegistrationList(registration);
            System.out.println("Registration submitted! Pending approval.");
            return true;
        } else {
            System.out.println("Registration failed. You already have an active registration.");
            return false;
        }
    }

    /**
     * Check if the officer is in charge of the project
     * @return project if the officer is in charge, null otherwise
     */
    public Project projectInCharge() {
        Registration registration = this.getRegistration();
        // has active registration
        if (!(registration == null)) {
            if (registration.getFormStatus().equals("APPROVED")) {
                System.out.println("You are currently in charge of: " + registration.getProject().getName());
                return registration.getProject();
            } else {
                System.out.println("Your registration for " + registration.getProject().getName() + " has not been approved yet.");
                return null;
            }

        // no active registration
        } else {
            System.out.println("You are currently not in charge of any projects.");
            return null;
        }
    }

    /**
     * Gets the registration status of the officer
     * * @return registration status of the officer if the officer is in charge of a project, null otherwise
     */
    public void viewRegistrationStatus() {
        Registration registration = this.getRegistration();

        // user has an active registration
        if (!(registration == null)) {
            System.out.println("Project: " + registration.getProject().getName());
            System.out.println("Neighbourhood: " + registration.getProject().getNeighbourhood());
            System.out.println("Registration Status: " + registration.getFormStatus() + "\n");
        } else {
            System.out.println("No active registration to view!\n");
        }
    }

    /**
     * Books a flat for the Applicant if the application is Approved
     * @param project
     * @param applicantNRIC
     * @return true if booking is successful, false otherwise
     */
    public boolean chooseFlat(Project project, String applicantNRIC) {
        Application application = retrieveApplication(project, applicantNRIC);

        if (application.getFormStatus().equals("APPROVED")) {
            String flatType = application.getFlatType();
            Map<String, List<Integer>> flatsInfo = project.getFlatsInfo();
            
            // update remaining flats 
            for (String unitType : flatsInfo.keySet()) {
                if (unitType.equals(flatType)) {
                    List<Integer> flatInfo = flatsInfo.get(unitType);
                    Integer numFlats = flatInfo.get(0);
                    flatInfo.set(0, numFlats-1);
                }
            }

            // update application status 
            application.setFormStatus("BOOKED");

            System.out.println(flatType + " flat under Project " + project.getName() + " successfully booked!");
            System.out.println();
            flatSelectionReceipt(application);
            return true;
        }

        System.out.println("Application not approved yet. Unable to book flat.");
        return false;
    }
}
