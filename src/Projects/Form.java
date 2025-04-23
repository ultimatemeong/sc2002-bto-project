package Projects;

import Users.*;

import java.time.LocalDate;

/**
 * Status is an enum that represents the status of a form.
 * It can be NULL, PENDING, APPROVED, REJECTED, or BOOKED.
 * @author Ang Qi Xuan Evan
 * @version 1.0
 * @since 2025-04-23
 */
enum Status {
    NULL,
    PENDING,
    APPROVED,
    REJECTED,
    BOOKED
}

/**
 * Form is an abstract class that represents a form in the system.
 * It contains common attributes and methods for all forms.
 * @author Ang Qi Xuan Evan
 * @version 1.0
 * @since 2025-04-23
 */
public abstract class Form{
    protected Integer id;
    protected Project project;
    protected User user;
    protected LocalDate date;
    protected Status formStatus; // Pending, Approved, Rejected

    /**
     * Constructor for Form class.
     * @param id The ID of the form.
     * @param project The project associated with the form.
     * @param user The user associated with the form.
     * @param date The date of the form.
     * @param formStatus The status of the form.
     */
    public Form(Integer id, Project project, User user, LocalDate date, String formStatus) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.date = date;
        this.formStatus = Status.valueOf(formStatus.toUpperCase()); 
    }

    /**
     * Get the ID of the form.
     * @return The ID of the form.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get the project associated with the form.
     * @return The project associated with the form.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Get the user associated with the form.
     * @return The user associated with the form.
     */
    public User getUser() {
        return user;
    }

    /**
     * Get the date of the form.
     * @return The date of the form.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the status of the form.
     * @return The status of the form.
     */
    public String getFormStatus() {
        return formStatus.toString();
    }

    /**
     * Set the status of the form.
     * @param formStatus The new status of the form.
     */
    public void setFormStatus(String formStatus) {
        this.formStatus = Status.valueOf(formStatus);
    }

    /**
     * Abstract method to be implemented by subclasses to return a string representation of the form.
     * @return A string representation of the form.
     */
    public abstract String toString();
}
