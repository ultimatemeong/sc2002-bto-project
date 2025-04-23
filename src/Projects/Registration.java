package Projects;

import Users.Officer;
import Users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * The Registration class represents a registration for a project.
 * It extends the Form class and includes additional attributes and methods specific to registrations.
 * @author Ang Qi Xuan Evan
 * @version 1.0
 * @since 2025-04-23
 */
public class Registration extends Form{
    private static Integer registrationCounter = 1;

    /**
     * Constructor for Registration class
     * @param id The unique ID of the registration.
     * @param project The project related to this registration.
     * @param officer The officer who processed the registration.
     * @param registrationDate The date when the registration was made.
     * @param formStatus The status of the form (e.g., Pending, Approved, Rejected).
     */
    public Registration(Integer id, Project project, Officer officer, LocalDate registrationDate, String formStatus) {
        super(id, project, officer, registrationDate, formStatus);
        registrationCounter = id + 1;
    }

    /**
     * Gets the unique ID for the next Registration.
     * @return the Counter for number of Registrations.
     */
    public static Integer getRegistrationCounter() {
        return registrationCounter;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(user.getNric()).append(",").append(date).append(",");
        sb.append(formStatus.toString()).append(",");
        sb.append(project.getName());
        return sb.toString();
    }

    /**
     * Filters the list of registrations based on the user's role.
     * @param registrationList The list of registrations to filter.
     * @param user The user whose role is used to filter the registrations. 
     * @returns a list of registrations that the user can view.
     */
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
