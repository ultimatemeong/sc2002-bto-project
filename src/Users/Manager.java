package Users;

import Misc.*;
import Enquiries.*;
import Projects.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public void createProject(String proj_name, String neighbourhood, String unitType1, Integer numUnitsType1, Integer priceType1, String unitType2, Integer numUnitsType2, Integer priceType2, LocalDate appOpenDate, LocalDate appCloseDate, boolean visibility, Integer officerSlots, List<Officer> officerList) {
        Project project = new Project(proj_name, neighbourhood, unitType1, numUnitsType1, priceType1, unitType2, numUnitsType2, priceType2, appOpenDate, appCloseDate, visibility, this, officerSlots, officerList, new ArrayList<Application>(), new ArrayList<Registration>(), new ArrayList<Enquiry>());

        AccessControl<Project> accessControl = new AccessControl<>();
        accessControl.add(project, this, "RW");
    }

    public void editProject(Project project, Project tempProject) {
        /*Check for different fields and update the original project object directly.
        Only flatsInfo, visibility, and officerSlots can be edited by Manager. */
        project.setFlatsInfo(tempProject.getFlatsInfo());
        project.setVisibility(tempProject.isVisible());
        project.setOfficerSlots(tempProject.getOfficerSlots());

        tempProject = null;
    }

    public void deleteProject(Project project) {
        AccessControl<Project> accessControl = new AccessControl<>();
        accessControl.delete(project, this);
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
