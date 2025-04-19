package Misc;

import Projects.Project;
import Users.User;
import java.util.HashMap;
import java.util.Map;

public class ProjectAccess implements AccessControl<Project> {
    private static Map<String, Map<String, String>> projectAccessMap = new HashMap<>();

    public String check(Project project, User user){
        String id = project.getName();
        String userNRIC = user.getNric();

        try {
            return projectAccessMap.get(userNRIC).get(id);
        } catch (Exception e) {
            if (!projectAccessMap.containsKey(userNRIC)) {
                projectAccessMap.put(userNRIC, new HashMap<>());
            }
            if (!projectAccessMap.get(userNRIC).containsKey(id)) {
                if (user.getClass().getSimpleName().equals("Manager")) {
                    return "R"; // Manager has read access to all projects
                } else {
                    if (!project.isVisible()) {
                        return "NULL"; // If the project is not visible and the user is not a manager, return "NULL"
                    } else {
                        return "R"; // If the project is visible, return "R" for read access
                    }
                }
            } 
        }
        return "NULL"; // Default return value if no access is found
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
