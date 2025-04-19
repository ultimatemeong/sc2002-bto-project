package App;

import java.time.LocalDate;
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
        int choice;
        do {
            System.out.println("Manager Interface");
            System.out.println("1. Project Management");
            System.out.println("2. Account Management");
            System.out.println("3. Logout");

            choice = scanner.nextInt();
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
        } while (choice != 3);
    }

    private static void projectInterface() {
        List<Project> readableProjects = Project.viewProjects(all_projects, current_user).stream()
            .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).toList();
        Scanner scanner = new Scanner(System.in);
        

        int choice;
        do {
            System.out.println("Project Management Interface");
            System.out.println("1. View Projects");
            System.out.println("2. Add Project");
            System.out.println("3. Work On Project");
            System.out.println("4. Delete Project");
            System.out.println("5. Back to Main Menu");
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("1. View All Projects");
                    System.out.println("2. View Your Projects");
                    int viewby = scanner.nextInt();
                    switch (viewby) {
                        case 1:
                            for (Project project : readableProjects) {
                                System.out.println(project.getName()+ ", " + project.getNeighbourhood());
                            }
                            break;
                        case 2:
                            for (Project project : readableProjects) {
                                if(project.getManager().getName().equals(current_user.getName())) {
                                    System.out.println(project.getName()+ ", " + project.getNeighbourhood());
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Adding Project:");
                    System.out.println("Project Name:");
                    scanner.nextLine();
                    String proj_name = scanner.nextLine();
                    System.out.println("Project Neighbourhood:");
                    String neighbourhood = scanner.nextLine();
                    System.out.println("Project Unit Type 1: (2-room)");
                    String unitType1 = scanner.next();
                    System.out.println("Project Number of Units for Type 1:");
                    Integer numUnitsType1 = scanner.nextInt();
                    System.out.println("Project Unit Price for Type 1:");
                    Integer priceType1 = scanner.nextInt();
                    System.out.println("Project Unit Type 2: (3-room)");
                    String unitType2 = scanner.next();
                    System.out.println("Project Number of Units for Type 2:");
                    Integer numUnitsType2 = scanner.nextInt();
                    System.out.println("Project Unit Price for Type 2:");
                    Integer priceType2 = scanner.nextInt();
                    System.out.println("Project Opening Date:");
                    LocalDate appOpenDate = LocalDate.parse(scanner.next());
                    System.out.println("Project Closing Date:");
                    LocalDate appCloseDate = LocalDate.parse(scanner.next());
                    System.out.println("Project Officer Slots:");
                    Integer officerSlots = scanner.nextInt();
                    
                    ((Manager) current_user).createProject(all_projects, proj_name, neighbourhood, unitType1, numUnitsType1, priceType1, unitType2, 
                    numUnitsType2, priceType2, appOpenDate, appCloseDate, true, officerSlots, new ArrayList<Officer>());
                    
                    break;
                    
                case 3:
                    AccessControl<Project> accessControl = new ProjectAccess();
                    List<Project> editableProjects = readableProjects.stream()
                        .filter(project -> accessControl.check(project, current_user).contains("RW"))
                        .toList();
        
                    int work_choice;

                    do {
                        System.out.println("Project Work Interface:");
                        System.out.println("1. Edit Project Details");
                        System.out.println("2. Applications");
                        System.out.println("3. Withdrawals");
                        System.out.println("4. Registrations");
                        System.out.println("5. Enquiries");
                        System.out.println("6. Back to Project Management Interface");
                        System.out.println("Please select an option:");
                        work_choice = scanner.nextInt();
                        switch (work_choice) {
                            case 1:
                                projectDetailsEditInterface(editableProjects);
                                break;
                            case 2:
                                // System.out.println("Viewing Applications...");
                                projectApplicationInterface(editableProjects);
                                break;
                            case 3:
                                // System.out.println("Viewing Withdrawals...");
                                projectWithdrawalInterface(editableProjects);
                                break;
                            case 4:
                                System.out.println("Viewing Registrations...");
                                break;
                            case 5:
                                System.out.println("Viewing Enquiries...");
                                break;
                            case 6:
                                System.out.println("Back to Project Management Interface...");
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    } while (work_choice != 6);
                    break;
                case 4:
                    
                    break;
                case 5:
                    System.out.println("Back to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);
    }

    private static void projectDetailsEditInterface(List<Project> editableProjects) {
        Scanner scanner = new Scanner(System.in);
        if (editableProjects.size() > 0) {
            int i = 1;
            System.out.println("Editable Projects:");
            for(Project proj : editableProjects) {
                System.out.println(String.valueOf(i) + ". " + proj.getName());
                i++;
            }
            System.out.println(String.valueOf(i) + ". Exit to Project Management Interface");
            System.out.print("Choose the project to edit:");
            Integer proj_choice = scanner.nextInt();
            if (proj_choice < (i)) {
                Project proj = editableProjects.get(proj_choice-1);

                boolean proj_visibility = proj.isVisible();
                Integer roomType1Num = proj.getFlatsInfo().get("2-Room").get(0);
                Integer roomType1price = proj.getFlatsInfo().get("2-Room").get(1);
                Integer roomType2Num = proj.getFlatsInfo().get("3-Room").get(0);
                Integer roomType2price = proj.getFlatsInfo().get("3-Room").get(1);
                Integer proj_officerSlots = proj.getOfficerSlots();
                int edit_choice;
                do {
                    System.out.println("1. Edit Project's Visibility");
                    System.out.println("2. Edit Project's 2-room Information");
                    System.out.println("3. Edit Project's 3-room Information");
                    System.out.println("4. Edit Project's Number of Officer Slots");
                    System.out.println("5. Exit");
                    System.out.print("Choose an option: ");

                    edit_choice = scanner.nextInt();

                    switch (edit_choice) {
                        case 1:
                            if (proj_visibility){
                                System.out.println("Project is currently: Visible");
                            } else {
                                System.out.println("Project is currently: Not Visible");
                            }
                            String toggle;
                            do {
                                System.out.print("Do you wish to toggle the Visibility? (Y/N): ");
                                toggle = scanner.next().toUpperCase();
                                switch (toggle) {
                                    case "Y":
                                        proj_visibility = !proj_visibility;
                                        break;

                                    case "N":
                                        System.out.println("No change was made");
                                        break;
                                    default:
                                        break;
                                }
                            } while ( !toggle.equals("Y") && !toggle.equals("N"));
                            
                            break;
                            
                        case 2:
                            System.out.println("Project's Current 2-room Info:");
                            System.out.println("Number of Flats: " + roomType1Num);
                            System.out.println("Price of Flats: "+ roomType1price);

                            System.out.print("New Number of Flats: ");
                            roomType1Num = scanner.nextInt();
                            System.out.print("New Price of Flats: ");
                            roomType1price = scanner.nextInt();

                            break;

                        case 3:
                            System.out.println("Project's Current 3-room Info:");
                            System.out.println("Number of Flats: " + roomType2Num);
                            System.out.println("Price of Flats: "+ roomType2price);

                            System.out.print("New Number of Flats: ");
                            roomType2Num = scanner.nextInt();
                            System.out.print("New Price of Flats: ");
                            roomType2price = scanner.nextInt();

                            break;

                        case 4:
                            System.out.println("Project's Current number of Officer Slots: " + proj_officerSlots);

                            System.out.print("New number of Officer Slots: ");
                            proj_officerSlots = scanner.nextInt();

                            break;
                        
                        case 5:
                            System.out.println("Going Back...");
                            break;
                        default:
                            break;
                    }
                } while (edit_choice != 5);

                Project tempProject = new Project(proj.getName(), proj.getNeighbourhood(), "2-Room", 
                roomType1Num, roomType1price, "3-Room", roomType2Num, roomType2price, 
                proj.getApplicationOpenDate(), proj.getApplicationCloseDate(), proj_visibility, proj.getManager(), 
                proj_officerSlots, proj.getOfficerList(), proj.getApplicationList(), 
                proj.getRegistrationList(), proj.getEnquiryList());

                ((Manager) current_user).editProject(proj, tempProject);
            } else {
                System.out.println("Back to Project Management Interface...");
                projectInterface();
            }
        } else {
            System.out.println("You have no editable projects.");
            System.out.println("Back to Project Management Interface...");
            projectInterface();
        }
    }

    private static void projectApplicationInterface(List<Project> editableProjects) {

        if (editableProjects.size() < 1) {
            System.out.println("You have no projects to work on.");
            System.out.println("Back to Project Management Interface...");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int appCount;
        int choice;
        do {
            System.out.println("Project Application Interface");
            System.out.println("1. View Applications");
            System.out.println("2. Back to Project Work Interface");
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Viewing Applications...");
                    System.out.println("1. View All Applications");
                    System.out.println("2. View Applications For A Specific Project");
                    int viewby = scanner.nextInt();
                    switch (viewby) {
                        case 1:
                            appCount = 1;
                            for (Project project : editableProjects) {
                                System.out.println(project.getName()+ ", " + project.getNeighbourhood() + ": ");
                                for (Application app : project.getApplicationList()) {
                                    System.out.println(String.valueOf(appCount) + ". Name: " + app.getUser().getName() + ", Room Type: " + app.getFlatType());
                                    appCount++;
                                }
                            }

                            System.out.println("Would you like to work on an application? (Y/N): ");
                            String work = scanner.next().toUpperCase();
                            switch (work) {
                                case "Y":
                                    System.out.println("Choose an application to work on:");
                                    int appChoice;
                                    do {
                                        appChoice = scanner.nextInt();
                                        if (appChoice < (appCount)) {
                                            Application app = null;
                                            for (Project project : editableProjects) {
                                                if (appChoice <= project.getApplicationList().size()) {
                                                    app = project.getApplicationList().get(appChoice-1);
                                                    break;
                                                } else {
                                                    appChoice -= project.getApplicationList().size();
                                                }
                                            }
                                            workOnApplication(app);
                                            appChoice = appCount;
                                        } else if (appChoice == (appCount)) {
                                            appChoice = appCount;
                                            break;
                                        } else {
                                            System.out.println("Invalid choice. Please try again.");
                                        }
                                    } while (appChoice != (appCount));
                                    break;
                                case "N":
                                    choice = 2;
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 2:
                            int i = 1;
                            for (Project project : editableProjects) {
                                if(project.getManager().getName().equals(current_user.getName())) {
                                    System.out.println(String.valueOf(i) + ". " + project.getName() + ", " + project.getNeighbourhood());
                                    i++;
                                }
                            }
                            System.out.println(String.valueOf(i) + ". Exit to Project Work Interface");
                            System.out.print("Choose a project:");
                            int proj_choice;
                            do {
                                proj_choice = scanner.nextInt();
                                if (proj_choice < (i)) {
                                    Project proj = editableProjects.get(proj_choice-1);
                                    appCount = 1;
                                    for (Application app : proj.getApplicationList()) {
                                        System.out.println(String.valueOf(appCount) + ". Name: " + app.getUser().getName() + ", Room Type: " + app.getFlatType());
                                        appCount++;
                                    }
                                    System.out.println("Would you like to work on an application? (Y/N): ");
                                    String workByProj = scanner.next().toUpperCase();
                                    switch (workByProj) {
                                        case "Y":
                                            System.out.println("Choose an application to work on:");
                                            int appChoice;
                                            do {
                                                appChoice = scanner.nextInt();
                                                if (appChoice < (appCount)) {
                                                    Application app = proj.getApplicationList().get(appChoice-1);
                                                    workOnApplication(app);
                                                    proj_choice = i;
                                                } else if (appChoice == (appCount)) {
                                                    proj_choice = i;
                                                    break;
                                                } else {
                                                    System.out.println("Invalid choice. Please try again.");
                                                }
                                            } while (appChoice != (appCount));
                                            break;
                                        case "N":
                                            proj_choice = i;
                                            break;
                                        default:
                                            break;
                                    }

                                } else if (proj_choice == (i)) {
                                    choice = 2;
                                    break;
                                } else {
                                    System.out.println("Invalid choice. Please try again.");
                                }

                            } while (proj_choice != (i));
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Back to Project Work Interface...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 2);
    }

    private static void workOnApplication(Application app) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Working on Application: " + app.getUser().getName() + ", Room Type: " + app.getFlatType());
            System.out.println("1. Approve Application");
            System.out.println("2. Reject Application");
            System.out.println("3. Back to Project Work Interface");
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Application Approved");
                    app.setFormStatus("APPROVED");
                    break;
                case 2:
                    System.out.println("Application Rejected");
                    app.setFormStatus("REJECTED");
                    break;
                case 3:
                    System.out.println("Back to Project Work Interface...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
    }

    private static void projectWithdrawalInterface(List<Project> editableProjects) {
        Scanner scanner = new Scanner(System.in);
        
        int choice;
        int withdrawalCount;
        do {
            System.out.println("Project Withdrawal Interface");
            System.out.println("1. View Withdrawals");
            System.out.println("2. Back to Project Work Interface");
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Viewing Withdrawals...");
                    System.out.println("1. View All Withdrawals");
                    System.out.println("2. View Withdrawals For A Specific Project");
                    int viewby = scanner.nextInt();
                    switch (viewby) {
                        case 1:
                            withdrawalCount = 1;
                            for (Project project : editableProjects) {
                                System.out.println(project.getName()+ ", " + project.getNeighbourhood() + ": ");
                                System.out.println("Pending Withdrawals: ");
                                for (Application app : project.getApplicationList()) {
                                    System.out.println("Name: " + app.getUser().getName() + ", Withdrawal Status: " + app.getWithdrawalStatus());
                                    withdrawalCount++;
                                }

                                
                                System.out.println(String.valueOf(withdrawalCount) + ". Exit to Project Work Interface");
                                System.out.println("Would you like to work on an Withdrawal? (Y/N): ");
                                String work = scanner.next().toUpperCase();
                                switch (work) {
                                    case "Y":
                                        System.out.println("Choose an Withdrawal to work on:");
                                        int appChoice;
                                        do {
                                            appChoice = scanner.nextInt();
                                            if (appChoice < (withdrawalCount)) {
                                                Application app = null;
                                                for (Project proj : editableProjects) {
                                                    if (appChoice <= project.getApplicationList().size()) {
                                                        app = project.getApplicationList().get(appChoice-1);
                                                        break;
                                                    } else {
                                                        appChoice -= project.getApplicationList().size();
                                                    }
                                                }
                                                workOnWithdrawal(app);
                                            } else if (appChoice == (withdrawalCount)) {
                                                System.out.println("Exiting to Project Work Interface...");
                                                break;
                                            } else {
                                                System.out.println("Invalid choice. Please try again.");
                                            }
                                        } while (appChoice != (withdrawalCount));
                                        break;
                                    case "N":
                                        choice = 2;
                                        break;
                                    default:
                                        break;
                                }
                            }
                            break;
                        case 2:
                            int i = 1;
                            for (Project project : editableProjects) {
                                if(project.getManager().getName().equals(current_user.getName())) {
                                    System.out.println(String.valueOf(i) + ". " + project.getName() + ", " + project.getNeighbourhood());
                                    i++;
                                }
                            }
                            System.out.println(String.valueOf(i) + ". Exit to Project Work Interface");
                            System.out.print("Choose a project:");
                            int proj_choice;
                            do {
                                proj_choice = scanner.nextInt();
                                if (proj_choice < (i)) {
                                    Project proj = editableProjects.get(proj_choice-1);
                                    withdrawalCount = 1;
                                    for (Application app : proj.getApplicationList()) {
                                        System.out.println("Name: " + app.getUser().getName() + ", Withdrawal Status: " + app.getWithdrawalStatus());
                                        withdrawalCount++;
                                    }
                                    System.out.println(String.valueOf(withdrawalCount) + ". Exit to Project Work Interface");
                                    System.out.println("Would you like to work on an Withdrawal? (Y/N): ");
                                    String workByProj = scanner.next().toUpperCase();
                                    switch (workByProj) {
                                        case "Y":
                                            System.out.println("Choose an Withdrawal to work on:");
                                            int appChoice;
                                            do {
                                                appChoice = scanner.nextInt();
                                                if (appChoice < (withdrawalCount)) {
                                                    Application app = proj.getApplicationList().get(appChoice-1);
                                                    workOnWithdrawal(app);
                                                } else if (appChoice == (withdrawalCount)) {
                                                    proj_choice = i;
                                                    break;
                                                } else {
                                                    System.out.println("Invalid choice. Please try again.");
                                                }
                                            } while (appChoice != (withdrawalCount));
                                            break;
                                        case "N":
                                            choice = 2;
                                            break;
                                        default:
                                            break;
                                    }

                                } else if (proj_choice == (i)) {
                                    choice = 2;
                                    break;
                                } else {
                                    System.out.println("Invalid choice. Please try again.");
                                }

                            } while (proj_choice != (i));
                            break;
                        default:
                            break;
                    }

                    break;
                case 2:
                    System.out.println("Back to Project Management Interface...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 2);
    }

    private static void workOnWithdrawal(Application app) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Working on Withdrawal: " + app.getUser().getName() + ", Room Type: " + app.getFlatType());
            System.out.println("1. Approve Withdrawal");
            System.out.println("2. Reject Withdrawal");
            System.out.println("3. Back to Project Work Interface");
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Withdrawal Approved");
                    app.setWithdrawalStatus("APPROVED");
                    break;
                case 2:
                    System.out.println("Withdrawal Rejected");
                    app.setWithdrawalStatus("REJECTED");
                    break;
                case 3:
                    System.out.println("Back to Project Work Interface...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
    }
}
