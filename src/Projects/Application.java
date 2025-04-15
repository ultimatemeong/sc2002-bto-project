package Projects;

import Users.*;
import java.util.Date;
import java.util.List;

import Misc.AccessControl;
import Misc.ApplicationAccess;

public class Application extends Form {
    private static Integer applicationCounter = 1;
    private String flatType;

    // Used when loading from CSV
    public Application(Integer id, Project project, String flatType, Applicant applicant, Date applicationDate, String formStatus, String withdrawalStatus) {
        super(id, project, applicant, applicationDate, formStatus, withdrawalStatus);
        this.flatType = flatType;
        applicationCounter = id + 1; // Increment the counter for the next application
    }

    public Integer getApplicationCounter() {
        return applicationCounter;
    }

    public String getFlatType() {
        return flatType;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(flatType).append(",").append(user.getName()).append(",").append(date).append(",");
        sb.append(formStatus.toString()).append(",").append(withdrawalStatus.toString()).append(",");
        sb.append(project.getName()).append(",");
        return sb.toString();
    }

    public static List<Application> viewApplications(List<Application> applicationList,User user) {;
        AccessControl<Application> accessControl = new ApplicationAccess();

        List<Application> readableApplications = applicationList.stream()
                .filter(application -> accessControl.check(application, user).contains("R"))
                .toList();

        return readableApplications;
    }
}
