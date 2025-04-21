package App;

import Enquiries.*;
import Misc.*;
import Projects.*;
import Users.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ManagerApp extends MainApp {
    
    public static void managerInterface() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Manager Interface");
            System.out.println("1. Project Management");
            System.out.println("2. Enquiry Management");
            System.out.println("3. Account Management");
            System.out.println("4. Logout");
            System.out.print("Please select an option:");

            choice = scanner.nextInt();
            System.out.println();
            switch (choice) {
                case 1:
                    projectInterface();
                    break;
                case 2:
                    projectEnquiryInterface();
                    break;
                case 3:
                    MainApp.accountInterface();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    System.out.println("Goodbye, " + current_user.getName() + "!");
                    logout();
                    choice = 3;
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
            System.out.print("Please select an option:");
            choice = scanner.nextInt();
            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("1. View All Projects");
                    System.out.println("2. Filter Projects");
                    System.out.println("Please select an option:");
                    int viewby = scanner.nextInt();
                    switch (viewby) {
                        case 1:
                            for (Project project : readableProjects) {
                                System.out.println(project.getName()+ ", " + project.getNeighbourhood());
                            }
                            current_filter = ProjectFilter.NULL;
                            break;
                        case 2:
                            filterProjects(readableProjects);
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
                        System.out.println("6. Generate Report");
                        System.out.println("7. Back to Project Management Interface");
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
                                projectRegistrationInterface(editableProjects);
                                break;
                            case 5:
                                projectEnquiryInterface();
                                break;
                            case 6:
                                generateReport();
                            case 7:
                                System.out.println("Back to Project Management Interface...");
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    } while (work_choice != 7);
                    break;
                case 4:
                    AccessControl<Project> delaccessControl = new ProjectAccess();
                    List<Project> deletableProjects = readableProjects.stream()
                        .filter(project -> delaccessControl.check(project, current_user).contains("RW"))
                        .toList();

                    int projCount = 1;
                    System.out.println("Delete Project:");
                    for (Project project : deletableProjects) {
                        System.out.println(projCount + ": " + project.getName()+ ", " + project.getNeighbourhood());
                    }
                    System.out.println("Choose a project to delete:");
                    int proj_choice = scanner.nextInt();
                    if (proj_choice < (projCount)) {
                        Project proj = deletableProjects.get(proj_choice-1);
                        deleteProject(proj);
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
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
                                    choice = 2;
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
                                                    break;
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
                                            proj_choice = i;
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
        List<Application> withdrawals = new ArrayList<>();
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
                                    if (app.getWithdrawalStatus().equals("PENDING")) {
                                        System.out.println(String.valueOf(withdrawalCount) + ". Name: " + app.getUser().getName() + ", Withdrawal Status: " + app.getWithdrawalStatus());
                                        withdrawals.add(app);
                                        withdrawalCount++;
                                    }
                                }
                            }
                            System.out.println("Would you like to work on an Withdrawal? (Y/N): ");
                            String work = scanner.next().toUpperCase();
                            switch (work) {
                                case "Y":
                                    System.out.println("Choose an Withdrawal to work on:");
                                    int appChoice;
                                    do {
                                        appChoice = scanner.nextInt();
                                        if (appChoice < (withdrawalCount)) {
                                            Application app = withdrawals.get(appChoice-1);
                                            workOnWithdrawal(app);
                                            appChoice = withdrawalCount;
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
                                    choice = 2;
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
                                    withdrawalCount = 1;
                                    for (Application app : proj.getApplicationList()) {
                                        if (app.getWithdrawalStatus().equals("PENDING")) {
                                            System.out.println(String.valueOf(withdrawalCount) + ". Name: " + app.getUser().getName() + ", Withdrawal Status: " + app.getWithdrawalStatus());
                                            withdrawals.add(app);
                                            withdrawalCount++;
                                        }
                                    }
                                    
                                    System.out.println("Would you like to work on an Withdrawal? (Y/N): ");
                                    String workByProj = scanner.next().toUpperCase();
                                    switch (workByProj) {
                                        case "Y":
                                            System.out.println("Choose an Withdrawal to work on:");
                                            int appChoice;
                                            do {
                                                appChoice = scanner.nextInt();
                                                if (appChoice < (withdrawalCount)) {
                                                    Application app = withdrawals.get(appChoice-1);
                                                    workOnWithdrawal(app);
                                                    proj_choice = i;
                                                    break;
                                                } else if (appChoice == (withdrawalCount)) {
                                                    proj_choice = i;
                                                    break;
                                                } else {
                                                    System.out.println("Invalid choice. Please try again.");
                                                }
                                            } while (appChoice != (withdrawalCount));
                                            break;
                                        case "N":
                                            proj_choice = i;
                                            break;
                                        default:
                                            proj_choice = i;
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

    public static void projectRegistrationInterface(List<Project> editableProjects) {
        Scanner scanner = new Scanner(System.in);
        List<Registration> registrations = new ArrayList<>();
        int choice;
        int regCount;
        do {
            System.out.println("Project Registration Interface");
            System.out.println("1. View Registrations");
            System.out.println("2. Back to Project Work Interface");
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Viewing Registrations...");
                    System.out.println("1. View All Registrations");
                    System.out.println("2. View Registrations For A Specific Project");
                    int viewby = scanner.nextInt();
                    switch (viewby) {
                        case 1:
                            regCount = 1;
                            for (Project project : editableProjects) {
                                System.out.println(project.getName()+ ", " + project.getNeighbourhood() + ": ");
                                System.out.println("Pending Registrations: ");
                                for (Registration reg : project.getRegistrationList()) {
                                    if (reg.getFormStatus().equals("PENDING")) {
                                        System.out.println(String.valueOf(regCount) + ". Name: " + reg.getUser().getName() + ", Registration Status: " + reg.getFormStatus());
                                        registrations.add(reg);
                                        regCount++;
                                    }
                                }
                            }
                            System.out.println("Would you like to work on an Registration? (Y/N): ");
                            String work = scanner.next().toUpperCase();
                            switch (work) {
                                case "Y":
                                    System.out.println("Choose an Registration to work on:");
                                    int regChoice;
                                    do {
                                        regChoice = scanner.nextInt();
                                        if (regChoice < (regCount)) {
                                            Registration reg = registrations.get(regChoice-1);
                                            workOnRegistration(reg);
                                            regChoice = regCount;
                                        } else if (regChoice == (regCount)) {
                                            System.out.println("Exiting to Project Work Interface...");
                                            break;
                                        } else {
                                            System.out.println("Invalid choice. Please try again.");
                                        }
                                    } while (regChoice != (regCount));
                                    break;
                                case "N":
                                    choice = 2;
                                    break;
                                default:
                                    choice = 2;
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
                                    regCount = 1;
                                    for (Registration reg : proj.getRegistrationList()) {
                                        if (reg.getFormStatus().equals("PENDING")) {
                                            System.out.println(String.valueOf(regCount) + ". Name: " + reg.getUser().getName() + ", Registration Status: " + reg.getFormStatus());
                                            registrations.add(reg);
                                            regCount++;
                                        }
                                    }
                                    
                                    System.out.println("Would you like to work on an Registrations? (Y/N): ");
                                    String workByProj = scanner.next().toUpperCase();
                                    switch (workByProj) {
                                        case "Y":
                                            System.out.println("Choose an Registrations to work on:");
                                            int regChoice;
                                            do {
                                                regChoice = scanner.nextInt();
                                                if (regChoice < (regCount)) {
                                                    Registration reg = registrations.get(regChoice-1);
                                                    workOnRegistration(reg);
                                                    proj_choice = i;
                                                    break;
                                                } else if (regChoice == (regCount)) {
                                                    proj_choice = i;
                                                    break;
                                                } else {
                                                    System.out.println("Invalid choice. Please try again.");
                                                }
                                            } while (regChoice != (regCount));
                                            break;
                                        case "N":
                                            proj_choice = i;
                                            break;
                                        default:
                                            proj_choice = i;
                                            break;
                                    }

                                } else {
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
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

    private static void workOnRegistration(Registration reg) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Working on Registration: " + reg.getUser().getName());
            System.out.println("1. Approve Registration");
            System.out.println("2. Reject Registration");
            System.out.println("3. Back to Project Work Interface");
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Registration Approved");
                    reg.setFormStatus("APPROVED");
                    break;
                case 2:
                    System.out.println("Registration Rejected");
                    reg.setFormStatus("REJECTED");
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

    public static void projectEnquiryInterface() {
        Scanner scanner = new Scanner(System.in);
        List<Enquiry> enquiries = new ArrayList<>();
        int choice;
        int enqCount;
        do {
            System.out.println("Project Enquiry Interface");
            System.out.println("1. View Enquiries");
            System.out.println("2. Back to Project Work Interface");
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Viewing Enquiries...");
                    System.out.println("1. View All Enquiries");
                    System.out.println("2. View Enquiries For A Specific Project");
                    int viewby = scanner.nextInt();
                    switch (viewby) {
                        case 1:
                            enqCount = 1;
                            for (Project project : all_projects) {
                                System.out.println(project.getName()+ ", " + project.getNeighbourhood() + ": ");
                                System.out.println("Enquries: ");
                                for (Enquiry enq : project.getEnquiryList()) {
                                    if (enq.getReply()== null) {
                                        System.out.println(String.valueOf(enqCount) + ". Name: " + enq.getApplicant().getName());
                                        System.out.println("\tEnquiry: " + enq.getEnquiryString());
                                        enquiries.add(enq);
                                        enqCount++;
                                    }
                                }
                            }
                            System.out.println("Would you like to reply to an Enquiry? (Y/N): ");
                            String work = scanner.next().toUpperCase();
                            switch (work) {
                                case "Y":
                                    System.out.println("Choose an Enquiry to reply:");
                                    int enqChoice;
                                    do {
                                        enqChoice = scanner.nextInt();
                                        if (enqChoice < (enqCount)) {
                                            Enquiry enq = enquiries.get(enqChoice-1);
                                            replyToEnquiry(enq);
                                            enqChoice = enqCount;
                                        } else if (enqChoice == (enqCount)) {
                                            System.out.println("Exiting to Project Work Interface...");
                                            break;
                                        } else {
                                            System.out.println("Invalid choice. Please try again.");
                                        }
                                    } while (enqChoice != (enqCount));
                                    break;
                                case "N":
                                    choice = 2;
                                    break;
                                default:
                                    choice = 2;
                                    break;
                                
                            }
                            break;
                        case 2:
                            int i = 1;
                            for (Project project : all_projects) {
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
                                    Project proj = all_projects.get(proj_choice-1);
                                    enqCount = 1;
                                    for (Enquiry enq : proj.getEnquiryList()) {
                                        System.out.println(String.valueOf(enqCount) + ". Name: " + enq.getApplicant().getName());
                                        System.out.println("Enquiry: " + enq.getEnquiryString());
                                        enquiries.add(enq);
                                        enqCount++;
                                    }
                                    
                                    System.out.println("Would you like to reply to an Enquiry? (Y/N): ");
                                    String workByProj = scanner.next().toUpperCase();
                                    switch (workByProj) {
                                        case "Y":
                                            System.out.println("Choose an Enquiry to work on:");
                                            int enqChoice;
                                            do {
                                                enqChoice = scanner.nextInt();
                                                if (enqChoice < (enqCount)) {
                                                    Enquiry enq = enquiries.get(enqChoice-1);
                                                    replyToEnquiry(enq);
                                                    proj_choice = i;
                                                    break;
                                                } else if (enqChoice == (enqCount)) {
                                                    proj_choice = i;
                                                    break;
                                                } else {
                                                    System.out.println("Invalid choice. Please try again.");
                                                }
                                            } while (enqChoice != (enqCount));
                                            break;
                                        case "N":
                                            proj_choice = i;
                                            break;
                                        default:
                                            proj_choice = i;
                                            break;
                                    }

                                } else {
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
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

    private static void replyToEnquiry(Enquiry enq) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Working on Enquiry: " + enq.getApplicant().getName() + ", Enquiry: " + enq.getEnquiryString());
            System.out.println("1. Reply to Enquiry");
            System.out.println("2. Back to Project Work Interface");
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Reply to Enquiry: ");
                    String replyString = scanner.nextLine();
                    Reply reply = new Reply(enq, current_user, LocalDateTime.now(), null);
                    reply.writeReply(replyString);
                    enq.setReply(reply);
                    choice = 2;
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

    private static void generateReport() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Project Report Interface");
            System.out.println("1. Generate Report");
            System.out.println("2. Back to Project Work Interface");
            System.out.println("Please select an option:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Filter Report By:");
                    for (ReportFilter filter : ReportFilter.values()) {
                        System.out.println(filter.ordinal() + 1 + ". " + filter.name());
                    }
                    System.out.println("Please select a filter:");
                    int filterChoice = scanner.nextInt();
                    do {
                        switch (filterChoice) {
                            case 1:
                                System.out.println("No Filter Applied: ");
                                all_projects = all_projects.stream()
                                        .sorted(Comparator.comparing(Project::getName))
                                        .toList();
                                        
                                ((Manager) current_user).generateReport(all_projects);
                                break;
                            case 2:
                                System.out.println("Filter by Application ID: ");
                                List<Application> allApplications = ((Manager) current_user).viewAllApplications(all_projects);
                                allApplications = allApplications.stream()
                                        .sorted(Comparator.comparing(Application::getId))
                                        .toList();
                                
                                for (Application app : allApplications) {
                                    ((Manager) current_user).generateReport(app);
                                }
                                break;

                            case 3:
                                System.out.println("Filter by Flat Type");
                                System.out.println("Please select a flat type (2-Room/3-Room): ");
                                String flatType = scanner.next();
                                List<Application> allApplications2 = ((Manager) current_user).viewAllApplications(all_projects);
                                if (!flatType.equals("2-Room") && !flatType.equals("3-Room")) {
                                    System.out.println("Invalid flat type. Please try again.");
                                    break;
                                }
                                allApplications2 = allApplications2.stream()
                                        .filter(app -> app.getFlatType().equals(flatType))
                                        .sorted(Comparator.comparing(Application::getId))
                                        .toList();

                                for (Application app : allApplications2) {
                                    ((Manager) current_user).generateReport(app);
                                }
                                break;

                            case 4:
                                System.out.println("Filter by Marital Status: ");
                                System.out.println("Please select a marital status (Single/Married): ");
                                String maritalStatus = scanner.next().toUpperCase();
                                List<Application> allApplications3 = ((Manager) current_user).viewAllApplications(all_projects);
                                if (!maritalStatus.equals("SINGLE") && !maritalStatus.equals("MARRIED")) {
                                    System.out.println("Invalid marital status. Please try again.");
                                    break;
                                }
                                allApplications3 = allApplications3.stream()
                                        .filter(app -> app.getUser().getMaritalStatus().equals(maritalStatus))
                                        .sorted(Comparator.comparing(Application::getId))
                                        .toList();
                                
                                System.out.println(allApplications3);
                                for (Application app : allApplications3) {
                                    ((Manager) current_user).generateReport(app);
                                }
                                break;

                            case 5:
                                System.out.println("Filter by Project Name: ");
                                System.out.println("Please select a project name: ");
                                scanner.nextLine(); // Consume the newline character
                                String projectName = scanner.nextLine();
                                List<Application> allApplications4 = ((Manager) current_user).viewAllApplications(all_projects);
                                allApplications4 = allApplications4.stream()
                                        .filter(app -> app.getProject().getName().equals(projectName))
                                        .sorted(Comparator.comparing(Application::getId))
                                        .toList();

                                for (Application app : allApplications4) {
                                    ((Manager) current_user).generateReport(app);
                                }
                                break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    } while (filterChoice < 1 || filterChoice > ReportFilter.values().length);
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

    private static void deleteProject(Project project) {
        Scanner scanner = new Scanner(System.in);
        String confirm;
        do {
            System.out.println("Are you sure you want to delete the project " + project.getName() + "? (Y/N): ");
            confirm = scanner.next().toUpperCase();
            switch (confirm) {
                case "Y":
                    all_projects.remove(project);
                    System.out.println("Project deleted successfully.");
                    break;
                case "N":
                    System.out.println("Project deletion cancelled.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (!confirm.equals("Y") && !confirm.equals("N"));
    }
}