package Users;

import java.util.List;

import Enquiries.Enquiry;
import Projects.*;

public class Officer extends Applicant{
    private Registration registration;

    public Officer(String name, String nric, int age, String maritalStatus, String password, Application application, List<Enquiry> enquiryList, Registration registration) {
        super(name, nric, age, maritalStatus, password, application, enquiryList);
        this.registration = registration;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
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

    
}
