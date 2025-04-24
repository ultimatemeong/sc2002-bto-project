package App;

import Enquiries.Enquiry;
import Projects.Application;
import Projects.Project;
import Users.*;
import java.util.List;
import java.util.Scanner;

public class ApplicantApp extends MainApp {

    public static void applicantInterface() throws Exception {
        List<Project> readableProjects = Project.viewProjects(all_projects, current_user).stream()
            .filter(project -> !project.getOfficerList().contains(current_user) && !project.getRegistrationList().stream().anyMatch(reg -> reg.getUser().equals(current_user)))
            .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
            .toList();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Applicant Interface");
            System.out.println("1. Project Management");
            System.out.println("2. Application Management");
            System.out.println("3. Enquiry Management");
            System.out.println("4. Account Management");
            System.out.println("5. Logout");
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
                    enquiryInterface(readableProjects, null);
                    break;

                case 4:
                    accountInterface();
                    break;

                case 5:
                    System.out.println("Goodbye, " + current_user.getName() + "!");
                    MainApp.logout();
                    break;
                
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);
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
                        System.out.println(i + ". Back to Project Management Interface");
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
                    filterProjects(readableProjects);
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
                scanner.nextLine();
                do { 
                    System.out.println("Enter Project To Apply For: ");
                    String projName = scanner.nextLine();
                    selectedProject = Project.getProjectByName(readableProjects, projName);

                    if (selectedProject == null) {
                        System.out.println("This project does not exist.\n");
                    }
                    
                } while (selectedProject == null);

                String flatType;
                do {
                    System.out.println("Enter Flat Type to Apply");
                    flatType = scanner.next().toUpperCase();
                } while (flatType != "2-ROOM" && flatType != "3-ROOM");

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
        int choice;

        if (selectedProject == null) {
            System.out.println("Enquiry Management Interface");
            System.out.println("1. Enquire About A Project");
            System.out.println("2. View My Enquiries");
            System.out.println("3. Exit Enquiry Management Interface");
            System.out.print("Please select an option: ");
    
            choice = scanner.nextInt();
            System.out.println();
        } else {
            choice = 1;
        }

        switch (choice) {
            // enquire
            case 1:
                scanner.nextLine();
                do { 
                    System.out.println("Enter Project To Enquire About: ");
                    String projName = scanner.nextLine();
                    selectedProject = Project.getProjectByName(readableProjects, projName);

                    if (selectedProject == null) {
                        System.out.println("This project does not exist.\n");
                    }
                    
                } while (selectedProject == null);

                System.out.println("Enter Enquiry");
                String enquiryString = scanner.nextLine();

                ((Applicant) current_user).createEnquiry(selectedProject, enquiryString);
                break;

            // view my enquiries
            case 2:
                List<Enquiry> enqList = ((Applicant) current_user).getEnquiryList();

                int enqChoice;
                int i;

                do {
                    i = 1;
                    if (enqList.isEmpty()) {
                        System.out.println("\tNo Enquiries to view.");
                    } else {
                        System.out.println("Select An Enquiry:");
                        for (Enquiry enquiry : enqList) {
                            System.out.println("\t" + i + ". " + enquiry.getProject().getName() + ": " + enquiry.getEnquiryString());
                            i++;
                        }
                    }
                    System.out.println(i + ". Back to Enquiry Management Interface");
                    System.out.print("Please select an option: ");

                    enqChoice = scanner.nextInt();
                    System.out.println();

                    // select enquiry
                    if (enqChoice < i) {
                        int selectedEnquiryID = enqList.get(enqChoice-1).getId();
                        int enqActionChoice;

                        do { 
                            System.out.println("Enquiry Actions");
                            System.out.println("1. Edit Enquiry");
                            System.out.println("2. Delete Enquiry");
                            System.out.println("3. Select Another Enquiry");
                            System.out.print("Please select an option: ");

                            enqActionChoice = scanner.nextInt();
                            System.out.println();

                            switch (enqActionChoice) {
                                // edit
                                case 1:
                                    System.out.println("Selected Enquiry: " + Enquiry.getEnquiryById(enqList, selectedEnquiryID).getEnquiryString());
                                    System.out.println("Enter updated enquiry string:");
                                    //////////////////////////////////////////////////////////
                                    String newEnquiryString = scanner.nextLine();
                                    //////////////////////////////////////////////////////////
                                    System.out.print("Confirm edit? (Y/N): ");
                                    String confirmEdit = scanner.next().toUpperCase();

                                    switch (confirmEdit) {
                                        case "Y":
                                            ((Applicant) current_user).editEnquiry(selectedEnquiryID, newEnquiryString);
                                            break;

                                        case "N":
                                            System.out.println("Edit Cancelled.\n");
                                            break;

                                        default:
                                            System.out.println("Invalid choice. Please try again.");
                                            break;
                                    }
                                    break;

                                // delete
                                case 2:
                                    System.out.println("Selected Enquiry: " + Enquiry.getEnquiryById(enqList, selectedEnquiryID).getEnquiryString());
                                    System.out.print("Confirm Deletion? (Y/N): ");
                                    String confirmDelete = scanner.next().toUpperCase();

                                    switch (confirmDelete) {
                                        case "Y":
                                            ((Applicant) current_user).deleteEnquiry(selectedEnquiryID);
                                            break;

                                        case "N":
                                            System.out.println("Deletion Cancelled.\n");
                                            break;

                                        default:
                                            System.out.println("Invalid choice. Please try again.");
                                            break;
                                    }
                                    break;

                                // back to select enquiry
                                case 3:
                                    System.out.println("Back to Enquiry Selection...\n");
                                    break;

                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
                            }

                        } while (enqActionChoice != 3);

                    } else if (enqChoice == i) {
                        System.out.println("Back to Enquiry Management Interface...\n");
                        break;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                        break;
                    }
                } while (enqChoice != i);
                break;

            // exit
            case 3:
                System.out.println("Exiting Enquiry Management Interface...\n");
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
}
