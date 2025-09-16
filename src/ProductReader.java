import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 Name: Tika Khadka
 */
public class ProductReader {

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        ArrayList<String> lines = new ArrayList<>();

        final int FIELDS_LENGTH = 4;

        String id, name, description;
        double cost;

        System.out.println("=== Product Data Reader ===");
        System.out.println("Select a product data file to read and display.");

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

                // Display formatted output using String.format (neatly formatted)
                displayFormattedData(lines);

            } else {
                // User cancelled file selection
                System.out.println("No file selected. Program terminated.");
                System.out.println("Please run the program again and select a file.");
                System.exit(0);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nProgram completed. Thank you!");
    }

    /**
     * Display the product data in a neatly formatted columnar display
     */
    private static void displayFormattedData(ArrayList<String> lines) {
        final int FIELDS_LENGTH = 4;

        System.out.println("\n" + "=".repeat(85));
        System.out.println(String.format("%-8s %-15s %-35s %12s",
                "ID#", "Name", "Description", "Cost"));
        System.out.println("=".repeat(85));

        // Process and display each record
        for (String line : lines) {
            String[] fields = line.split(",");

            if (fields.length == FIELDS_LENGTH) {
                try {
                    // Trim whitespace from all fields
                    String id = fields[0].trim();
                    String name = fields[1].trim();
                    String description = fields[2].trim();
                    double cost = Double.parseDouble(fields[3].trim());

                    // Display in formatted columns using String.format
                    System.out.println(String.format("%-8s %-15s %-35s $%10.2f",
                            id, name, description, cost));

                } catch (NumberFormatException e) {
                    System.out.println("Error parsing cost in record: " + line);
                }
            } else {
                System.out.println("Warning: Found a record that may be corrupt: ");
                System.out.println(line);
            }
        }

        System.out.println("=".repeat(85));
        System.out.println("Data display completed successfully!");
    }
}