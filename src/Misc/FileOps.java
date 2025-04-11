package Misc;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOps {
    private static final String APPLICANT_LIST_FILE = "src\\Data\\ApplicantList.csv";
    private static final String OFFICER_LIST_FILE = "src\\Data\\OfficerList.csv";
    private static final String MANAGER_LIST_FILE = "src\\Data\\ManagerList.csv";
    private static final String PROJECT_LIST_FILE = "src\\Data\\ProjectList.csv";
    private static final String APPLICATION_LIST_FILE = "src\\Data\\ApplicationList.csv";
    private static final String REGISTRATION_LIST_FILE = "src\\Data\\RegistrationList.csv";

    public static String getApplicantListFile() {
        return APPLICANT_LIST_FILE;
    }

    public static String getOfficerListFile() {
        return OFFICER_LIST_FILE;
    }

    public static String getManagerListFile() {
        return MANAGER_LIST_FILE;
    }

    public static String getProjectListFile() {
        return PROJECT_LIST_FILE;
    }

    public static String getApplicationListFile() {
        return APPLICATION_LIST_FILE;
    }

    public static String getRegistrationListFile() {
        return REGISTRATION_LIST_FILE;
    }

    public static List<List<String>> readFile(String filePath) throws Exception {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }
        return records;
    }

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public static void writeFile(String filePath, List<List<String>> data) throws Exception{
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (List<String> record : data) {
                for (String i : record) {
                    writer.print(i + ",");
                }
                writer.println(); // Move to the next line after writing a record
            }
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Example usage of the FileOps class
    // public static void main(String[] args) {
    //     try {
    //         // Read from file example  
    //         List<List<String>> data = readFile(APPLICANT_LIST_FILE);
    //         for (List<String> record : data) {
    //             System.out.println(record);
    //         }
    //         // Write to file example
    //         data.add(List.of("John Doe", "12345678A"));
    //         writeFile(APPLICANT_LIST_FILE, data);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
