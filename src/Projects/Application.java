package Projects;

import Misc.AccessControl;
import Misc.ApplicationAccess;
import Users.*;

import java.time.LocalDate;
import java.util.List;

public class Application extends Form {
    private static Integer applicationCounter = 1;
    private String flatType;
    protected Status withdrawalStatus; // Null, Pending, Approved, Rejected

    public Application(Integer id, Project project, String flatType, Applicant applicant, LocalDate applicationDate, String formStatus, String withdrawalStatus) {
        super(id, project, applicant, applicationDate, formStatus);
        this.flatType = flatType;
        this.withdrawalStatus = Status.valueOf(withdrawalStatus.toUpperCase());
        applicationCounter = id + 1; // Increment the counter for the next application
    }

    public static Integer getApplicationCounter() {
        return applicationCounter;
    }

    public String getFlatType() {
        return flatType;
    }

    public String getWithdrawalStatus() {
        return withdrawalStatus.toString();
    }

    public void setWithdrawalStatus(String withdrawalStatus) {
        this.withdrawalStatus = Status.valueOf(withdrawalStatus);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(flatType).append(",").append(user.getNric()).append(",").append(date).append(",");
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
