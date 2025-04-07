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
}
