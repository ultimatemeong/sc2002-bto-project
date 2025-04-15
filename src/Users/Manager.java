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

    public void viewAllProjects() {
        // Logic for viewing all projects
        // View all projects regardless of visibility
    }

    public void createProject(String proj_name, String neighbourhood, String unitType1, Integer numUnitsType1, Integer priceType1, String unitType2, Integer numUnitsType2, Integer priceType2, LocalDate appOpenDate, LocalDate appCloseDate, boolean visibility, Integer officerSlots, List<Officer> officerList) {
        Project project = new Project(proj_name, neighbourhood, unitType1, numUnitsType1, priceType1, unitType2, numUnitsType2, priceType2, appOpenDate, appCloseDate, visibility, this, officerSlots, officerList, new ArrayList<Application>(), new ArrayList<Registration>(), new ArrayList<Enquiry>());

        AccessControl<Project> accessControl = new ProjectAccess();
        accessControl.add(project, this, "RW");
    }

    public void toggleVisibility(Project project) {
        /* toggles project's visibility between true and false 
         * i.e. if current visibility is true, it will be set as false. */
        boolean currentVis = project.isVisible();
        project.setVisibility(!currentVis);
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
        AccessControl<Project> accessControl = new ProjectAccess();
        accessControl.delete(project, this);
    }

    public void reviewRegistration(Registration registration, boolean accepted) {
        /* approve or reject officer's registration for a project */ 
        Project project = registration.getProject();

        // only can approve registration if he is the manager in charge of the project 
        if (accepted && project.getManager().equals(this)) {
            Officer officer = (Officer) registration.getUser();
            Integer officerSlots = project.getOfficerSlots();
            project.getOfficerList().add(officer); // add officer to project's officer list
            project.setOfficerSlots(officerSlots-1); // update remaining officer slots
            registration.setFormStatus("Approved");

            // allow officer to view project
            AccessControl<Project> accessControl = new ProjectAccess();
            accessControl.add(project, officer, "R");
        } else {
            registration.setFormStatus("Rejected");
        }
    }
    
    //Applicants specific methods
    public List<Applicant> viewAllApplicants(List<Project> projects) {
        // returns list of all applicants of all projects
        List<Applicant> applicants = new ArrayList<>();
        List<Application> applicationList = new ArrayList<>();
        
        for (Project project : projects) {
            for (Application application : project.getApplicationList()) {
                applicationList.add(application);
            };
        }

        for (Application application : applicationList) {
            applicants.add((Applicant) application.getUser());
        }
        return applicants;
    }

    public void reviewApplication(Application application, boolean accepted) {
        /* approve or reject applicant's application for a project */ 
        if (accepted) {
            application.setFormStatus("Approved");
        } else {
            application.setFormStatus("Rejected");
        }
    }

    public void reviewWithdrawal(Application application, boolean accepted) {
        /* approve or reject applicant's withdrawal for a project */ 
        if (accepted) {
            application.setWithdrawalStatus("Approved");
        } else {
            application.setWithdrawalStatus("Rejected");
        }
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
