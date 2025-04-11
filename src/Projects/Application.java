package Projects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Users.*;
import Misc.AccessControl;

public class Application {
    private Integer id;
    private Project project;
    private String flatType;
    private Applicant applicant;
    private Date applicationDate;
    private String status; // e.g. "Pending", "Approved", "Rejected"

    public Application(Integer id, Project project, String flatType, Applicant applicant, Date applicationDate, String status) {
        this.id = id;
        this.project = project;
        this.flatType = flatType;
        this.applicant = applicant;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getFlatType() {
        return flatType;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
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
