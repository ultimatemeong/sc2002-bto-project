package Projects;

import java.util.Date;

import Users.Applicant;

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
}
