package Misc;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import Users.*;
import Projects.*;
import Enquiries.*;

public class AccessControl<T> {
    private static Map<String, Map<String, String>> projectAccessMap = new HashMap<>();
    private static Map<String, Map<String, String>> enquiryAccessMap = new HashMap<>();
    private static Map<String, Map<String, String>> applicationAccessMap = new HashMap<>();
    
    public String checkAccess(T t, User user){
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
                return "Invalid type";
        }

        String userNRIC = user.getNric();
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
