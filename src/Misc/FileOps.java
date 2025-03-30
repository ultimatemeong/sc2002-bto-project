package Misc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOps {
    private static final String COMMA_DELIMITER = ",";
    private static final String APPLICANT_LIST_FILE = "Assignment\\src\\ApplicantList.csv";
    private static final String OFFICER_LIST_FILE = "Assignment\\src\\OfficerList.csv";
    private static final String MANAGER_LIST_FILE = "Assignment\\src\\ManagerList.csv";
    private static final String PROJECT_LIST_FILE = "Assignment\\src\\ProjectList.csv";
    private static final String APPLICATION_LIST_FILE = "Assignment\\src\\ApplicationList.csv";
    private static final String REGISTRATION_LIST_FILE = "Assignment\\src\\RegistrationList.csv";

    public static List<List<String>> readFile(String filePath) throws Exception {
        // Implement file reading logic here
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
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public static void writeFile(String filePath, List<String> data) {
        // Implement file writing logic here

    }
}
