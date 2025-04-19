package App;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Enquiries.*;
import Users.*;
import Projects.*;
import Misc.*;

public class MainApp {
    protected static List<Project> all_projects = new ArrayList<>();
    protected static List<Applicant> all_applicants = new ArrayList<>();
    protected static List<Officer> all_officers = new ArrayList<>();
    protected static List<Manager> all_managers = new ArrayList<>();
    protected static List<Enquiry> all_enquiries = new ArrayList<>();
    protected static User current_user;

    public static void main(String[] args) throws Exception {
        init();

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the HDB Application System!");
        String role = "";
        do {
            System.out.println("Please Login to continue.");
            System.out.println("NRIC: ");
            String nric = scanner.next();
            System.out.println("Password: ");
            String password = scanner.next();

            for (Manager manager : all_managers) {
                if (manager.getNric().equals(nric) && manager.validatePassword(password)) {
                    System.out.println("Login successful! Welcome, " + manager.getName() + ".");
                    current_user = manager;
                    role = "Manager";
                }
            }

            if (role.equals("")) {
                for (Officer officer : all_officers) {
                    if (officer.getNric().equals(nric) && officer.validatePassword(password)) {
                        System.out.println("Login successful! Welcome, " + officer.getName() + ".");
                        current_user = officer;
                        role = "Officer";
                    }
                }
            }

            if (role.equals("")) {
                for (Applicant applicant : all_applicants) {
                    if (applicant.getNric().equals(nric) && applicant.validatePassword(password)) {
                        System.out.println("Login successful! Welcome, " + applicant.getName() + ".");
                        current_user = applicant;
                        role = "Applicant";
                    }
                }
            }

            switch (role) {
                case "Manager":
                    ManagerApp.managerInterface();
                    break;

                case "Officer":
                    OfficerApp.officerInterface();
                    break;

                case "Applicant":
                    ApplicantApp.applicantInterface();
                    break;    

                default:
                    System.out.println("Invalid login credentials. Please try again.");
                    break;
            }
        } while (role == "");
    }

    public static void accountInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Account Management Interface");
        System.out.println("1. Change Password");
        System.out.println("2. Back to Main Menu");

        int choice;
        do {
            System.out.println("Please select an option:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    break;

                case 2:
                    System.out.println("Back to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 2);
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
            LocalDateTime replyDate = LocalDateTime.parse(record.get(1));
            String replyString = record.get(2);

            Enquiry enquiry = Enquiry.getEnquiryById(all_enquiries, enquiryId);
            if (enquiry != null) {
                Reply reply = new Reply(enquiry, replyDate, replyString);
                enquiry.setReply(reply); // Set the reply in the enquiry
            }
        }
    }
}
