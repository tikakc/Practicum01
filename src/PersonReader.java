import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 * Name: Tika Khadka
 */
public class PersonReader {

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        ArrayList<String> lines = new ArrayList<>();

        final int FIELDS_LENGTH = 5;

        String id, firstName, lastName, title;
        int yob;

        System.out.println("=== Person Data Reader ===");
        System.out.println("Select a person data file to read and display.");

        try {
            // Get current working directory
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            // Show file chooser dialog
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                // Use NIO to read the file
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                // Read all lines into ArrayList
                while (reader.ready()) {
                    rec = reader.readLine();
                    if (rec != null && !rec.trim().isEmpty()) {
                        lines.add(rec);
                    }
                }
                reader.close();

                System.out.println("\nData file read successfully!");
                System.out.println("File: " + selectedFile.getName());
                System.out.println("Records found: " + lines.size());

                // Display formatted header
                System.out.println("\n" + "=".repeat(80));
                System.out.printf("%-8s %-15s %-15s %-8s %-6s%n",
                        "ID#", "Firstname", "Lastname", "Title", "YOB");
                System.out.println("=".repeat(80));

                // Process and display each record
                for (String line : lines) {
                    String[] fields = line.split(",");

                    if (fields.length == FIELDS_LENGTH) {
                        // Trim whitespace from all fields
                        id = fields[0].trim();
                        firstName = fields[1].trim();
                        lastName = fields[2].trim();
                        title = fields[3].trim();
                        yob = Integer.parseInt(fields[4].trim());

                        // Display in formatted columns
                        System.out.printf("%-8s %-15s %-15s %-8s %-6d%n",
                                id, firstName, lastName, title, yob);
                    } else {
                        System.out.println(line);
                    }
                }

                System.out.println("=".repeat(80));
                System.out.println("\nData display completed successfully!");

            } else {
                // User cancelled file selection
                System.out.println("No file selected. Program terminated.");
                System.out.println("Please run the program again and select a file.");
                System.exit(0);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing year of birth: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nProgram completed. Thank you!");
    }
}