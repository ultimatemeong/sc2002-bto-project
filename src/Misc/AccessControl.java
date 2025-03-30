package Misc;

public class AccessControl {

    public static void checkProjectAccess(Object user_class) {
        switch (user_class.getClass().getSimpleName()) {
            case "Manager":
                System.out.println("Access granted to Manager.");
                break;
            case "Officer":
                System.out.println("Access granted to Officer.");
                break;
            case "Applicant":
                System.out.println("Access granted to Applicant.");
                break;
            default:
                System.out.println("Access denied.");
        }
    }

    public static void checkEnquiryAccess(Object user_class) {
        switch (user_class.getClass().getSimpleName()) {
            case "Manager":
                System.out.println("Access granted to Manager.");
                break;
            case "Officer":
                System.out.println("Access granted to Officer.");
                break;
            case "Applicant":
                System.out.println("Access granted to Applicant.");
                break;
            default:
                System.out.println("Access denied.");
        }
    }
}
