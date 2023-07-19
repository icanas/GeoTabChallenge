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

    public DeviceService(GeotabApi geotabApi) {
        this.geotabApi = geotabApi;
    }

    public List<Device> getAllDevices(){
        return geotabApi.callGet(DeviceEntity,
                DeviceSearch.builder().build()).orElse(new ArrayList<>());
    }

    public Map<String, Supplier<List<LogRecord>>> getDevicesCoordinates(GeotabApi api, List<Device> devices){
        LocalDateTime toDate = nowUtcLocalDateTime();
        LocalDateTime fromDate = toDate.minusMinutes(1); //TODO: Fix this time interval
        Api.MultiCallBuilder call = api.buildMultiCall();
        Map<String, Supplier<List<LogRecord>>> result = new HashMap<>();
        for (Device d : devices) {
            result.put(d.getId().getId(), call.callGet(LogRecordEntity, LogRecordSearch.builder()
                    .deviceSearch(d.getId()).fromDate(fromDate).toDate(toDate).build()));
        }
        call.execute();
        return result;
    }

    public List<Trip> getTripsForDevice(GeotabApi geotabApi, Device vehicle) {
        return geotabApi.callGet(TripEntity,
                TripSearch.builder().deviceSearch(DeviceSearch.builder()
                        .serialNumber(vehicle.getSerialNumber()).build()).build()).orElse(new ArrayList<>());
    }

    public List<StatusData> getStatusData(GeotabApi geotabApi, Device vehicle) {
        return geotabApi.callGet(StatusDataEntity,
                StatusDataSearch.builder().deviceSearch(DeviceSearch.builder()
                        .serialNumber(vehicle.getSerialNumber()).build()).fromDate(LocalDateTime.now().minusHours(3)).toDate(LocalDateTime.now()).build()).orElse(new ArrayList<>());
    }


    public Map<String, List<Trip>> getTripMapForVehicles(GeotabApi geotabApi, List<Device> vehicles) {
        Collectors Collectors = null;
        return vehicles.stream()
                .collect(Collectors.toMap(
                        device -> device.getId().getId(),
                        vehicle -> getTripsForDevice(geotabApi, vehicle)
                ));
    }


}
