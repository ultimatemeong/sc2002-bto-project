package App;

import Projects.Project;
import Users.Applicant;
import java.util.List;
import java.util.Scanner;

public class ApplicantApp extends MainApp {
    public static void applicantInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Applicant Interface");
        System.out.println("1. Project Management");
        System.out.println("2. Application Management");
        System.out.println("3. Account Management");
        System.out.println("4. Logout");
    }

    private static void projectInterface() {
        List<Project> readableProjects = Project.viewProjects(all_projects, current_user).stream()
            .sorted().toList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Project Management Interface");
        System.out.println("1. View Projects");
        System.out.println("2. Back to Main Menu");
        int choice;
        
        do {
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                // project management interface
                case 1:
                    System.out.println("1. View All Projects");
                    System.out.println("2. View Project Application Status");

                    int viewby = scanner.nextInt();
                    switch (viewby) {
                        // view all projects
                        case 1:
                            int i = 1;
                            for (Project project : readableProjects) {
                                System.out.println(i + ". " + project.getName() + ": " + project.getNeighbourhood());
                                i++;
                            }

                            // select project
                            System.out.println("Please select an option:");
                            System.out.println("1. Apply For a Project");
                            System.out.println("2. Enquire About a Project");

                            int projActionChoice = scanner.nextInt();
                            switch (projActionChoice) {
                                // apply
                                case 1:
                                    // applicationInterface();
                                    break;
                                
                                // enquire
                                case 2:
                                    // enquiryInterface();
                                    break;
                            }

                            break;

                        // application management
                        case 2:
                            
                            
                            break;
                        default:
                            break;
                    }
                    break;

                // application management interface
                case 2:

                    break;

                // account management interface
                case 3:

                    break;

            }
        } while (choice != 2);
    }

    private static void applicationInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Application Management Interface");
        System.out.println("1. Apply for Project");
        System.out.println("2. Withdraw Application for Project");

        int appChoice = scanner.nextInt();
        switch (appChoice) {
            // apply
            case 1:

                break;

            // withdraw
            case 2:

                break;
        }

    }

    private static void enquiryInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter project to enquire: ");
        String projName = scanner.next();
        System.out.println("Enter enquiry: ");
        String enqString = scanner.next(); 

        current_user.createEnquiry(Project.getProjectByName(all_projects, projName), enqString);

    }
}
