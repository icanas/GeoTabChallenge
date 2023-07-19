package org.geotab.example.service;

import com.geotab.api.Api;
import com.geotab.api.GeotabApi;
import com.geotab.model.entity.device.Device;
import com.geotab.model.entity.logrecord.LogRecord;
import com.geotab.model.entity.statusdata.StatusData;
import com.geotab.model.entity.trip.Trip;
import com.geotab.model.search.DeviceSearch;
import com.geotab.model.search.LogRecordSearch;
import com.geotab.model.search.StatusDataSearch;
import com.geotab.model.search.TripSearch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.geotab.api.DataStore.*;
import static com.geotab.util.DateTimeUtil.nowUtcLocalDateTime;

public class DeviceService {

    private final GeotabApi geotabApi;

    /**
     * Constructs a DeviceService object with the provided GeotabApi instance.
     *
     * @param geotabApi The GeotabApi instance to be used for API calls.
     */
    public DeviceService(GeotabApi geotabApi) {
        this.geotabApi = geotabApi;
    }

    /**
     * Get a list of all devices.
     *
     * @return A list of Device objects representing all devices.
     */
    public List<Device> getAllDevices(){
        return geotabApi.callGet(DeviceEntity,
                DeviceSearch.builder().build()).orElse(new ArrayList<>());
    }

    /**
     * Get a map of device IDs to Suppliers that provide a list of LogRecords for each device.
     *
     * @param vehicles The list of Device objects representing the vehicles to fetch LogRecords for.
     * @return A map of device IDs to Suppliers of LogRecords.
     */
    public Map<String, Supplier<List<LogRecord>>> getDevicesCoordinates(GeotabApi api, List<Device> vehicles){
        LocalDateTime toDate = nowUtcLocalDateTime();
        LocalDateTime fromDate = toDate.minusSeconds(1);
        Api.MultiCallBuilder call = api.buildMultiCall();
        Map<String, Supplier<List<LogRecord>>> result = new HashMap<>();
        for (Device v : vehicles) {
            result.put(v.getId().getId(), call.callGet(LogRecordEntity, LogRecordSearch.builder()
                    .deviceSearch(v.getId()).fromDate(fromDate).toDate(toDate).build()));
        }
        call.execute();
        return result;
    }

    /**
     * Get a list of trips for a specific device.
     * IMPORTANT **** DEAD CODE, NOT BEING USED **** ONLY FOR DEMO REASONS **** DELETE
     *
     * @param vehicle The Device object representing the vehicle to fetch trips for.
     * @return A list of Trip objects representing the trips for the device.
     */
    public List<Trip> getTripsForDevice(GeotabApi geotabApi, Device vehicle) {
        return geotabApi.callGet(TripEntity,
                TripSearch.builder().deviceSearch(DeviceSearch.builder()
                        .serialNumber(vehicle.getSerialNumber()).build()).build()).orElse(new ArrayList<>());
    }

    /**
     * Get a list of status data for a specific device
     * IMPORTANT **** DEAD CODE, NOT BEING USED **** ONLY FOR DEMO REASONS **** DELETE
     *
     * @param vehicle The Device object representing the vehicle to fetch status data for.
     * @return A list of StatusData objects representing the status data for the device.
     */
    public List<StatusData> getStatusData(GeotabApi geotabApi, Device vehicle) {
        return geotabApi.callGet(StatusDataEntity,
                StatusDataSearch.builder().deviceSearch(DeviceSearch.builder()
                        .serialNumber(vehicle.getSerialNumber()).build()).fromDate(LocalDateTime.now().minusHours(3)).toDate(LocalDateTime.now()).build()).orElse(new ArrayList<>());
    }

    /**
     * Get a map of device IDs to lists of trips for each device
     * IMPORTANT **** DEAD CODE, NOT BEING USED **** ONLY FOR DEMO REASONS **** DELETE
     *
     * @param vehicles The list of Device objects representing the vehicles to fetch trips for.
     * @return A map of device IDs to lists of Trip objects representing the trips for each device.
     */
    public Map<String, List<Trip>> getTripMapForVehicles(GeotabApi geotabApi, List<Device> vehicles) {
        Collectors Collectors = null;
        return vehicles.stream()
                .collect(Collectors.toMap(
                        device -> device.getId().getId(),
                        vehicle -> getTripsForDevice(geotabApi, vehicle)
                ));
    }


}
