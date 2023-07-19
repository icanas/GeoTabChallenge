package org.geotab.example.main;

import com.geotab.model.login.Credentials;
import org.geotab.example.controller.ConsoleController;
import org.geotab.example.login.LoginManager;
import org.geotab.example.utils.ConnectionUtils;
import org.geotab.example.utils.CsvUtils;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class BackUpVehicleData {

    private static int EXECUTE_INTERVAL_MINUTES = 1;

    public static void main(String[] args) {
        LoginManager loginManager = new LoginManager();
        ConsoleController consoleController = new ConsoleController(loginManager);

        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Display the options menu
        displayMenu();

        // Read user input and execute corresponding option
        while (true) {
            System.out.print("Enter option (1: Execute, 2: Change CSV Path, 3: Print YML Values, 4: Change Execute Interval, 5: Exit): ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    consoleController.execute(EXECUTE_INTERVAL_MINUTES);
                    break;
                case 2:
                    // Change CSV data output path
                    System.out.print("Enter new CSV data output path: ");
                    String newPath = scanner.nextLine();
                    CsvUtils.setCsvResultPath(newPath);
                    System.out.println("CSV data output path changed to: " + newPath);
                    break;
                case 3:
                    // Print YAML file values (Assuming you have a method to read and display YAML values)
                    try {
                        printYamlFileValues("credentials.yml");
                    } catch (Exception e) {
                        System.err.println("Error while reading YAML file: " + e.getMessage());
                    }
                    break;
                case 4:
                    // Change execute interval
                    System.out.print("Enter new execute interval (in minutes): ");
                    int newInterval = scanner.nextInt();
                    EXECUTE_INTERVAL_MINUTES = newInterval;
                    System.out.println("Execute interval changed to: " + newInterval + " minutes");
                    break;
                case 5:
                    // Stop the scheduled execution and exit the program
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("=== Options Menu ===");
        System.out.println("1. Start Scheduled Execution");
        System.out.println("2. Change CSV Data Output Path");
        System.out.println("3. Print YAML File Values");
        System.out.println("4. Change Execute Interval (Minutes)");
        System.out.println("5. Exit");
        System.out.println("====================");
    }

    private static void printYamlFileValues(String fileName) {
        System.out.println("YAML File Values:");
        Credentials credentials = ConnectionUtils.readCredentialsFromYaml(fileName);
        System.out.println("Database: " + credentials.getDatabase());
        System.out.println("Username: " + credentials.getUserName());
        System.out.println("Password: " + credentials.getPassword());
    }
}
