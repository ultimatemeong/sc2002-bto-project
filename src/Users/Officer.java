package Users;

import Enquiries.Enquiry;
import Projects.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Officer extends Applicant{
    private Registration registration;

    // Constructor for officer with no existing application and registration
    public Officer(String name, String nric, Integer age, String maritalStatus, String password, List<Enquiry> enquiryList) {
        super(name, nric, age, maritalStatus, password, enquiryList);
    }

    // Constructor for officer with existing application and registration
    public Officer(String name, String nric, Integer age, String maritalStatus, String password, Application application, List<Enquiry> enquiryList, Registration registration) {
        super(name, nric, age, maritalStatus, password, application, enquiryList);
        this.registration = registration;
    }

    public Registration getRegistration() {
        return registration;
    }

    public static Officer getOfficerByNric(List<Officer> officerList, String nric) {
        for (Officer officer : officerList) {
            if (officer.getNric().equals(nric)) {
                return officer;
            }
        }
        return null; 
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    private Application retrieveApplication(Project project, String applicantNric) {
        for (Application application : project.getApplicationList()) {
            if (application.getUser().getNric().equals(applicantNric)) {
                return application;
            }
        }
        System.out.println("Application not found.");
        return null;
    }

    private boolean notApplicant(Project project) {
        for (Application application : project.getApplicationList()) {
            if (application.getUser().getNric().equals(this.getNric())) {
                return false;
            }
        }
        return true;
    }

    private void flatSelectionReceipt(Application application) {
        // Logic for flat selection receipt
        Applicant applicant = (Applicant) application.getUser();
        Project project = application.getProject();

        System.out.println("Applicant Details:");
        applicant.toString();

        System.out.println("\nProject Details:");
        project.toString();
    }

    public boolean registerForProject(Project project) {
        // Logic for registering for projects
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

    public Project projectInCharge() {
        Registration registration = this.getRegistration();
        // has active registration
        if (!(registration == null)) {
            if (registration.getFormStatus().equals("SUCCESSFUL")) {
                System.out.println("You are current in charge of: " + registration.getProject().getName());
                return registration.getProject();
            } else {
                return null;
            }

        // no active registration
        } else {
            System.out.println("You are currently not in charge of any projects.");
            return null;
        }
    }

    public void viewRegistrationStatus() {
        // Logic for viewing registration status
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

    public boolean chooseFlat(Project project, String applicantNRIC) {
        // Logic for choosing project
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
