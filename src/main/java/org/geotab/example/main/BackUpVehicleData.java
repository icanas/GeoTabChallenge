package org.geotab.example.main;

import com.geotab.api.Api;
import com.geotab.http.request.param.GetFeedParameters;
import com.geotab.model.FeedResult;
import com.geotab.model.entity.logrecord.LogRecord;
import com.geotab.api.GeotabApi;
import com.geotab.model.entity.device.Device;
import com.geotab.model.entity.trip.Trip;
import com.geotab.model.entity.enginetype.EngineType;
import com.geotab.model.search.DeviceSearch;
import com.geotab.model.search.LogRecordSearch;
import org.geotab.example.controller.ConsoleController;
import org.geotab.example.login.LoginManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.function.Supplier;

import static com.geotab.api.DataStore.DeviceEntity;
import static com.geotab.api.DataStore.LogRecordEntity;
import static com.geotab.util.DateTimeUtil.nowUtcLocalDateTime;

public class BackUpVehicleData {

    private static final String FILE_PATH = "./data"; // TODO: Change and manage paths inside options

    public static void main(String[] args) {
        LoginManager loginManager = new LoginManager();
        ConsoleController consoleController = new ConsoleController(loginManager);
        consoleController.execute();
    }


/*    public static void main(String[] args) {
        try {
            LoginManager loginManager = new LoginManager();
            GeotabApi geotabApi = loginManager.getGeotabApi();

            // Download all available vehicles
            List<Device> devices = geotabApi.callGet(DeviceEntity,
                    DeviceSearch.builder().build()).orElse(new ArrayList<>());

            loadTrips(geotabApi, devices.get(0));
            getCoordinates(geotabApi, devices);

            // TODO: Delete this and line above
            for (Device vehicle : devices) {
                String fileName = FILE_PATH + vehicle.getName()+ ".csv";
                downloadVehicleData(geotabApi, vehicle, fileName);
            }

            // Incrementally download new data every minute
            while (true) {
                List<Device> vehiclesList = geotabApi.callGet(DeviceEntity,
                        DeviceSearch.builder().build()).orElse(new ArrayList<>());
                for (Device vehicle : vehiclesList) {
                    String fileName = FILE_PATH + vehicle.getId() + ".csv";
                    downloadVehicleData(geotabApi, vehicle, fileName);
                }

                Thread.sleep(60000); // Wait for 1 minute
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error while generating csv " + e.getMessage());
        }
    }

    private static void getCoordinates(GeotabApi api, List<Device> devices){ //TODO: remove the static everywhere
        LocalDateTime toDate = nowUtcLocalDateTime();
        LocalDateTime fromDate = toDate.minusMinutes(1);
        Api.MultiCallBuilder call = api.buildMultiCall();
        Map<Device, Supplier<List<LogRecord>>> result = new HashMap<>();
        for (Device d : devices) {
            result.put(d, call.callGet(LogRecordEntity, LogRecordSearch.builder()
                    .deviceSearch(d.getId()).fromDate(fromDate).toDate(toDate).build()));
        }
        call.execute();
    }

    private static List<Trip> loadTrips(GeotabApi geotabApi, Device vehicle) {

        Optional<FeedResult<Trip>> trips = geotabApi.callGetFeed(GetFeedParameters.getFeedParamsBuilder().search(DeviceSearch.builder()
                        .serialNumber(vehicle.getSerialNumber()).build())
                .typeName(LogRecord.class.getSimpleName()).build(), Trip.class);


        return trips.get().getData();
    }

    private static void downloadVehicleData(GeotabApi geotabApi, Device vehicle, String fileName)
            throws IOException {

        Optional<FeedResult<LogRecord>> logRecords = geotabApi.callGetFeed(GetFeedParameters.getFeedParamsBuilder().search(DeviceSearch.builder()
                        .serialNumber(vehicle.getSerialNumber()).build())
                .typeName(LogRecord.class.getSimpleName()).build(), LogRecord.class);


        //TODO: vin vehicleIdentificationNumber
        if (logRecords.isPresent()) {
            List<LogRecord> logRecordList = logRecords.get().getData();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                for (LogRecord logRecord : logRecordList) {
                    String csvRow = String.format("%s,%s,%s,%s%n", logRecord.getId(), logRecord.getDateTime(),
                            logRecord.getLatitude(), logRecord.getLongitude());
                    writer.write(csvRow);
                }
            }
        }
    }*/
}