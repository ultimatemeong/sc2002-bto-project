package Misc;

import java.util.HashMap;
import java.util.Map;

import Projects.Application;
import Users.User;

public class ApplicationAccess implements AccessControl<Application> {
    private static Map<String, Map<Integer, String>> applicationAccessMap = new HashMap<>();

    public String check(Application application, User user) {
        Integer id = application.getId();
        String userNRIC = user.getNric();

        try {
            return applicationAccessMap.get(userNRIC).get(id);
        } catch (Exception e) {
            if (!applicationAccessMap.get(userNRIC).containsKey(id)) {
                if (user.getClass().getSimpleName().equals("Manager")) {
                    return "R"; // Manager has read access to all applications
                } else if (user.getClass().getSimpleName().equals("Officer") && application.getProject().getOfficerList().contains(user)) {
                    return "R"; // Officer has read access to all applications for projects he is involved in
                } else {
                    return "NULL"; // If user is applicant and not in accessmap
                }
            } 
        }
        return "NULL"; // Default return value if no access is found
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
