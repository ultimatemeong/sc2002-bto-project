package Users;

import Projects.*;
import java.time.LocalDate;
import java.util.List;

public class Manager extends User{

    public Manager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public String toString() {
        return "Manager: " + super.toString();
    }

    //Project specific methods
    public void viewAllProjects() {
        // Logic for viewing all projects
        // View all projects regardless of visibility
    }

    public void createProject(String proj_name, String neighbourhood, String unitType1, Integer numUnitsType1, Integer priceType1, String unitType2, Integer numUnitsType2, Integer priceType2, LocalDate appOpenDate, LocalDate appCloseDate, boolean visibility, Integer officerSlots, List<String> officerList) {
        Project project = new Project(proj_name, neighbourhood, unitType1, numUnitsType1, priceType1, unitType2, numUnitsType2, priceType2, appOpenDate, appCloseDate, visibility, this, officerSlots, officerList);

    }

    public void editProject() {
        // Logic for editing projects
    }

    public void deleteProject() {
        // Logic for deleting projects
    }

    public void reviewRegistration() {
        // Logic for reviewing registrations
    }
    
    //Applicants specific methods
    public void viewAllApplicants() {
        // Logic for viewing all applicants
    }

    public void reviewApplication() {
        // Logic for approving/denying application
    }

    public void reviewWithdrawal() {
        // Logic for approving/denying withdrawal
    }

    public void generateReport() {
        // Logic for generating reports
    }

    //Enquiries specific methods
    public void viewAllEnquiries() {
        // Logic for viewing all enquiries
    }

    public void replyEnquiry() {
        // Logic for reply enquiry
    }

}
