package Misc;

import Projects.Project;
import Users.User;
import java.util.HashMap;
import java.util.Map;

public class ProjectAccess implements AccessControl<Project> {
    private static Map<String, Map<String, String>> projectAccessMap = new HashMap<>();

    private String checkMartitalStatus(User user, Project project) {
        switch (user.getMaritalStatus()) {
            case "SINGLE":
                if (project.getFlatsInfo().get("2-Room").get(0) > 0) {
                    return "R";
                } else {
                    return "NULL"; // If no 2-Rooms are available, return "NULL"
                }

            case "MARRIED":
                if (project.getFlatsInfo().get("2-Room").get(0) > 0 || project.getFlatsInfo().get("3-Room").get(0) > 0) {
                    return "R";
                } else {
                    return "NULL"; // If no 2-Rooms are available, return "NULL"
                }

            default:
                return "NULL"; // If marital status is not recognized, return "NULL"
        }
    }

    public String check(Project project, User user){
        String id = project.getName();
        String userNRIC = user.getNric();
        Map<String, String> userAccessMap;

        if (!projectAccessMap.containsKey(userNRIC)) {
            projectAccessMap.put(userNRIC, new HashMap<>());
            userAccessMap = projectAccessMap.get(userNRIC);
        } else {
            userAccessMap = projectAccessMap.get(userNRIC);
        }


        if (!userAccessMap.containsKey(id)) {
            if (user.getClass().getSimpleName().equals("Manager")) {
                return "R"; // Manager has read access to all projects
            } else {
                if (project.isVisible()) {
                    return checkMartitalStatus(user, project);
                } else {
                    return "NULL"; // If the project is not visible, return "NULL"\
                }
            }
        } else {
            return userAccessMap.get(id); // Return the access level if it exists
        } 
    }

    public void add(Project project, User user, String accessLevel){
        String id = project.getName();
        String userNRIC = user.getNric();
        projectAccessMap.putIfAbsent(userNRIC, new HashMap<>());
        projectAccessMap.get(userNRIC).put(id, accessLevel);
    }

    public void delete(Project project, User user) {
        String id = project.getName();
        String userNRIC = user.getNric();
        if (projectAccessMap.containsKey(userNRIC)) {
            projectAccessMap.get(userNRIC).remove(id);
        }
    }
}
