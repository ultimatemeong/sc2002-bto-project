import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Users.*;
import Projects.*;
import Misc.*;

public class App {
    private static List<Project> all_projects = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current Date and Time: " + currentDateTime);
    }
}
