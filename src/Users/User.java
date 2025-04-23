package Users;

/**
 * MaritalStatus enum represents the marital status of a user.
 * It can be either SINGLE or MARRIED.
 */
enum MaritalStatus{
    SINGLE,
    MARRIED
}

/**
 * User class represents a user in the system.
 * It includes attributes such as name, NRIC, age, marital status, and password.
 * @author Ang QiLe Dora
 * @version 1.0
 * @since 2025-04-23
 */
public class User {
    private String name;
    private String nric;
    private int age;
    private MaritalStatus maritalStatus;
    private String password;

    /**
     * Constructor for User class
     * @param name
     * @param nric
     * @param age
     * @param maritalStatus
     * @param password
     */
    protected User(String name, String nric, Integer age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = MaritalStatus.valueOf(maritalStatus.toUpperCase());
        this.password = password;
    }

    /**
     * Set Age for User class
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }   

    /**
     * Set Marital Status for User class
     * @param maritalStatus
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = MaritalStatus.valueOf(maritalStatus.toUpperCase());;
    }

    /**
     * Set Password for User class
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the name of the user
     * @return name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Get the NRIC of the user
     * @return nric of the user
     */
    public String getNric() {
        return nric;
    }

    /**
     * Get the age of the user
     * @return age of the user
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Get the marital status of the user
     * @return marital status of the user
     */
    public String getMaritalStatus() {
        return maritalStatus.toString();
    }

    /**
     * Checks if the user's password is valid
     * @return validate the password
     */
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Converts the user object to a string representation
     * @return string representation of the user object
     */
    public String toString() {
        return name + "," + nric + "," + age + "," + maritalStatus + "," + password;
    }
}
