package App;

import java.util.Scanner;

public class ManagerApp {
    public static void managerInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Manager Interface");
        System.out.println("1. Project Management");
        System.out.println("2. Account Management");
        System.out.println("3. Logout");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                projectInterface();
                break;
            case 2:
                accountInterface();
                break;
            case 3:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void projectInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Project Management Interface");
        System.out.println("1. View Projects");
        System.out.println("2. Add Project");
        System.out.println("3. Edit Project");
        System.out.println("4. Delete Project");
        System.out.println("5. Back to Main Menu");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
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
    }

    private static void accountInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Account Management Interface");
        System.out.println("1. Change Password");
        System.out.println("2. Back to Main Menu");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                break;

            case 2:
                System.out.println("Back to Main Menu...");
                managerInterface();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
}
