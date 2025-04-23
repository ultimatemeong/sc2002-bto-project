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

/**
 * The Project class represents a housing project with various attributes and methods.
 * It includes information about the project's name, neighbourhood, flats, application dates, visibility, and associated officers.
 * The class also provides methods for filtering projects based on different criteria.
 * @author Ang QiLe Dora
 * @version 1.0
 * @since 2025-04-23
 */
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

    /**
     * Constructor for the Project class.
     * @param name The name of the project.
     * @param neighbourhood The neighbourhood where the project is located.
     * @param unitType1 The type of the first unit (e.g., 2-room, 3-room).
     * @param numUnitsType1 The number of units of the first type.
     * @param priceType1 The selling price of the first type of unit.
     * @param unitType2 The type of the second unit (e.g., 4-room, 5-room).
     * @param numUnitsType2 The number of units of the second type.
     * @param priceType2 The selling price of the second type of unit.
     * @param applicationOpenDate The date when applications open.
     * @param applicationCloseDate The date when applications close.
     * @param visibility Indicates whether the project is visible to users.
     * @param manager The manager responsible for the project.
     * @param officerSlots The number of officer slots available for the project.
     * @param officerList List of officers associated with the project.
     * @param applicationList List of applications for the project.
     * @param registrationList List of registrations for the project.
     * @param enquiryList List of enquiries related to the project.
     */
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

    /**
     * Gets the name of the project.
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the neighbourhood of the project.
     * @return The neighbourhood of the project.
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Gets the flats information of the project.
     * @return A map containing the flat types and their corresponding information (number of units and selling price).
     */
    public Map<String, List<Integer>> getFlatsInfo() {
        return flatsInfo;
    }

    /**
     * Gets the application open date of the project.
     * @return The date when applications open.
     */
    public LocalDate getApplicationOpenDate() {
        return applicationOpenDate;
    }

    /**
     * Gets the application close date of the project.
     * @return The date when applications close.
     */
    public LocalDate getApplicationCloseDate() {
        return applicationCloseDate;
    }
     
    /**
     * Gets the visibility status of the project.
     * @return true if the project is visible, false otherwise.
     */
    public boolean isVisible() {
        return visibility;
    }

    /**
     * Gets the manager responsible for the project.
     * @return The manager of the project.
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Gets the number of officer slots available for the project.
     * @return The number of officer slots.
     */
    public Integer getOfficerSlots() {
        return officerSlots;
    }

    /**
     * Gets the list of officers associated with the project.
     * @return A list of officers.
     */
    public List<Officer> getOfficerList() {
        return officerList;
    }

    /**
     * Gets the list of applications for the project.
     * @return A list of applications.
     */
    public List<Application> getApplicationList() {
        return applicationList;
    }

    /**
     * Gets the list of registrations for the project.
     * @return A list of registrations.
     */
    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    /**
     * Gets the list of enquiries related to the project.
     * @return A list of enquiries.
     */
    public List<Enquiry> getEnquiryList() {
        return enquiryList;
    }

    /**
     * Gets the project by its name from a list of projects.
     * @param projectList The list of projects to search in.
     * @param name The name of the project to find.
     * @return The project with the specified name, or null if not found.
     */
    public static Project getProjectByName(List<Project> projectList, String name) {
        for (Project project : projectList) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        return null; 
    }

    /**
     * Sets the flat information for the project.
     * @param flatsInfo
     */
    public void setFlatsInfo(Map<String, List<Integer>> flatsInfo) {
        this.flatsInfo = flatsInfo;
    }

    /**
     * Change the visibility of the project.
     * @param visibility
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Change the number of officer slots available for the project.
     * @param officerSlots
     */
    public void setOfficerSlots(Integer officerSlots) {
        this.officerSlots = officerSlots;
    }

    /**
     * Add an officer to the officer list of the project.
     * @param officer
     */
    public void addToOfficerList(Officer officer) {
        this.officerList.add(officer);
    }

    /**
     * Add an application to the application list of the project.
     * @param application
     */
    public void addToApplicationList(Application application) {
        this.applicationList.add(application);
    }

    /**
     * Add a registration to the registration list of the project.
     * @param registration
     */
    public void addToRegistrationList(Registration registration) {
        this.registrationList.add(registration);
    }

    /**
     * Add an enquiry to the enquiry list of the project.
     * @param enquiry
     */
    public void addToEnquiryList(Enquiry enquiry) {
        this.enquiryList.add(enquiry);
    }

    /**
     * Converts the project information to a string representation.
     * @return A string representation of the project.
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(neighbourhood).append(",");
        for (Map.Entry<String, List<Integer>> entry : flatsInfo.entrySet()) {
            sb.append(entry.getKey()).append(",").append(entry.getValue().get(0)).append(",").append(entry.getValue().get(1)).append(",");
        }
        sb.append(applicationOpenDate).append(",").append(applicationCloseDate).append(",").append(manager.getNric()).append(",");
        sb.append(officerSlots).append(",");
        for (Officer officer : officerList) {
            sb.append(officer.getNric()).append(";");
        }
        return sb.toString();
    }

    /**
     * Filters the list of projects based on the user's access rights.
     * @param allProjects The list of projects to filter.
     * @param user The user whose permissions are being checked.
     * @return A list of projects that the user has permission to view.
     */
    public static List<Project> viewProjects(List<Project> allProjects, User user) {
        AccessControl<Project> accessControl = new ProjectAccess();

        List<Project> readableProjects = new ArrayList<>();
        for (Project project : allProjects) {
            String access = accessControl.check(project, user);
            if (access.contains("R")){
                readableProjects.add(project);
            }
        }
        return readableProjects;
    }

    /**
     * Filters the list of projects based on the flat type.
     * @param allProjects The list of projects to filter.
     * @param faltType The flat type to filter by (e.g., 2-Room, 3-Room).
     * @return A list of projects that the user has permission to view and are visible.
     */
    // Takes in the list of readable projects and filters them based on the flat type
    public static List<Project> filterProjectsByFlatType(List<Project> allProjects, String flatType) {
        List<Project> filteredProjects = allProjects.stream()
                .filter(project -> project.getFlatsInfo().containsKey(flatType) && project.getFlatsInfo().get(flatType).get(0) > 0) // Check if the flat type exists and has available units
                .toList();

        return filteredProjects;
    }

    /**
     * Filters the list of projects based on the neighbourhood.
     * @param allProjects The list of projects to filter.
     * @param neighbourhood The neighbourhood to filter by.
     * @return A list of projects that match the specified neighbourhood.
     */
    public static List<Project> filterProjectsByNeighbourhood(List<Project> allProjects, String neighbourhood) {
        List<Project> filteredProjects = allProjects.stream()
                .filter(project -> project.getNeighbourhood().equals(neighbourhood))
                .toList();

        return filteredProjects;
    }

    /**
     * Filters the list of projects based on the price range.
     * @param allProjects The list of projects to filter.
     * @param minPrice The minimum price for the filter.
     * @param maxPrice The maximum price for the filter.
     * @return A list of projects that match the specified price range.
     */
    public static List<Project> filterProjectsByPrice(List<Project> allProjects, Integer minPrice, Integer maxPrice) {
        List<Project> filteredProjects = allProjects.stream()
                .filter(project -> {
                    for (List<Integer> priceInfo : project.getFlatsInfo().values()) {
                        Integer price = priceInfo.get(1); // Get the selling price
                        if (price >= minPrice && price <= maxPrice) {
                            return true; // At least one flat type matches the price range
                        }
                    }
                    return false; // No flat type matches the price range
                })
                .toList();

        return filteredProjects;
    }
}   