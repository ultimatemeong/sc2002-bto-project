package Misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import Users.*;
import Projects.*;

public class AccessControl {
    private static final Map<String, String> accessMap = new HashMap<>();
    
    static {
        accessMap.put("Manager", "All");
        accessMap.put("Officer", "Officer");
        accessMap.put("Applicant", "Applicant");
    }

    public static List<Project> checkAccess(User user, List<Project> Data) {
        List<Project> projects = new ArrayList<>();
        switch (user.getClass().getSimpleName()) {
            case "Manager":
                projects = Data;
                break;
            case "Officer":
                // Check if the officer is in the project officer list
                for (Project project : Data) {
                    if (project.getOfficerList().contains(user.getName())) {
                        projects.add(project);
                    }
                }
                
                // Check if the officer is in the project applicant list
                for (Project project : Data) {
                    if (project.getApplicantList().contains(user.getName())) {
                        projects.add(project);
                    }
                }

                break;
            case "Applicant":
                for (Project project : Data) {
                    if (project.getApplicantList().contains(user.getName())) {
                        projects.add(project);
                    }
                }
                break;
            default:
                System.out.println("Access denied.");

        }

        return projects;
    }
    
}
