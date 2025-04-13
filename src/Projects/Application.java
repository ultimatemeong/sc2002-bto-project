package Projects;

import Misc.AccessControl;
import Users.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application extends Form {
    private static Integer applicationCounter = 0;
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

    public List<Application> viewApplications(User user) {
        List<Application> all_applications = project.getApplicationList();
        AccessControl<Application> accessControl = new AccessControl<>();

        List<Application> readableApplications = new ArrayList<>();
        for (Application application : all_applications) {
            String access = accessControl.checkAccess(application, user);
            if (access.contains("R")){
                readableApplications.add(application);
            }
        }
        return readableApplications;
    }
}
