package Misc;

import Enquiries.*;
import Projects.*;
import Users.*;
import java.util.HashMap;
import java.util.Map;

public class AccessControl<T> {
    private static Map<String, Map<String, String>> projectAccessMap = new HashMap<>();
    private static Map<String, Map<String, String>> enquiryAccessMap = new HashMap<>();
    private static Map<String, Map<String, String>> applicationAccessMap = new HashMap<>();
    
    public String checkAccess(T t, User user) {
        Map<String, Map<String, String>> accessMap;
        String id;
        String userNRIC = user.getNric();

        switch (t.getClass().getSimpleName()) {
            case "Project":
                accessMap = projectAccessMap;
                id = ((Project) t).getName();

                if (!((Project) t).isVisible() && !user.getClass().getSimpleName().equals("Manager")) {
                    // If the project is not visible and the user is not a manager, return "NULL"
                    return "NULL";
                } else if (((Project) t).isVisible()) {
                    return "R"; // If the project is visible, return "R" for read access
                }
                break;

            case "Enquiry":
                accessMap = enquiryAccessMap;
                id = ((Enquiry) t).getId().toString();

                // If user is in the Officer List of the project 
                if (((Enquiry) t).getProject().getOfficerList().contains(user)){
                    return "R"; // Would be an officer if in officer list
                }
                break;

            case "Application":
                accessMap = applicationAccessMap;
                id = ((Application) t).getId().toString();

                // If user is in the Officer List of the project
                if (((Application) t).getProject().getOfficerList().contains(user)){
                    return "RW"; // Can edit status, thus RW
                }
                break;
                
            default:
                return "Invalid type";
        }

        if (!accessMap.containsKey(userNRIC)) {
            return "NULL";
        }

        if (!accessMap.get(userNRIC).containsKey(id)) {
            if (user.getClass().getSimpleName().equals("Manager")) {
                return "R"; // Manager has read access to all projects/enquiries/applications
            } else {
                return "NULL"; // No access for other users
            }
        } 
        
        Map<String, String> data = accessMap.get(userNRIC);
        return data.get(id);
    }

    public void add(T t, User user, String accessLevel) {
        Map<String, Map<String, String>> accessMap;
        String id;
        switch (t.getClass().getSimpleName()) {
            case "Project":
                accessMap = projectAccessMap;
                id = ((Project) t).getName();
                break;

            case "Enquiry":
                accessMap = enquiryAccessMap;
                id = ((Enquiry) t).getId().toString();
                break;

            case "Application":
                accessMap = applicationAccessMap;
                id = ((Application) t).getId().toString();

                // Give the project manager read and write(to change status) access to the application
                Manager projManager = ((Application) t).getProject().getManager();
                accessMap.putIfAbsent(projManager.getNric(), new HashMap<>());
                accessMap.get(projManager.getNric()).put(id, "RW"); // Give the project manager read access to the application
                break;
                
            default:
                return;
        }

        String userNRIC = user.getNric();
        accessMap.putIfAbsent(userNRIC, new HashMap<>());
        accessMap.get(userNRIC).put(id, accessLevel);

    }

    public void delete(T t, User user) {
        Map<String, Map<String, String>> accessMap;
        String id;
        switch (t.getClass().getSimpleName()) {
            case "Project":
                accessMap = projectAccessMap;
                id = ((Project) t).getName();
                break;

            case "Enquiry":
                accessMap = enquiryAccessMap;
                id = ((Enquiry) t).getId().toString();
                break;

            case "Application":
                accessMap = applicationAccessMap;
                id = ((Application) t).getId().toString();
                break;
                
            default:
                return;
        }

        String userNRIC = user.getNric();
        if (accessMap.containsKey(userNRIC)) {
            accessMap.get(userNRIC).remove(id);
        }
    }
}
