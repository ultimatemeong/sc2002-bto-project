package Users;

public class Applicant extends User{

    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public String toString() {
        return "Applicant: " + super.toString();
    }

    //Project specific methods
    protected void viewAllProjects() {
        // Logic for viewing all projects
        // If single only 2-room flat, if married both 2-room & 3-room flat
    }

    protected void applyForProject() {
        // Logic for applying for flats
    }

    //Application specific methods
    public void viewApplicationStatus() {
        // Logic for viewing application status
    }

    public void withdrawApplication() {
        // Logic for withdrawing application
    }

    //Enquiries specific methods
    protected void viewEnquiries() {
        // Logic for viewing enquiries
    }

    protected void makeEnquiry() {
        // Logic for making enquiry
    }

    protected void editEnquiry() {
        // Logic for editing enquiry
    }

    protected void deleteEnquiry() {
        // Logic for deleting enquiry
    }
}
