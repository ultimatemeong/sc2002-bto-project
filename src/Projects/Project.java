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

    private Manager manager;

    private int officerSlots = 10;
    private List<Officer> officerList = new ArrayList<>();
    
    private List<Application> applicationList = new ArrayList<>();


    public String getName() {
        return name;
    }

    public boolean isVisible(){
        return visibility;
    }

    public Manager getManager() {
        return manager;
    }

    public List<Officer> getOfficerList() {
        return officerList;
    }


}
