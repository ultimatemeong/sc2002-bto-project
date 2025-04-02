package Projects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Users.*;
import Misc.*;

public class Project {
    private String name;
    private String neighbourhood;
    private String[] flatTypes; // e.g. 2-room, 3-room
    private int[] sellingPrices; // e.g. 2-room: 300k, 3-room: 500k
    private int[] availableUnits; // e.g. 2-room: 100, 3-room: 50
    private LocalDate applicationStartDate; // e.g. 2023-01-01  (Does not need Date class, as we done need it to be too precise)
    private LocalDate applicationEndDate; // e.g. 2023-01-31  
    private boolean isVisible;


    private List<String> ApplicantList = new ArrayList<>();

    private int officerSlots = 10;
    private List<String> OfficerList = new ArrayList<>();

    private String manager;

    public Project(String name, String neighbourhood, String[] flatTypes, int[] sellingPrices, int[] availableUnits, LocalDate applicationStartDate, LocalDate applicationEndDate) {
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.flatTypes = flatTypes;
        this.sellingPrices = sellingPrices;
        this.availableUnits = availableUnits;
        this.applicationStartDate = applicationStartDate;
        this.applicationEndDate = applicationEndDate;
        this.isVisible = true; // Default to visible
    }

    public void addOfficer(Officer officer){
        if (OfficerList.size() < officerSlots) {
            OfficerList.add(officer.getName());
        } else {
            System.out.println("No more slots available for officers.");
        }
    }

    public List<String> getOfficerList() {
        return OfficerList;
    }

    public List<String> getApplicantList() {
        return ApplicantList;
    }
}
