package Projects;

import Users.*;
import java.util.Date;

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
}
