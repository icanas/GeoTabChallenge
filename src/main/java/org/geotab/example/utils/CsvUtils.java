package org.geotab.example.utils;

import com.geotab.model.entity.device.Device;
import com.geotab.model.entity.device.GoDevice;
import com.geotab.model.entity.logrecord.LogRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CsvUtils {

    private static final String CSV_HEADER = "ID;timestamp;VIN;Coordinates";
    private static String CSV_RESULT_PATH = "data";

    /**
     * Set the CSV result path.
     *
     * @param newPath The new CSV result path.
     */
    public static void setCsvResultPath(String newPath) {
        CSV_RESULT_PATH = newPath;
    }

    /**
     * Write vehicle data to CSV files for each vehicle in the provided list.
     *
     * @param vehicles       List of Device objects representing the vehicles.
     * @param deviceDataMap  Map with vehicle ID as key and Supplier<List<LogRecord>> as value, providing log records for each vehicle.
     */
    public static void writeVehiclesDataToCsv(List<Device> vehicles, Map<String, Supplier<List<LogRecord>>> deviceDataMap) {
        vehicles.forEach(vehicle -> {
            try {
                writeVehicleDataToCsv((GoDevice) vehicle, deviceDataMap.get(vehicle.getId().getId()));
            } catch (IOException e) {
                String errorMessage = "Error when creating CSV for vehicle " + vehicle.getName() + ": " + e.getMessage();
                System.err.println(errorMessage);
                throw new RuntimeException(errorMessage, e);
            }
        });
    }

    /**
     * Write data for a single vehicle to the corresponding CSV file.
     *
     * @param vehicle        GoDevice object representing the vehicle.
     * @param vehicleDataMap Supplier providing a list of LogRecord objects for the vehicle.
     * @throws IOException   If an error occurs while writing to the file.
     */
    private static void writeVehicleDataToCsv(GoDevice vehicle, Supplier<List<LogRecord>> vehicleDataMap) throws IOException {
        String fileName = Paths.get(CSV_RESULT_PATH, vehicle.getName() + ".csv").toString();
        Path parentDir = Paths.get(CSV_RESULT_PATH);
        if (!Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        boolean fileExists = Files.exists(Paths.get(fileName));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            if (!fileExists) {
                writer.write(CSV_HEADER);
                writer.newLine();
            }

            List<LogRecord> logRecords = vehicleDataMap.get();
            if (!logRecords.isEmpty()) {
                LogRecord vehicleDataMapResult = logRecords.get(logRecords.size() - 1);
                String csvRow = formatCsvRow(vehicle, vehicleDataMapResult);
                writer.write(csvRow);
            }
        }
    }

    /**
     * Format a CSV row based on the vehicle and the latest log record for that vehicle.
     *
     * @param vehicle    GoDevice object representing the vehicle.
     * @param logRecord  LogRecord object representing the latest log record for the vehicle.
     * @return           A formatted CSV row as a String.
     */
    private static String formatCsvRow(GoDevice vehicle, LogRecord logRecord) {
        double latitude = logRecord.getLatitude();
        double longitude = logRecord.getLongitude();
        String timestamp = OffsetDateTime.now().toString();
        String vin = vehicle.getVehicleIdentificationNumber();
        return String.format("%s;%s;%s;%s%n", vehicle.getId().getId(), timestamp, vin, getCoordinates(latitude, longitude));
    }

    /**
     * Get the coordinates as a String based on the latitude and longitude values.
     *
     * @param latitude   Latitude value.
     * @param longitude  Longitude value.
     * @return           Coordinates as a formatted String.
     */
    private static String getCoordinates(double latitude, double longitude) {
        return latitude + "," + longitude;
    }
}
