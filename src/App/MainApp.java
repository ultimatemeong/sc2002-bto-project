package App;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import Enquiries.*;
import Users.*;
import Projects.*;
import Misc.*;

enum ProjectFilter {
    NULL,
    MY_PROJECTS,
    NEIGHBOURHOOD,
    FLAT_TYPE,
    PRICE,    
}

enum ReportFilter {
    NULL,
    APPLICATION_ID,
    FLAT_TYPE,
    MARITAL_STATUS,
    PROJECT_NAME
}

public class MainApp {
    protected static List<Project> all_projects = new ArrayList<>();
    protected static List<Applicant> all_applicants = new ArrayList<>();
    protected static List<Officer> all_officers = new ArrayList<>();
    protected static List<Manager> all_managers = new ArrayList<>();
    protected static List<Enquiry> all_enquiries = new ArrayList<>();
    protected static User current_user;
    protected static ProjectFilter current_filter = ProjectFilter.NULL;
    private static boolean isEnd = false;

    public static void main(String[] args) throws Exception {
        init();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the HDB Application System!");
        String role = "";
        do {
            System.out.println("Please Login to continue.");
            System.out.print("NRIC: ");
            String nric = scanner.next();
            System.out.print("Password: ");
            String password = scanner.next();

            for (Manager manager : all_managers) {
                if (manager.getNric().equals(nric) && manager.validatePassword(password)) {
                    System.out.println("Login successful! Welcome, " + manager.getName() + ".");
                    current_user = (Manager) manager;
                    role = "Manager";
                }
            }

            if (role.equals("")) {
                for (Officer officer : all_officers) {
                    if (officer.getNric().equals(nric) && officer.validatePassword(password)) {
                        System.out.println("Login successful! Welcome, " + officer.getName() + ".");
                        current_user = (Officer) officer;
                        role = "Officer";
                    }
                }
            }

            if (role.equals("")) {
                for (Applicant applicant : all_applicants) {
                    if (applicant.getNric().equals(nric) && applicant.validatePassword(password)) {
                        System.out.println("Login successful! Welcome, " + applicant.getName() + ".");
                        current_user = (Applicant) applicant;
                        role = "Applicant";
                    }
                }
            }

            switch (role) {
                case "Manager":
                    ManagerApp.managerInterface();
                    break;

                case "Officer":
                    OfficerApp.officerMainMenuInterface();
                    break;

                case "Applicant":
                    ApplicantApp.applicantInterface();
                    break;    

                default:
                    System.out.println("Invalid login credentials. Please try again.\n");
                    break;
            }
        } while (role == "" && !isEnd);
    }

    public static void accountInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Account Management Interface");
        System.out.println("1. Change Password");
        System.out.println("2. Back to Main Menu");

