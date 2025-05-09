package Misc;

import java.util.HashMap;
import java.util.Map;

import Projects.Application;
import Users.User;

/**
 * ApplicationAccess is a class that implements the AccessControl interface for managing access to applications.
 * It provides methods to check, add, and delete access levels for users on specific applications.
 * @author Ang Qi Xuan Evan
 * @version 1.0
 * @since 2025-04-23
 */
public class ApplicationAccess implements AccessControl<Application> {
    private static Map<String, Map<Integer, String>> applicationAccessMap = new HashMap<>();

    public String check(Application application, User user) {
        Integer id = application.getId();
        String userNRIC = user.getNric();
        Map<Integer, String> userAccessMap;

        if (!applicationAccessMap.containsKey(userNRIC)) {
            applicationAccessMap.put(userNRIC, new HashMap<>());
            userAccessMap = applicationAccessMap.get(userNRIC);
        } else {
            userAccessMap = applicationAccessMap.get(userNRIC);
        }

        if (!userAccessMap.containsKey(id)) {
            if (user.getClass().getSimpleName().equals("Manager")) {
                return "R"; // Manager has read access to all applications
            } else if (user.getClass().getSimpleName().equals("Officer") && application.getProject().getOfficerList().contains(user)) {
                return "R"; // Officer has read access to all applications for projects he is involved in
            } else {
                return "NULL"; // If user is applicant and not in accessmap
            }
        } else {
            return userAccessMap.get(id); // Return the access level if it exists
        }
    }

    public void add(Application application, User user, String accessLevel){
        Integer id = application.getId();
        String userNRIC = user.getNric();
        applicationAccessMap.putIfAbsent(userNRIC, new HashMap<>());
        applicationAccessMap.get(userNRIC).put(id, accessLevel);
    }

    public void delete(Application application, User user) {
        Integer id = application.getId();
        String userNRIC = user.getNric();
        if (applicationAccessMap.containsKey(userNRIC)) {
            applicationAccessMap.get(userNRIC).remove(id);
        }
    }
}
