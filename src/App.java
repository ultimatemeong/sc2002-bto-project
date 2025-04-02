import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Users.Applicant;
import Users.Manager;
import Users.Officer;
import Projects.Project;
import Misc.*;

public class App {
        
    public static void main(String[] args) throws Exception {
        Manager manager = new Manager("John Doe", "S1234567A", 35, "Single", "password123");
        Officer officer = new Officer("Daniel", "S7654321B", 30, "Married", "password456");
        Applicant applicant = new Applicant("Alice Tan", "S9876543C", 28, "Single", "password789");

        // Load projects from a file or database
        List<Project> projects = new ArrayList<>();
        Project project1 = new Project("Project A", "Woodlands", new String[]{"2-room", "3-room"}, new int[]{300000, 500000}, new int[]{100, 50}, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31));    
        project1.addOfficer(officer);
        projects.add(project1);

        System.out.println(AccessControl.checkAccess(manager, projects));
        System.out.println(AccessControl.checkAccess(officer, projects));
        System.out.println(AccessControl.checkAccess(applicant, projects)); 
    }
}
