package org.geotab.example.service;

import com.geotab.api.Api;
import com.geotab.api.GeotabApi;
import com.geotab.model.entity.device.Device;
import com.geotab.model.entity.logrecord.LogRecord;
import com.geotab.model.entity.statusdata.StatusData;
import com.geotab.model.search.DeviceSearch;
import com.geotab.model.search.DiagnosticSearch;
import com.geotab.model.search.LogRecordSearch;
import com.geotab.model.search.StatusDataSearch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.geotab.api.DataStore.*;
import static com.geotab.util.DateTimeUtil.nowUtcLocalDateTime;

public class DeviceService {

    private static final String DIAGNOSTIC_ODOMETER_ID = "DiagnosticOdometerId";

    private final GeotabApi geotabApi;

    /**
     * Constructs a DeviceService object with the provided GeotabApi instance
     *
     * @param geotabApi The GeotabApi instance to be used for API calls
     */
    public DeviceService(GeotabApi geotabApi) {
        this.geotabApi = geotabApi;
    }

    /**
     * Get a list of all devices
     *
     * @return A list of Device objects representing all devices
     */
    public List<Device> getAllDevices(){
        return geotabApi.callGet(DeviceEntity,
                DeviceSearch.builder().build()).orElse(new ArrayList<>());
    }

    /**
     * Get a map of device IDs to Suppliers that provide a list of LogRecords for each device
     *
     * @param vehicles The list of Device objects representing the vehicles to fetch LogRecords for
     * @return A map of device IDs to Suppliers of LogRecords
     */
    public Map<String, Supplier<List<LogRecord>>> getDevicesCoordinates(List<Device> vehicles){
        LocalDateTime toDate = nowUtcLocalDateTime();
        LocalDateTime fromDate = toDate.minusSeconds(5);
        Api.MultiCallBuilder call = geotabApi.buildMultiCall();
        Map<String, Supplier<List<LogRecord>>> result = new HashMap<>();
        for (Device v : vehicles) {
            result.put(v.getId().getId(), call.callGet(LogRecordEntity, LogRecordSearch.builder()
                    .deviceSearch(v.getId()).fromDate(fromDate).toDate(toDate).build()));
        }
        call.execute();
        return result;
    }

    /**
     * Retrieves the odometer data for a list of devices
     * This method fetches the latest odometer data for each device in the provided list
     * The result is returned as a Map, where the device ID is mapped to its corresponding odometer value
     *
     * @param vehicles A List of Device for which to retrieve odometer data
     * @return A Map with device IDs as keys and their corresponding odometer values as Double
     */
     public Map<String, Double> getDevicesOdometer(List<Device> vehicles) {

        Map<String, Double> result = new HashMap<>();

        vehicles.forEach(
                v -> {
                    List<StatusData> statusData = geotabApi.callGet(StatusDataEntity,StatusDataSearch.builder()
                                    .fromDate(nowUtcLocalDateTime())
                                    .toDate(nowUtcLocalDateTime().minusSeconds(5))
                                    .deviceSearch(DeviceSearch.builder().id(v.getId().getId()).build())
                                    .diagnosticSearch(
                                            DiagnosticSearch.builder()
                                                    .id(DIAGNOSTIC_ODOMETER_ID)
                                                    .name(v.getName())
                                                    .build()).build())
                            .orElse(new ArrayList<>());
                    result.put(v.getId().getId(), statusData.get(0).getData());
                }
        );
        return result;
    }
}
