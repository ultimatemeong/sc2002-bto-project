package App;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Enquiries.*;
import Users.*;
import Projects.*;
import Misc.*;

public class ManagerApp extends MainApp {
    
    public static void managerInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Manager Interface");
        System.out.println("1. Project Management");
        System.out.println("2. Account Management");
        System.out.println("3. Logout");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                projectInterface();
                break;
            case 2:
                MainApp.accountInterface();
                break;
            case 3:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void projectInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Project Management Interface");
        System.out.println("1. View Projects");
        System.out.println("2. Add Project");
        System.out.println("3. Edit Project");
        System.out.println("4. Delete Project");
        System.out.println("5. Back to Main Menu");

        int choice = scanner.nextInt();
        do {
            System.out.println("Please select an option:");
            switch (choice) {
                case 1:
                    List<Project> readableProjects = Project.viewProjects(all_projects, current_user);
                    System.out.println("1. View All Projects");
                    System.out.println("2. View Your Projects");
                    int viewby = scanner.nextInt();
                    switch (viewby) {
                        case 1:
                            for (Project project : readableProjects) {
                                System.out.println(project.toString());
                            }
                            break;
                        case 2:
                            for (Project project : readableProjects) {
                                if(project.getManager().equals(current_user.getName())) {
                                    System.out.println(project.toString());
                                }
                            }
                            break;
                        default:
                            break;
                    }
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Back to Main Menu...");
                    managerInterface();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);
    }
}
