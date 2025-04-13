package Projects;

import java.util.Date;

import Users.*;

public class Form {
    protected Integer id;
    protected Project project;
    protected User user;
    protected Date date;
    protected String formStatus; // e.g. "Pending", "Approved", "Rejected"
    private String withdrawalStatus; // Null, Pending, Approved, Rejected

    public Form(Integer id, Project project, User user, Date date, String formStatus, String withdrawalStatus) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.date = date;
        this.formStatus = formStatus;
        this.withdrawalStatus = withdrawalStatus; // Pending, Approved, Rejected
    }

    public Integer getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return formStatus;
    }

    public String getWithdrawalStatus() {
        return withdrawalStatus;
    }

    public void setWithdrawalStatus(String withdrawalStatus) {
        this.withdrawalStatus = withdrawalStatus;
    }
}
