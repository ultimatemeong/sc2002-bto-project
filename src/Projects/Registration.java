package Projects;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Misc.AccessControl;
import Users.Officer;
import Users.User;

public class Registration {
    private Integer id;
    private Project project;
    private Officer officer;
    private Date registrationDate;
    private String status; // e.g. "Pending", "Approved", "Rejected"

    public Registration(Integer id, Project project, Officer officer, Date registrationDate, String status) {
        this.id = id;
        this.project = project;
        this.officer = officer;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public Officer getOfficer() {
        return officer;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public List<Registration> viewRegistrations(User user) {
        List<Registration> all_registrations = project.getRegistrationList();
        AccessControl<Registration> accessControl = new AccessControl<>();

        List<Registration> readableRegistrations = new ArrayList<>();
        for (Registration registration : all_registrations) {
            String access = accessControl.checkAccess(registration, user);
            if (access.contains("R")){
                readableRegistrations.add(registration);
            }
        }
        return readableRegistrations;
    }
}
