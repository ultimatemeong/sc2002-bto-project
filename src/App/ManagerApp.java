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
        int choice;
        do {
            Scanner scanner = new Scanner(System.in);
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
                    System.out.println("1. View All Projects");
                    System.out.println("2. View Your Projects");
                    int viewby = scanner.nextInt();
                    switch (viewby) {
                        case 1:
                            for (Project project : readableProjects) {
                                System.out.println(project.getName());
                            }
                            break;
                        case 2:
                            for (Project project : readableProjects) {
                                if(project.getManager().getName().equals(current_user.getName())) {
                                    System.out.println(project.toString());
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
                    projectEditInterface(readableProjects);
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

    private static void projectEditInterface(List<Project> readableProjects) {
        Scanner scanner = new Scanner(System.in);
        AccessControl<Project> accessControl = new ProjectAccess();
        List<Project> editableProjects = readableProjects.stream()
            .filter(project -> accessControl.check(project, current_user).contains("RW"))
            .toList();

        if (editableProjects.size() > 0) {
            int i = 1;
            System.out.println("Editable Projects:");
            for(Project proj : editableProjects) {
                System.out.println(String.valueOf(i) + ". " + proj.getName());
            }
            System.out.println(String.valueOf(i+1) + " Exit to Project Management Interface");
            System.out.print("Choose the project to edit:");
            Integer proj_choice = scanner.nextInt();
            if (proj_choice < (i+1)) {
                Project proj = editableProjects.get(proj_choice-1);

                boolean proj_visibility = proj.isVisible();
                Integer roomType1Num = proj.getFlatsInfo().get("2-room").get(0);
                Integer roomType1price = proj.getFlatsInfo().get("2-room").get(1);
                Integer roomType2Num = proj.getFlatsInfo().get("3-room").get(0);
                Integer roomType2price = proj.getFlatsInfo().get("3-room").get(1);
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
                            } while(!toggle.equals("Y") || !toggle.equals("N"));
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

                Project tempProject = new Project(proj.getName(), proj.getNeighbourhood(), "2-room", 
                roomType1Num, roomType1price, "3-room", roomType2Num, roomType2price, 
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
}
