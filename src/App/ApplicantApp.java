package App;

import Projects.Application;
import Projects.Project;
import Users.Applicant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ApplicantApp extends MainApp {

    public static void applicantInterface() throws Exception {
        List<Project> readableProjects = Project.viewProjects(all_projects, current_user).stream()
            .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).toList();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Applicant Interface");
            System.out.println("1. Project Management");
            System.out.println("2. Application Management");
            System.out.println("3. Account Management");
            System.out.println("4. Logout");
            System.out.print("Please select an option: ");

            choice = scanner.nextInt();
            System.out.println();

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
                    System.out.println("Goodbye, " + current_user.getName() + "!");
                    MainApp.logout();
                    choice = 4; // Exit the loop
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
            System.out.println("2. Filter Projects");
            System.out.println("3. Back to Main Menu");
            System.out.print("Please select an option: ");

            choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                // view projects
                case 1:
                    int projChoice;
                    int i;

                    do {
                        i = 1;
                        if (readableProjects.isEmpty()) {
                            System.out.println("\tNo Projects to view.");
                        } else {
                            System.out.println("Select A Project:");
                            for (Project project : readableProjects) {
                                System.out.println("\t" + i + ". " + project.getName() + ": " + project.getNeighbourhood());
                                i++;
                            }
                        }
                        System.out.println(i + ". Back to Project Management Interface.");
                        System.out.print("Please select an option: ");

                        projChoice = scanner.nextInt();
                        System.out.println();

                        // select project
                        if (projChoice < i) {
                            Project selectedProject = readableProjects.get(projChoice-1);
                            int projActionChoice;

                            do { 
                                System.out.println("Project Actions");
                                System.out.println("1. Apply For This Project");
                                System.out.println("2. Enquire About This Project");
                                System.out.println("3. Select Another Project");
                                
                                System.out.print("Please select an option: ");

                                projActionChoice = scanner.nextInt();
                                System.out.println();

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
                                        System.out.println("Back to Project Selection...\n");
                                        break;

                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }

                            } while (projActionChoice != 3);

                        } else if (projChoice == i) {
                            System.out.println("Back to Project Management Interface...\n");
                            break;
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                            break;
                        }

                        
                    } while (projChoice != i);  
                    break;

                case 2:
                    System.out.println("1. Filter by Flat Type");
                    System.out.println("2. Filter by Neighbourhood");
                    System.out.println("3. Filter by Price Range");
                    System.out.println("4. View My Projects");
                    System.out.println("5. Back to Project Management Interface");
                    List<Project> filteredProjects = new ArrayList<>();
                    int filter_choice;
                    do {
                        System.out.print("Please select an option:");
                        filter_choice = scanner.nextInt();
                        switch (filter_choice) {
                            case 1:
                                System.out.print("Enter Flat Type: (2-room or 3-room) ");
                                String flatType = scanner.next();
                                current_filter = ProjectFilter.FLAT_TYPE;
                                filteredProjects = Project.filterProjectsByFlatType(readableProjects, flatType);
                                if (filteredProjects.isEmpty()) {
                                    System.out.println("No Projects to View.");
                                } else {
                                    for (Project project : filteredProjects) {
                                        System.out.println(project.getName()+ ", " + project.getNeighbourhood());
                                    }
                                }
                                break;
                            case 2:
                                System.out.print("Enter Neighbourhood: ");
                                String neighbourhood = scanner.next();
                                current_filter = ProjectFilter.NEIGHBOURHOOD;
                                filteredProjects = Project.filterProjectsByNeighbourhood(readableProjects, neighbourhood);
                                if (filteredProjects.isEmpty()) {
                                    System.out.println("No Projects to View.");
                                } else {
                                    for (Project project : filteredProjects) {
                                        System.out.println(project.getName()+ ", " + project.getNeighbourhood());
                                    }
                                }
                                break;
                            case 3:
                                System.out.print("Enter Price Range: (min) ");
                                Integer minPrice = scanner.nextInt();
                                System.out.print("Enter Price Range: (max) ");
                                Integer maxPrice = scanner.nextInt();
                                current_filter = ProjectFilter.PRICE;
                                
                                filteredProjects = Project.filterProjectsByPrice(readableProjects, minPrice, maxPrice);
                                if (filteredProjects.isEmpty()) {
                                    System.out.println("No Projects to View.");
                                } else {
                                    for (Project project : filteredProjects) {
                                        System.out.println(project.getName()+ ", " + project.getNeighbourhood());
                                    }
                                }
                                break;
                            case 4:
                                System.out.println("Viewing My Projects...");
                                filteredProjects = readableProjects.stream()
                                    .filter(project -> project.getManager().getName().equals(current_user.getName()))
                                    .toList();
                                if (filteredProjects.isEmpty()) {
                                    System.out.println("No Projects to View.");
                                } else {
                                    for (Project project : filteredProjects) {
                                        System.out.println(project.getName()+ ", " + project.getNeighbourhood());
                                    }
                                }
                                break;
                            case 5:
                                System.out.println("Back to Project Management Interface...");
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        } 
                    } while (filter_choice > 5);
                    if (filter_choice < 5 && readableProjects.size() > 0) {
                        readableProjects = filteredProjects.stream()
                            .sorted(Comparator.comparing(Project::getName)).toList();
                    }
                    break;

                case 3:
                    System.out.println("Back to Main Menu...\n");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);

    }
        
    private static void applicationInterface(List<Project> readableProjects, Project selectedProject) {
        Scanner scanner = new Scanner(System.in);
        int appChoice;

        if (selectedProject == null) {
            System.out.println("Application Management Interface");
            System.out.println("1. Apply for Project");
            System.out.println("2. View My Application");
            System.out.println("3. Withdraw Application for Project");
            System.out.println("4. Exit Application Management Interface");
            System.out.print("Please select an option: ");
    
            appChoice = scanner.nextInt();
            System.out.println();
        } else {
            appChoice = 1;
        }

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

            // view my application
            case 2:
                ((Applicant) current_user).viewApplicationStatus();
                break;

            // withdraw
            case 3:
                Application currentApplication = ((Applicant) current_user).getApplication();
                if (currentApplication == null) {
                    System.out.println("You do not have any active applications.\n");
                    break;
                } else {
                    // has already applied but selected different project
                    if (!(selectedProject==null) && !(selectedProject.equals(((Applicant) current_user).getApplication().getProject()))) {
                        System.out.println("This is not the project you applied for.\n");

                    } else {
                        System.out.println("Your current application: ");
                        ((Applicant) current_user).viewApplicationStatus();
                        System.out.print("Confirm Withdrawal? (Y/N): ");
                        String withdraw = scanner.next().toUpperCase();
    
                        switch (withdraw) {
                            case "Y": 
                                ((Applicant) current_user).withdrawApplication();
                                break;
    
                            case "N":
                                System.out.println("Withdrawal Cancelled.\n");
                                break;
    
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                }

            case 4:
                System.out.println("Exiting Application Management Interface...\n");
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
        
    }

    private static void enquiryInterface(List<Project> readableProjects, Project selectedProject) {
        Scanner scanner = new Scanner(System.in);

        if (selectedProject == null) {
            System.out.print("Enter project to enquire: ");
            String projName = scanner.next();
            selectedProject = Project.getProjectByName(readableProjects, projName);
        }

        System.out.print("Enter enquiry: ");
        String enqString = scanner.next(); 

        ((Applicant) current_user).createEnquiry(selectedProject, enqString);
    }
}
