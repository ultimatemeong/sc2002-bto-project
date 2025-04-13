package Projects;

import Misc.AccessControl;
import Enquiries.*;
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

        HashMap<String, List<Integer>> flatsInfo = new HashMap<String, List<Integer>>();
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

    public void setFlatsInfo(Map<String, List<Integer>> flatsInfo) {
        this.flatsInfo = flatsInfo;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setOfficerSlots(Integer officerSlots) {
        this.officerSlots = officerSlots;
    }

    public void setOfficerList(List<Officer> officerList) {
        this.officerList = officerList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    public void setRegistrationList(List<Registration> registrationList) {
        this.registrationList = registrationList;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public List<Project> viewProjects(List<Project> all_projects, User user) {
        AccessControl<Project> accessControl = new AccessControl<>();

        List<Project> readableProjects = new ArrayList<>();
        for (Project project : all_projects) {
            String access = accessControl.checkAccess(project, user);
            if (access.contains("R")){
                readableProjects.add(project);
            }
        }
        return readableProjects;
    }
    

}   