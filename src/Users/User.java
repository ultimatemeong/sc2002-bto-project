package Users;

enum MaritalStatus{
    SINGLE,
    MARRIED
}

public class User {
    private String name;
    private String nric;
    private int age;
    private MaritalStatus maritalStatus;
    private String password;

    protected User(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = MaritalStatus.valueOf(maritalStatus.toUpperCase());
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }   
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = MaritalStatus.valueOf(maritalStatus.toUpperCase());;
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
        return maritalStatus.toString();
    }
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
    
    public String toString() {
        return name + "," + nric + "," + age + "," + maritalStatus + "," + password;
    }
}
