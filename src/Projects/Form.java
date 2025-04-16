package Projects;

import Users.*;
import java.util.Date;

enum Status {
    NULL,
    PENDING,
    APPROVED,
    REJECTED,
    BOOKED
}

public abstract class Form{
    protected Integer id;
    protected Project project;
    protected User user;
    protected Date date;
    protected Status formStatus; // Pending, Approved, Rejected
    protected Status withdrawalStatus; // Null, Pending, Approved, Rejected

    public Form(Integer id, Project project, User user, Date date, String formStatus) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.date = date;
        this.formStatus = Status.valueOf(formStatus.toUpperCase()); 
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

    public String getFormStatus() {
        return formStatus.toString();
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = Status.valueOf(formStatus);
    }

    public void setWithdrawalStatus(String withdrawalStatus) {
        this.withdrawalStatus = Status.valueOf(withdrawalStatus);
    }

    public abstract String toString();
}
