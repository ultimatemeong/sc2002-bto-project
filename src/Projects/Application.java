package Projects;

import Misc.AccessControl;
import Misc.ApplicationAccess;
import Users.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Application class represents an application for a flat in a project.
 * It extends the Form class and includes additional attributes and methods specific to applications.
 * @author Ang Qi Xuan Evan
 * @version 1.0
 * @since 2025-04-23
 */
public class Application extends Form implements IWithdrawable{
    private static Integer applicationCounter = 1;
    private String flatType;
    private Status withdrawalStatus; // Null, Pending, Approved, Rejected

    /**
     * Constructor for Application class
     * @param id
     * @param project
     * @param flatType
     * @param applicant
     * @param applicationDate
     * @param formStatus
     * @param withdrawalStatus
     */
    public Application(Integer id, Project project, String flatType, Applicant applicant, LocalDate applicationDate, String formStatus, String withdrawalStatus) {
        super(id, project, applicant, applicationDate, formStatus);
        this.flatType = flatType;
        this.withdrawalStatus = Status.valueOf(withdrawalStatus.toUpperCase());
        applicationCounter = id + 1; // Increment the counter for the next application
    }

    /**
     * Get the unique counter for applications
     * Generates the ID for the next application 
     * @return id for the next application
     */
    public static Integer getApplicationCounter() {
        return applicationCounter;
    }

    /**
     * Get the flat type of the application
     * @return flatType of the application
     */
    public String getFlatType() {
        return flatType;
    }

    /**
     * Gets the withdrawal status of the application
     * @return withdrawalStatus of the application
     */
    public String getWithdrawalStatus() {
        return withdrawalStatus.toString();
    }

    /**
     * Sets the withdrawal status of the application
     * @param withdrawalStatus of the application
     */
    public void setWithdrawalStatus(String withdrawalStatus) {
        this.withdrawalStatus = Status.valueOf(withdrawalStatus);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(flatType).append(",").append(user.getNric()).append(",").append(date).append(",");
        sb.append(formStatus.toString()).append(",").append(withdrawalStatus.toString()).append(",");
        sb.append(project.getName());
        return sb.toString();
    }

    /**
     * Filters the list of applications based on the user's access rights.
     * @param applicationList
     * @param user
     * @return a list of applications that the user can view
     */
    public static List<Application> viewApplications(List<Application> applicationList,User user) {;
        AccessControl<Application> accessControl = new ApplicationAccess();

        List<Application> readableApplications = applicationList.stream()
                .filter(application -> accessControl.check(application, user).contains("R"))
                .toList();

        return readableApplications;
    }
}
