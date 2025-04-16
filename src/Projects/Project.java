package Projects;

import Enquiries.*;
import Misc.AccessControl;
import Misc.ProjectAccess;
import Users.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
    private String name;
    private String neighbourhood;
    private Map<String, List<Integer>> flatsInfo = new HashMap<>(); // key: flat type (e.g. 2-room, 3-room), value: [selling price, avail units]

    private LocalDate applicationOpenDate; // e.g. 2023-01-01  (Does not need Date class, as we dont need it to be too precise)
    private LocalDate applicationCloseDate; // e.g. 2023-01-31  
    private boolean visibility;

    private Manager manager;

    private Integer officerSlots = 10;
    private List<Officer> officerList = new ArrayList<>();
    
    private List<Application> applicationList = new ArrayList<>();
    private List<Registration> registrationList = new ArrayList<>();
    private List<Enquiry> enquiryList = new ArrayList<>();

    public Project(String name, String neighbourhood, String unitType1, Integer numUnitsType1, Integer priceType1, String unitType2, Integer numUnitsType2, Integer priceType2,
            LocalDate applicationOpenDate, LocalDate applicationCloseDate, boolean visibility, Manager manager,
            Integer officerSlots, List<Officer> officerList, List<Application> applicationList, List<Registration> registrationList, List<Enquiry> enquiryList) {
        this.name = name;
        this.neighbourhood = neighbourhood;

        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.visibility = visibility;
        this.manager = manager;
        this.officerSlots = officerSlots;
        this.officerList = officerList;

        List<Integer> type1Info = Arrays.asList(numUnitsType1, priceType1);
        List<Integer> type2Info = Arrays.asList(numUnitsType2, priceType2);

        Map<String, List<Integer>> flatsInfo = new HashMap<>();
        flatsInfo.put(unitType1, type1Info);
        flatsInfo.put(unitType2, type2Info);

        this.flatsInfo = flatsInfo;

        this.applicationList = applicationList;
        this.registrationList = registrationList;
        this.enquiryList = enquiryList;
    }

    public String getName() {
        return name;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public Map<String, List<Integer>> getFlatsInfo() {
        return flatsInfo;
    }

    public LocalDate getApplicationOpenDate() {
        return applicationOpenDate;
    }

    public LocalDate getApplicationCloseDate() {
        return applicationCloseDate;
    }
     
    public boolean isVisible() {
        return visibility;
    }

    public Manager getManager() {
        return manager;
    }

    public Integer getOfficerSlots() {
        return officerSlots;
    }

    public List<Officer> getOfficerList() {
        return officerList;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    public List<Enquiry> getEnquiryList() {
        return enquiryList;
    }

    public void setFlatsInfo(Map<String, List<Integer>> flatsInfo) {
        this.flatsInfo = flatsInfo;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setOfficerSlots(Integer officerSlots) {
        this.officerSlots = officerSlots;
    }

    public void addToOfficerList(Officer officer) {
        this.officerList.add(officer);
    }

    public void addToApplicationList(Application application) {
        this.applicationList.add(application);
    }

    public void addToRegistrationList(Registration registration) {
        this.registrationList.add(registration);
    }

    public void addToEnquiryList(Enquiry enquiry) {
        this.enquiryList.add(enquiry);
    }

    public boolean isVisibility() {
        return visibility;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(neighbourhood).append(",");
        for (Map.Entry<String, List<Integer>> entry : flatsInfo.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue().get(0)).append(":").append(entry.getValue().get(1)).append(",");
        }
        sb.append(applicationOpenDate).append(",").append(applicationCloseDate).append(",").append(manager.getName()).append(",");
        sb.append(officerSlots).append(",");
        for (Officer officer : officerList) {
            sb.append(officer.getName()).append(";");
        }
        return sb.toString();
    }

    public static List<Project> viewProjects(List<Project> all_projects, User user) {
        AccessControl<Project> accessControl = new ProjectAccess();

        List<Project> readableProjects = new ArrayList<>();
        for (Project project : all_projects) {
            String access = accessControl.check(project, user);
            if (access.contains("R")){
                readableProjects.add(project);
            }
        }
        return readableProjects;
    }

    // Takes in the list of readable prjects and filters them based on the flat type
    public static List<Project> filterProjects(List<Project> all_projects, String flatType) {
        List<Project> filteredProjects = all_projects.stream()
                .filter(project -> project.getFlatsInfo().containsKey(flatType) && project.getFlatsInfo().get(flatType).get(0) > 0)
                .toList();

        return filteredProjects;
    }
}   