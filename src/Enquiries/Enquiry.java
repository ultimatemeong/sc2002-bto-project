package Enquiries;

import Users.Applicant;

public class Enquiry {
    private Integer id;
    private Applicant applicant;
    private String enquiryString;
    private Reply reply;

    public Enquiry(Integer id, Applicant applicant, String enquiryString) {
        this.id = id;
        this.applicant = applicant;
        this.enquiryString = enquiryString;
        this.reply = null; // Initially, there is no reply
    }

    public Integer getId() {
        return id;
    }
}
