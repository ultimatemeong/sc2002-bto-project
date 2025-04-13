package Projects;

import Users.Officer;
import Users.User;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Registration extends Form{
    private static Integer registrationCounter = 0;

    public Registration(Integer id, Project project, Officer officer, Date registrationDate, String formStatus, String withdrawalStatus) {
        super(id, project, officer, registrationDate, formStatus, withdrawalStatus);
        registrationCounter = id + 1;
    }

    public Integer getRegistrationCounter() {
        return registrationCounter;
    }

    public List<Registration> viewRegistrations(User user) {
        Project project = this.getProject();
        List<Registration> registrations = project.getRegistrationList();

        if ((user.getClass().getSimpleName()).equals("Manager")) {
            return registrations;
        }

        List<Registration> readableRegistrations = new ArrayList<>();

        for (Registration registration : registrations) {
            if (registration.getUser().equals(user)) {
                readableRegistrations.add(registration);
            }
        }

        return readableRegistrations;
    }
}
