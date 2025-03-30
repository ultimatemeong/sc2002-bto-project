package Misc;

import java.io.File;
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

    public static void writeFile(String filePath, List<String> data) {
        return;
    }
}
