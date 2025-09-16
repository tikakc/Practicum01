import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Name: Tika Khadka
 */
public class PersonGenerator {

    public static void main(String[] args) {
        Scanner pipe = new Scanner(System.in);
        ArrayList<String> personRecords = new ArrayList<>();
        boolean continueInput = true;

        System.out.println("=== Person Data Generator ===");
        System.out.println("This program collects person information and saves it to a CSV file.");
        System.out.println("Data format: ID, FirstName, LastName, Title, YearOfBirth");
        System.out.println();

        // Collect person data from user
        while (continueInput) {
            System.out.println("Enter person information:");

            // Get ID (String)
            String id = SafeInput.getNonZeroLenString(pipe, "Enter ID (e.g., 000001)");

            // Get First Name (String)
            String firstName = SafeInput.getNonZeroLenString(pipe, "Enter First Name");

            // Get Last Name (String)
            String lastName = SafeInput.getNonZeroLenString(pipe, "Enter Last Name");

            // Get Title (String)
            String title = SafeInput.getNonZeroLenString(pipe, "Enter Title (e.g., Mr., Ms., Esq., Mrs., Dr.)");

            // Get Year of Birth (int)
            int yearOfBirth = SafeInput.getRangedInt(pipe, "Enter Year of Birth", 1000, 2025);

            // Create the CSV record
            String record = id + ", " + firstName + ", " + lastName + ", " + title + ", " + yearOfBirth;
            personRecords.add(record);

            System.out.println("\nRecord added: " + record);
            System.out.println("Total records: " + personRecords.size());

            // Ask if user wants to continue
            continueInput = SafeInput.getYNConfirm(pipe, "Do you want to add another person?");
        }

        // If we have records, save them to a file
        if (!personRecords.isEmpty()) {
            // Get filename from user
            String filename = SafeInput.getNonZeroLenString(pipe, "Enter filename to save data");

            // Automatically add .txt extension if not present
            if (!filename.toLowerCase().endsWith(".txt")) {
                filename = filename + ".txt";
            }

            // Save the records to file
            saveRecordsToFile(personRecords, filename);

            // Display summary
            System.out.println("File saved as: " + filename);

        } else {
            System.out.println("No records to save.");
        }

        System.out.println("\nProgram completed. Thank you!");
    }

    /**
     * Saves the person records to a text file using NIO
     */
    private static void saveRecordsToFile(ArrayList<String> records, String filename) {
        try {
            // Get current working directory and create path
            File workingDirectory = new File(System.getProperty("user.dir"));
            Path file = Paths.get(workingDirectory.getPath() + File.separator + filename);

            // Create BufferedWriter using NIO
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            // Write each record to the file
            for (String record : records) {
                writer.write(record, 0, record.length());
                writer.newLine();
            }

            // Close the writer to flush and seal the file
            writer.close();
            System.out.println("\nData file written successfully!");

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
