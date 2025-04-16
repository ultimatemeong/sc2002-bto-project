package Projects;

import Users.Officer;
import Users.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Registration extends Form{
    private static Integer registrationCounter = 1;

    public Registration(Integer id, Project project, Officer officer, Date registrationDate, String formStatus) {
        super(id, project, officer, registrationDate, formStatus);
        registrationCounter = id + 1;
    }

    public static Integer getRegistrationCounter() {
        return registrationCounter;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(user.getName()).append(",").append(date).append(",");
        sb.append(formStatus.toString()).append(",").append(withdrawalStatus.toString()).append(",");
        sb.append(project.getName()).append(",");
        return sb.toString();
    }

    public static List<Registration> viewRegistrations(List<Registration> registrationList,User user) {

        if ((user.getClass().getSimpleName()).equals("Manager")) {
            return registrationList;
        }

        List<Registration> readableRegistrations = new ArrayList<>();

        for (Registration registration : registrationList) {
            if (registration.getUser().equals(user)) {
                readableRegistrations.add(registration);
            }
        }

        return readableRegistrations;
    }
}
