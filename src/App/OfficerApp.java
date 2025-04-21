package App;

import Enquiries.Enquiry;
import Enquiries.Reply;
import Projects.*;
import Users.Officer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OfficerApp extends ApplicantApp {
    
    public static void officerMainMenuInterface() throws Exception {
        System.out.println("Officer Main Menu Interface");
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Select Role:");
            System.out.println("1. Officer");
            System.out.println("2. Applicant");
            System.out.println("3. Logout");

            choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    officerInterface();
                    break;
                    
                case 2:
                    applicantInterface();
                    break;

                case 3:
                    System.out.println("Goodbye, " + current_user.getName() + "!");
                    MainApp.logout();
                    choice = 3; // Exit the loop
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
    }

    public static void officerInterface() throws Exception {
        List<Project> readableProjects = Project.viewProjects(all_projects, current_user).stream()
            .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).toList();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do { 
            System.out.println("Officer Interface");
            ((Officer) current_user).projectInCharge();
            System.out.println();
            System.out.println("1. Project Management");
            System.out.println("2. Enquiry Management");
            System.out.println("3. Registration Management");
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
                    enquiryInterface(null);
                    break;

                case 3:
                    registrationInterface(readableProjects, null);
                    break;

                case 4:
                    MainApp.accountInterface();
                    break;

                case 5:
                    System.out.println("Logging out...");
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
            System.out.println("2. Back to Main Menu");
            System.out.print("Please select an option:");

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
                                System.out.println("1. Register For This Project");
                                System.out.println("2. View and Reply to Enquiries About This Project");
                                System.out.println("3. Book Flat for Applicant");
                                System.out.println("4. Select Another Project");
                                System.out.print("Please select an option: ");

                                projActionChoice = scanner.nextInt();
                                System.out.println();

                                switch (projActionChoice) {
                                    // register for project
                                    case 1:
                                        registrationInterface(readableProjects, selectedProject);
                                        break;

                                    // view and reply enquiries 
                                    case 2:
                                        enquiryInterface(selectedProject);
                                        break;

                                    // book flat
                                    case 3:
                                        bookFlatInterface(selectedProject);

                                    // back to select project
                                    case 4:
                                        System.out.println("Back to Project Selection...\n");
                                        break;

                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }

                            } while (projActionChoice != 4);

                        } else if (projChoice == i) {
                            System.out.println("Back to Project Management Interface...\n");
                            break;
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                            break;
                        }

                        
                    } while (projChoice != i);  
                    break;

                // back to main menu
                case 2:
                    System.out.println("Back to Main Menu...\n");
                    break;
            }
        } while (choice != 2);
    }


    private static void registrationInterface(List<Project> readableProjects, Project selectedProject) {
        Scanner scanner = new Scanner(System.in);
        int regChoice;

        if (selectedProject == null) {
            System.out.println("Registration Management Interface");
            System.out.println("1. Register for Project");
            System.out.println("2. View My Registration");
            System.out.println("3. Exit Registration Management Interface");
            System.out.print("Please select an option: ");

            regChoice = scanner.nextInt();
            System.out.println();
        } else {
            regChoice = 1;
        }

        switch (regChoice) {
            // apply
            case 1:
                if (selectedProject == null) {
                    System.out.println("Enter Project Name: ");
                    String projName = scanner.next();
                    selectedProject = Project.getProjectByName(readableProjects, projName);
                }

                ((Officer) current_user).registerForProject(selectedProject);
                break;

            // view my registration
            case 2:
                ((Officer) current_user).viewRegistrationStatus();
                break;

            case 3:
                System.out.println("Exiting Registration Management Interface...\n");
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void enquiryInterface(Project selectedProject) {
        Project projectInCharge = ((Officer) current_user).projectInCharge();
        if (!(projectInCharge == null)) {
            if ((selectedProject == null) || (selectedProject.equals(projectInCharge))) {
                List<Enquiry> enqList = projectInCharge.getEnquiryList();
                Scanner scanner = new Scanner(System.in);
        
                System.out.println("Enquiry Management Interface");
                System.out.println("Managing Enquiries for: " + projectInCharge.getName());
                System.out.println("1. View Enquiries");
                System.out.println("2. Back to Main Menu");
                System.out.print("Please select an option: ");
        
                int choice = scanner.nextInt();
                System.out.println();
        
        
                switch (choice) {
                    // view enq
                    case 1:
                        int enqChoice;
                        int i;
        
                        do { 
                            i = 1;
                            System.out.println("Select An Enquiry:");
                            for (Enquiry enquiry : enqList) {
                                System.out.println(i + ". User: " + enquiry.getApplicant().getName());
                                System.out.println("\tEnquiry: " + enquiry.getEnquiryString());
                                i++;
                            }
                            System.out.println(i + ". Back to Enquiry Management Interface");
                            System.out.print("Please select an option: ");
                            enqChoice = scanner.nextInt();
                            System.out.println();
        
                            // select enq
                            if (enqChoice < i) {
                                Enquiry enquiry = enqList.get(enqChoice-1);
                                System.out.println("Enquiry: " + enquiry.getEnquiryString());
                                System.out.println("1. Reply to Enquiry");
                                System.out.println("2. Select Another Enquiry");
                                System.out.print("Please select an option: ");
        
                                int enqActionChoice = scanner.nextInt();
        
                                switch (enqActionChoice) {
                                    // reply enquiry
                                    case 1:
                                        System.out.println("Reply to Enquiry: ");
                                        scanner.nextLine(); // Catch the previous newline character
                                        String replyString = scanner.nextLine();
                                        Reply reply = new Reply(enquiry, current_user, LocalDateTime.now(), null);
                                        reply.writeReply(replyString);
                                        enquiry.setReply(reply);
                                        break;
                                        
                                    // back to select enquiry
                                    case 2:
                                        System.out.println("Back to Enquiry Selection...\n");
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                                break;
        
                            } else if (enqChoice == i) {
                                System.out.println("Back to Enquiry Management Interface...\n");
                                break;
                            } else {
                                System.out.println("Invalid choice. Please try again.");
                                break;
                            }
                        } while (enqChoice != i);
        
                    // back to main menu
                    case 2:
                        System.out.println("Back to Main Menu...\n");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("You are not in charge of this project.\n");
            }
        }
    }

    private static void bookFlatInterface(Project selectedProject) {
        Project projectInCharge = ((Officer) current_user).projectInCharge();
        if (selectedProject.equals(projectInCharge)) {
            Scanner scanner = new Scanner(System.in);
            int applicationChoice;
            int i;

            List<Application> applicationList = projectInCharge.getApplicationList();
            List<Application> approvedAppList = new ArrayList<>();

            for (Application application : applicationList) {
                if (application.getFormStatus().equals("APPROVED")) {
                    approvedAppList.add(application);
                }
            }

            do { 
                i = 1;
                if (approvedAppList.isEmpty()) {
                    System.out.println("\tNo Applications to view.");
                } else {
                    System.out.println("Select An Application:");
                    for (Application application : approvedAppList) {
                        System.out.println("\t" + i + ". Applicant: " + application.getUser().getName());
                        System.out.println("\tFlat Type: " + application.getFlatType());
                        i++;
                    }
                }
                System.out.println(i + ". Back to Project Actions");
                System.out.print("Please select an option: ");

                applicationChoice = scanner.nextInt();
                System.out.println();

                // select application
                if (applicationChoice < i) {
                    Application application = approvedAppList.get(applicationChoice-1);
                    System.out.println("Selected Application: ");
                    System.out.println("Applicant: " + application.getUser().getName());
                    System.out.println("Flat Type: " + application.getFlatType());
                    System.out.println("Confirm Flat Booking? (Y/N)");
                    String confirmBooking = scanner.next().toUpperCase();

                    switch (confirmBooking) {
                        case "Y":
                            ((Officer) current_user).chooseFlat(projectInCharge, application.getUser().getNric());
                            break;
                        case "N":
                            System.out.println("Booking cancelled.\n");
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }


                } else if (applicationChoice == i) {
                    System.out.println("Back to Project Actions...\n");
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    break;
                }

            } while (applicationChoice != i);

        } else {
            System.out.println("You are not in charge of this project.\n");
        }
    }
}