        int choice;
        do {
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter new password: ");
                    String newpw = scanner.next();
                    current_user.setPassword(newpw);
                    System.out.println("Password has been changed.\n");
                    break;

                case 2:
                    System.out.println("Back to Main Menu...\n");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 2);
    }

    public static List<Project> filterProjects(List<Project> readableProjects) {
        Scanner scanner = new Scanner(System.in);

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
                    System.out.print("Enter Flat Type: (2-Room or 3-Room) ");
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
        if (filter_choice < 5 && filteredProjects.size() > 0) {
            readableProjects = filteredProjects.stream()
                .sorted(Comparator.comparing(Project::getName)).toList();
        }
        return readableProjects;
    }

    public static void logout() throws Exception {
        System.out.println("Logging out...\n");
        current_user = null;
        current_filter = ProjectFilter.NULL;
        isEnd = true;

        // Save all data to files
        String userHeader = "Name,NRIC,Age,Marital Status,Password";
        String projectHeader = "Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,Manager,Officer Slot,Officer";
        String applicationHeader = "ID,FlatType,NRIC,Date,Application Status,Withdrawal Status,Project";
        String registrationHeader = "ID,NRIC,Date,Registration Status,Project";
        String enquiryHeader = "ID,NRIC,Enquiry,DateTime,Project";
        String replyHeader = "Enquiry ID,User,DateTime,Reply";
        
        // Save Applicants
        List<List<String>> string_applicants = new ArrayList<>();
        
        string_applicants.add(List.of(userHeader.split(",")));
        for (Applicant applicant : all_applicants) {
            string_applicants.add(List.of(applicant.toString()));
        }
        FileOps.writeFile("ApplicantList", string_applicants);

        // Save Officers
        List<List<String>> string_officers = new ArrayList<>();

        string_officers.add(List.of(userHeader.split(",")));
        for (Officer officer : all_officers) {
            string_officers.add(List.of(officer.toString()));
        }
        FileOps.writeFile("OfficerList", string_officers);

        // Save Managers
        List<List<String>> string_managers = new ArrayList<>();

        string_managers.add(List.of(userHeader.split(",")));
        for (Manager manager : all_managers) {
            string_managers.add(List.of(manager.toString()));
        }
        FileOps.writeFile("ManagerList", string_managers);

        // Save Projects
        List<List<String>> string_projects = new ArrayList<>();

        string_projects.add(List.of(projectHeader.split(",")));
        for (Project project : all_projects) {
            string_projects.add(List.of(project.toString()));
        }
        FileOps.writeFile("ProjectList", string_projects);

        // Save Applications
        List<List<String>> string_applications = new ArrayList<>();

        string_applications.add(List.of(applicationHeader.split(",")));
        for (Project project : all_projects) {
            for (Application application : project.getApplicationList()) {
                string_applications.add(List.of(application.toString()));
            }
        }
        FileOps.writeFile("ApplicationList", string_applications);

        // Save registrations
        List<List<String>> string_registrations = new ArrayList<>();

        string_registrations.add(List.of(registrationHeader.split(",")));
        for (Project project : all_projects) {
            for (Registration registration : project.getRegistrationList()) {
                string_registrations.add(List.of(registration.toString()));
            }
        }
        FileOps.writeFile("RegistrationList", string_registrations);

        // Save Enquiries
        List<List<String>> string_enquiries = new ArrayList<>();

        string_enquiries.add(List.of(enquiryHeader.split(",")));
        for (Project project : all_projects) {
            for (Enquiry enquiry : project.getEnquiryList()) {
                string_enquiries.add(List.of(enquiry.toString()));
            }
        }
        FileOps.writeFile("EnquiryList", string_enquiries);

        // Save Replies
        List<List<String>> string_replies = new ArrayList<>();

        string_replies.add(List.of(replyHeader.split(",")));
        for (Enquiry enquiry : all_enquiries) {
            if (enquiry.getReply() != null) {
                string_replies.add(List.of(enquiry.getReply().toString()));
            }
        }
        FileOps.writeFile("ReplyList", string_replies);

        System.out.println("Logged out successfully.");
    }

    private static void init() throws Exception {
        // Load Applicants
        List<List<String>> applicantRecords = FileOps.readFile("ApplicantList");
        for (List<String> record : applicantRecords) {
            String name = record.get(0);
            String nric = record.get(1);
            Integer age = Integer.parseInt(record.get(2));
            String maritalStatus = record.get(3);
            String password = record.get(4);
            all_applicants.add(new Applicant(name, nric, age, maritalStatus, password, new ArrayList<>()));
        }

        // Load Officers
        List<List<String>> officerRecords = FileOps.readFile("OfficerList");
        for (List<String> record : officerRecords) {
            String name = record.get(0);
            String nric = record.get(1);
            Integer age = Integer.parseInt(record.get(2));
            String maritalStatus = record.get(3);
            String password = record.get(4);
            all_officers.add(new Officer(name, nric, age, maritalStatus, password, new ArrayList<>()));
        }
        
        // Load Managers
        List<List<String>> managerRecords = FileOps.readFile("ManagerList");
        for (List<String> record : managerRecords) {
            String name = record.get(0);
            String nric = record.get(1);
            Integer age = Integer.parseInt(record.get(2));
            String maritalStatus = record.get(3);
            String password = record.get(4);
            all_managers.add(new Manager(name, nric, age, maritalStatus, password));
        }

        // Load projects
        List<List<String>> projectRecords = FileOps.readFile("ProjectList");
        AccessControl<Project> projAccessControl = new ProjectAccess();
        for (List<String> record : projectRecords) {
            String name = record.get(0);
            String neighbourhood = record.get(1);
            String unitType1 = record.get(2);
            Integer numUnitsType1 = Integer.parseInt(record.get(3));
            Integer priceType1 = Integer.parseInt(record.get(4));
            String unitType2 = record.get(5);
            Integer numUnitsType2 = Integer.parseInt(record.get(6));
            Integer priceType2 = Integer.parseInt(record.get(7));
            LocalDate appOpenDate = LocalDate.parse(record.get(8));
            LocalDate appCloseDate = LocalDate.parse(record.get(9));
            String managerNric = record.get(10);
            Manager manager = Manager.getManagerByNric(all_managers, managerNric);
            boolean visibility = true;
            if (LocalDate.now().isAfter(appCloseDate)) {
                visibility = false;
            }

            Integer officerSlots = Integer.parseInt(record.get(11));

            // Load officers for the project
            String officers = record.get(12);
            List<Officer> officerList = new ArrayList<>();
            try (Scanner rowScanner = new Scanner(officers)) {
                rowScanner.useDelimiter(";");
                while (rowScanner.hasNext()) {
                    officerList.add(Officer.getOfficerByNric(all_officers,rowScanner.next()));
                }
            }

            Project project = new Project(name, neighbourhood, unitType1, numUnitsType1, priceType1, unitType2, numUnitsType2, priceType2, appOpenDate, appCloseDate, visibility, manager, officerSlots, officerList, new ArrayList<Application>(), new ArrayList<Registration>(), new ArrayList<Enquiry>());
            all_projects.add(project);
            projAccessControl.add(project, manager, "RW");
            for (Officer officer : officerList) {
                projAccessControl.add(project, officer, "R");
            }
        }

        // Load applications
        List<List<String>> applicationRecords = FileOps.readFile("ApplicationList");
        AccessControl<Application> applicationAccessControl = new ApplicationAccess(); 
        for (List<String> record : applicationRecords) {
            Integer applicationId = Integer.parseInt(record.get(0));
            String flatType = record.get(1);
            String applicantNric = record.get(2);
            LocalDate applicationDate = LocalDate.parse(record.get(3));
            String formStatus = record.get(4);
            String withdrawalStatus = record.get(5);
            String projectName = record.get(6);

            Project project = Project.getProjectByName(all_projects, projectName);
            Applicant applicant = Applicant.getApplicantByNric(all_applicants, applicantNric);

            Application application = new Application(applicationId, project, flatType, applicant, applicationDate, formStatus, withdrawalStatus);
            project.addToApplicationList(application);
            applicant.setApplication(application);
            applicationAccessControl.add(application, applicant, "RW");
            projAccessControl.add(project, applicant, "R");
        }

        // Load registrations
        List<List<String>> registrationRecords = FileOps.readFile("RegistrationList");
        for (List<String> record : registrationRecords) {
            Integer registrationId = Integer.parseInt(record.get(0));
            String officerNric = record.get(1);
            LocalDate registrationDate = LocalDate.parse(record.get(2));
            String status = record.get(3);
            String projectName = record.get(4);

            Project project = Project.getProjectByName(all_projects, projectName);
            Officer officer = Officer.getOfficerByNric(all_officers, officerNric);

            Registration registration = new Registration(registrationId, project, officer, registrationDate, status);
            project.addToRegistrationList(registration);
            officer.setRegistration(registration);
            projAccessControl.add(project, officer, "RW");
        }
        
        // Load enquiries
        List<List<String>> enquiryRecords = FileOps.readFile("EnquiryList");
        AccessControl<Enquiry> enquiryAccessControl = new EnquiryAccess();
        for (List<String> record : enquiryRecords) {
            Integer enquiryId = Integer.parseInt(record.get(0));
            String applicantNric = record.get(1);
            String enquiryString = record.get(2);
            LocalDateTime enquiryDate = LocalDateTime.parse(record.get(3));
            String projectName = record.get(4);
            Project project = Project.getProjectByName(all_projects, projectName);
            Applicant applicant = Applicant.getApplicantByNric(all_applicants, applicantNric);

            Enquiry enquiry = new Enquiry(enquiryId, applicant, enquiryString, enquiryDate, project);
            project.addToEnquiryList(enquiry);
            all_enquiries.add(enquiry);
            enquiryAccessControl.add(enquiry, applicant, "RW");
        }

        // Load replies
        List<List<String>> replyRecords = FileOps.readFile("ReplyList");
        for (List<String> record : replyRecords) {
            Integer enquiryId = Integer.parseInt(record.get(0));
            String userNric = record.get(1);
            LocalDateTime replyDate = LocalDateTime.parse(record.get(2));
            String replyString = record.get(3);

            User user = null;
            if (Manager.getManagerByNric(all_managers, userNric) != null) {
                user = Manager.getManagerByNric(all_managers, userNric);
            } else if (Officer.getOfficerByNric(all_officers, userNric) != null) {
                user = Officer.getOfficerByNric(all_officers, userNric);
            }
            Enquiry enquiry = Enquiry.getEnquiryById(all_enquiries, enquiryId);
            if (enquiry != null) {
                Reply reply = new Reply(enquiry, user, replyDate, replyString);
                enquiry.setReply(reply); // Set the reply in the enquiry
            }
        }
    }
}
