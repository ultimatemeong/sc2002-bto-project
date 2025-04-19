package App;

import Projects.Application;
import Projects.Project;
import Users.Applicant;
import java.util.List;
import java.util.Scanner;

public class ApplicantApp extends MainApp {

    public static void applicantInterface() {
        List<Project> readableProjects = Project.viewProjects(all_projects, current_user).stream()
            .sorted().toList();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Applicant Interface");
            System.out.println("1. Project Management");
            System.out.println("2. Application Management");
            System.out.println("3. Account Management");
            System.out.println("4. Logout");

            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    projectInterface(readableProjects);
                    break;

                case 2:
                    applicationInterface(readableProjects, null);
                    break;

                case 3:
                    accountInterface();
                    break;

                case 4:
                    System.out.println("Logging out...");
                    break;
                
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 4);
    }

    private static void projectInterface(List<Project> readableProjects) {
        
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Project Management Interface");
            System.out.println("1. View Projects");
            System.out.println("2. Back to Main Menu");
            
            choice = scanner.nextInt();

            switch (choice) {
                // view projects
                case 1:
                    int projChoice;
                    int i;

                    do {
                        i = 1;
                        System.out.println("Select Any Project:");
                        for (Project project : readableProjects) {
                            System.out.println("\t" + i + ". " + project.getName() + ": " + project.getNeighbourhood());
                            i++;
                        }
                        System.out.println(i + ". Back to Project Management Interface.");

                        projChoice = scanner.nextInt();

                        // select project
                        if (projChoice < i) {
                            Project selectedProject = readableProjects.get(projChoice-1);
                            int projActionChoice;

                            do { 
                                System.out.println("Please select an option:");
                                System.out.println("1. Apply For This Project");
                                System.out.println("2. Enquire About This Project");
                                System.out.println("3. Select Another Project");
                                
                                projActionChoice = scanner.nextInt();
                                switch (projActionChoice) {
                                    // apply for project
                                    case 1:
                                        applicationInterface(readableProjects, selectedProject);
                                        break;

                                    // enquire about project
                                    case 2:
                                        enquiryInterface(readableProjects, selectedProject);
                                        break;

                                    // back to select project
                                    case 3:
                                        System.out.println("Back to Project Selection...");
                                        break;
                                }

                            } while (projActionChoice != 3);

                        } else if (projChoice == i) {
                            System.out.println("Back to Project Management Interface...");
                            break;
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                            break;
                        }

                        
                    } while (projChoice != i);  
                    break;

                case 2:
                    System.out.println("Back to Main Menu...");
                    break;
            }
        } while (choice != 2);

    }
        
    private static void applicationInterface(List<Project> readableProjects, Project selectedProject) {
        Scanner scanner = new Scanner(System.in);
        Integer appChoice;

        System.out.println("Application Management Interface");
        System.out.println("1. Apply for Project");
        System.out.println("2. Withdraw Application for Project");
        System.out.println("3. Exit Application Management Interface");
        appChoice = scanner.nextInt();

        switch (appChoice) {
            // apply
            case 1:
                if (selectedProject == null) {
                    System.out.println("Enter Project Name: ");
                    String projName = scanner.next();
                    selectedProject = Project.getProjectByName(readableProjects, projName);
                }

                System.out.println("Enter Flat Type to Apply");
                String flatType = scanner.next();

                ((Applicant) current_user).applyForProject(selectedProject, flatType);
                break;

            // withdraw
            case 2:
                Application currentApplication = ((Applicant) current_user).getApplication();
                if (currentApplication == null) {
                    System.out.println("You do not have any active applications.");
                    break;
                } else {
                    // has already applied but selected different project
                    if (!(selectedProject==null) && !(selectedProject.equals(((Applicant) current_user).getApplication().getProject()))) {
                        System.out.println("This is not the project you applied for.");

                    } else {
                        System.out.println("Your current application: ");
                        System.out.println("Project: " + currentApplication.getProject().getName() + ", Neighbourhood: " + currentApplication.getProject().getNeighbourhood());
                        System.out.print("Confirm Withdrawal? (Y/N): ");
                        String withdraw = scanner.next().toUpperCase();
    
                        switch (withdraw) {
                            case "Y": 
                                ((Applicant) current_user).withdrawApplication();
                                break;
    
                            case "N":
                                System.out.println("Withdrawal Cancelled.");
                                break;
    
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }

                }

            case 3:
                System.out.println("Exiting Application Management Interface...");
                break;
        }
        
    }

    private static void enquiryInterface(List<Project> readableProjects, Project selectedProject) {
        Scanner scanner = new Scanner(System.in);

        if (selectedProject == null) {
            System.out.println("Enter project to enquire: ");
            String projName = scanner.next();
            selectedProject = Project.getProjectByName(readableProjects, projName);
        }

        System.out.println("Enter enquiry: ");
        String enqString = scanner.next(); 

        ((Applicant) current_user).createEnquiry(selectedProject, enqString);
    }
}
