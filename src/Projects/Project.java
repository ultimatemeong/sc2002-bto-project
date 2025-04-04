package Projects;

import Users.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private String neighbourhood;
    private String[] flatTypes; // e.g. 2-room, 3-room
    private int[] sellingPrices; // e.g. 2-room: 300k, 3-room: 500k
    private int[] availableUnits; // e.g. 2-room: 100, 3-room: 50
    private LocalDate applicationOpenDate; // e.g. 2023-01-01  (Does not need Date class, as we done need it to be too precise)
    private LocalDate applicationCloseDate; // e.g. 2023-01-31  
    private boolean visibility;

    private String manager;

    private int officerSlots = 10;
    private List<String> officerList = new ArrayList<>();
    
    private List<String> applicationList = new ArrayList<>();

    public Project(String name, String neighbourhood, String[] flatTypes, int[] sellingPrices, int[] availableUnits, LocalDate applicationOpenDate, LocalDate applicationCloseDate) {
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.flatTypes = flatTypes;
        this.sellingPrices = sellingPrices;
        this.availableUnits = availableUnits;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.visibility = true; // Default to visible
    }

    public boolean isVisible() {return visibility;}

    public void addOfficer(Officer officer){
        if (officerList.size() < officerSlots) {
            officerList.add(officer.getName());
        } else {
            System.out.println("No more slots available for officers.");
        }
    }

    public List<String> getOfficerList() {
        return officerList;
    }

    public List<String> getApplicantList() {
        return applicationList;
    }
}
