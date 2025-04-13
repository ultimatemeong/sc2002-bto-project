package Users;

public class User {
    private String name;
    private String nric;
    private int age;
    private String maritalStatus;
    private String password;

    protected User(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    protected void setAge(int age) {
        this.age = age;
    }
    protected void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public String getNric() {
        return nric;
    }
    public int getAge() {
        return age;
    }
    public String getMaritalStatus() {
        return maritalStatus;
    }
    public String getPassword() {
        return password;
    }

    public String toString() {
        return "Name: " + name + "\nNRIC: " + nric + "\nAge: " + age + "\nMarital Status: " + maritalStatus;
    }
    
    public String toCSV() {
        return name + "," + nric + "," + age + "," + maritalStatus + "," + password;
    }
}
