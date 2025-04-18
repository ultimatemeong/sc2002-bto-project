package Users;

import Misc.*;
import Enquiries.*;
import Projects.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Manager extends User{

    public Manager(String name, String nric, Integer age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public static Manager getManagerByNric(List<Manager> managerList, String nric) {
        for (Manager manager : managerList) {
            if (manager.getNric().equals(nric)) {
                return manager;
            }
        }
        return null; 
    }

    private boolean noActiveProjects(List<Project> projects) {
        for (Project project : projects) {
            if (project.getManager().equals(this) && project.isVisible()) {
                return false;
            }
        }
        return true;
    }

    public boolean createProject(List<Project> allProjects, String proj_name, String neighbourhood, String unitType1, Integer numUnitsType1, Integer priceType1, String unitType2, Integer numUnitsType2, Integer priceType2, LocalDate appOpenDate, LocalDate appCloseDate, boolean visibility, Integer officerSlots, List<Officer> officerList) {    
        if (noActiveProjects(allProjects)) {
            Project project = new Project(proj_name, neighbourhood, unitType1, numUnitsType1, priceType1, unitType2, 
            numUnitsType2, priceType2, appOpenDate, appCloseDate, visibility, this, officerSlots, officerList, 
            new ArrayList<Application>(), new ArrayList<Registration>(), new ArrayList<Enquiry>());
    
            AccessControl<Project> accessControl = new ProjectAccess();
            accessControl.add(project, this, "RW");
            System.out.println("Project successfully created!");
            return true;
        } else {
            System.out.println("Failed to create project. You are already managing an active project.");
            return false;
        }
    }

    public boolean toggleVisibility(Project project) {
        /* toggles project's visibility between true and false 
         * i.e. if current visibility is true, it will be set as false. */
        AccessControl<Project> accessControl = new ProjectAccess();
        if (accessControl.check(project, this).equals("RW")) {
            boolean newVis = !(project.isVisible());
            project.setVisibility(newVis);
            
            if (newVis) {
                System.out.println("Project visibility turned on.");
            } else {
                System.out.println("Project visibility turned off.");
            }
            return true;
        } else {
            System.out.println("Access denied.");
            return false;
        }
    }

    public boolean editProject(Project project, Project tempProject) {
        /*Check for different fields and update the original project object directly.
        Only flatsInfo, visibility, and officerSlots can be edited by Manager. */
        AccessControl<Project> accessControl = new ProjectAccess();
        if (accessControl.check(project, this).equals("RW")) {
            project.setFlatsInfo(tempProject.getFlatsInfo());
            project.setVisibility(tempProject.isVisible());
            project.setOfficerSlots(tempProject.getOfficerSlots());

            tempProject = null;
            System.out.println("Project details successfully updated.");
            return true;
        } else {
            System.out.println("Access denied.");
            return false;
        }
    }

    public void deleteProject(Project project) {
        AccessControl<Project> accessControl = new ProjectAccess();
        if (accessControl.check(project, this).equals("RW")) {
            accessControl.delete(project, this);
            System.out.println("Project " + project.getName() + " successfully deleted.");
        } else {
            System.out.println("Access denied.");
        }
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
    public List<Application> viewAllApplicantions(List<Project> projects) {
        // returns list of all applications of all projects
        List<Application> applications = new ArrayList<>();
        
        for (Project project : projects) {
            for (Application application : project.getApplicationList()) {
                applications.add(application);
            };
        }

        return applications;
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

    public void generateReport(List<Project> allProjects) {
        // Logic for generating reports
        List<Application> applications = viewAllApplicantions(allProjects);

        for (Application application : applications) {
            System.out.println("Flat type: " + application.getFlatType());
            System.out.println("Project name: " + application.getProject().getName());
            System.out.println("Applicant name: " + application.getUser().getName());
            System.out.println("Applicant marital status: " + application.getUser().getMaritalStatus());
        }
    }
}
