package org.geotab.example.controller;

import com.geotab.model.entity.device.Device;
import com.geotab.model.entity.logrecord.LogRecord;
import org.geotab.example.login.LoginManager;
import org.geotab.example.service.DeviceService;
import org.geotab.example.utils.CsvUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ConsoleController extends Controller {

    private final DeviceService deviceService;
    private final ScheduledExecutorService executorService;
    private int EXECUTE_INTERVAL_MINUTES = 1;

    public ConsoleController(LoginManager loginManager) {
        super(loginManager.getGeotabApi());
        this.deviceService = new DeviceService(geotabApi);
        this.executorService = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void execute() {
        executorService.scheduleAtFixedRate(this::processVehicleData, 0, EXECUTE_INTERVAL_MINUTES, TimeUnit.MINUTES);
    }

    private void processVehicleData() {
        List<Device> devices = deviceService.getAllDevices();
        Map<String, Supplier<List<LogRecord>>> deviceDataMap = deviceService.getDevicesCoordinates(geotabApi, devices);
        CsvUtils.writeVehiclesDataToCsv(devices, deviceDataMap);
    }

    public void execute(int minutes) {
        EXECUTE_INTERVAL_MINUTES = minutes;
        this.execute();
    }
}
