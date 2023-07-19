package org.geotab.example.controller;

import com.geotab.model.entity.device.Device;
import com.geotab.model.entity.logrecord.LogRecord;
import com.geotab.model.entity.statusdata.StatusData;
import com.geotab.model.entity.trip.Trip;
import org.geotab.example.login.LoginManager;
import org.geotab.example.service.DeviceService;
import org.geotab.example.utils.CsvUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ConsoleController extends Controller {

    private final DeviceService deviceService;

    public ConsoleController(LoginManager loginManager){
        super(loginManager.getGeotabApi());
        this.deviceService = new DeviceService(geotabApi);
    }

    @Override
    public void execute() {
        List<Device> devices = deviceService.getAllDevices();
        Map<String, Supplier<List<LogRecord>>> deviceDataMap = deviceService.getDevicesCoordinates(geotabApi, devices);
        CsvUtils.writeVehiclesDataToCsv(devices, deviceDataMap);

    }
}
