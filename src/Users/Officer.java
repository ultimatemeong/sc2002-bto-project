package Users;

public class Officer extends Applicant{

    public Officer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public String toString() {
        return "Officer: " + super.toString();
    }

    public void registerForProject() {
        // Logic for registering for projects
        
    }

    public void chooseFlat() {
        // Logic for choosing project
        
    }

    public void flatSelectionReceipt() {
        // Logic for flat selection receipt
        
    }

    public void viewAllProjects() {
        // Logic for viewing all projects (overridden from Applicant)
        // Regardless of visibility, but only for project he applied for
        
    }
}
