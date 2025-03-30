package Users;

public class Manager extends User{

    public Manager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public String toString() {
        return "Manager: " + super.toString();
    }

    //Project specific methods
    public void viewAllProjects() {
        // Logic for viewing all projects
        // View all projects regardless of visibility
    }

    public void createProject() {
        // Logic for creating projects
    }

    public void editProject() {
        // Logic for editing projects
    }

    public void deleteProject() {
        // Logic for deleting projects
    }

    public void reviewRegistration() {
        // Logic for reviewing registrations
    }
    
    //Applicants specific methods
    public void viewAllApplicants() {
        // Logic for viewing all applicants
    }

    public void reviewApplication() {
        // Logic for approving/denying application
    }

    public void reviewWithdrawal() {
        // Logic for approving/denying withdrawal
    }

    public void generateReport() {
        // Logic for generating reports
    }

    //Enquiries specific methods
    public void viewAllEnquiries() {
        // Logic for viewing all enquiries
    }

    public void replyEnquiry() {
        // Logic for reply enquiry
    }

}
