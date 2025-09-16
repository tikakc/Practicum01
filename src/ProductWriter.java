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
 Name: Tika Khadka
 */
public class ProductWriter {

    public static void main(String[] args) {
        Scanner pipe = new Scanner(System.in);
        ArrayList<String> productRecords = new ArrayList<>();
        boolean continueInput = true;

        System.out.println("=== Product Data Writer ===");
        System.out.println("This program collects product information and saves it to a CSV file.");
        System.out.println("Data format: ID, Name, Description, Cost");
        System.out.println();

        // Collect product data from user
        while (continueInput) {
            System.out.println("Enter product information:");

            // Get ID (String)
            String id = SafeInput.getNonZeroLenString(pipe, "Enter Product ID (e.g., 000001)");

            // Get Name (String)
            String name = SafeInput.getNonZeroLenString(pipe, "Enter Product Name");

            // Get Description (String)
            String description = SafeInput.getNonZeroLenString(pipe, "Enter Product Description");

            // Get Cost (Double)
            double cost = SafeInput.getDouble(pipe, "Enter Product Cost");

            // Create the CSV record
            String record = id + ", " + name + ", " + description + ", " + cost;
            productRecords.add(record);

            System.out.println("\nRecord added: " + record);
            System.out.println("Total records: " + productRecords.size());

            // Ask if user wants to continue
            continueInput = SafeInput.getYNConfirm(pipe, "Do you want to add another product?");
        }

        // If we have records, save them to a file
        if (!productRecords.isEmpty()) {
            // Get filename from user
            String filename = SafeInput.getNonZeroLenString(pipe, "Enter filename to save data");

            // Automatically add .txt extension if not present
            if (!filename.toLowerCase().endsWith(".txt")) {
                filename = filename + ".txt";
            }

            // Save the records to file
            saveRecordsToFile(productRecords, filename);

            // Display summary
            System.out.println("File saved as: " + filename);
            System.out.println("\nRecords saved:");
            for (int i = 0; i < productRecords.size(); i++) {
                System.out.println((i + 1) + ". " + productRecords.get(i));
            }
        } else {
            System.out.println("No records to save.");
        }

        System.out.println("\nProgram completed. Thank you!");
    }

    /**
     * Saves the product records to a text file using NIO
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

            // Close the writer
            writer.close();
            System.out.println("\nData file written successfully!");

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}