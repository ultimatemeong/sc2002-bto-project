package Misc;

import java.util.HashMap;
import java.util.Map;

import Users.*;


public class AccessControl {
    private static final Map<String, String> accessMap = new HashMap<>();
    
    static {
        accessMap.put("Manager", "All");
        accessMap.put("Officer", "Officer");
        accessMap.put("Applicant", "Applicant");
    }

    public static String checkAccess(User user) {
        String result = "";
        switch (user.getClass().getSimpleName()) {
            case "Manager":
                result = "All";
                break;
            case "Officer":
                result = user.getName();
                break;
            case "Applicant":
                result = user.getName();
                break;
            default:
                System.out.println("Access denied.");

        }

        return result;
    }
    
}
