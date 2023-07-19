package org.geotab.example.utils;

import com.geotab.model.entity.device.Device;
import com.geotab.model.entity.device.GoDevice;
import com.geotab.model.entity.logrecord.LogRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public class CsvUtils {

    private static final String CSV_HEADER = "ID,timestamp,VIN,Coordinates";

    public static void writeVehiclesDataToCsv(List<Device> vehicles, Map<String, Supplier<List<LogRecord>>> deviceDataMap)  {

        vehicles.forEach(
                vehicle -> {
                    try {
                        writeVehicleDataToCsv((GoDevice)vehicle, deviceDataMap.get(vehicle.getId().getId()));
                    } catch (IOException e) {
                        throw new RuntimeException(e); //TODO: Create message
                    }
                }
        );
    }

    private static void writeVehicleDataToCsv(GoDevice vehicle, Supplier<List<LogRecord>> vehicleDataMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vehicle.getName()))) {
            // Write CSV header
            writer.write(CSV_HEADER);
            writer.newLine();
            LogRecord vehicleDataMapResult = vehicleDataMap.get().get(vehicleDataMap.get().size()-1);
            String csvRow = String.format("%s,%s,%s,%s%n", vehicle.getId().getId(), OffsetDateTime.now(),
                    vehicle.getVehicleIdentificationNumber(), getCoordinates(vehicleDataMapResult));
            writer.write(csvRow);

        }

    }

    private static String getCoordinates(LogRecord logRecord) {
        double latitude = logRecord.getLatitude();
        double longitude = logRecord.getLongitude();
        return latitude + "," + longitude;
    }
}

