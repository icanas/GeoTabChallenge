package org.geotab.example.service;

import com.geotab.api.Api;
import com.geotab.api.GeotabApi;
import com.geotab.model.entity.device.Device;
import com.geotab.model.entity.logrecord.LogRecord;
import com.geotab.model.search.DeviceSearch;
import com.geotab.model.search.LogRecordSearch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.geotab.api.DataStore.DeviceEntity;
import static com.geotab.api.DataStore.LogRecordEntity;
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

    public Map<String, Supplier<List<LogRecord>>> getDevicesCoordinates(GeotabApi api, List<Device> devices){ //TODO: remove the static everywhere
        LocalDateTime toDate = nowUtcLocalDateTime();
        LocalDateTime fromDate = toDate.minusSeconds(1); //TODO: Fix this time interval
        Api.MultiCallBuilder call = api.buildMultiCall();
        Map<String, Supplier<List<LogRecord>>> result = new HashMap<>();
        for (Device d : devices) {
            result.put(d.getId().getId(), call.callGet(LogRecordEntity, LogRecordSearch.builder()
                    .deviceSearch(d.getId()).fromDate(fromDate).toDate(toDate).build()));
        }
        call.execute();
        return result;
    }

}
