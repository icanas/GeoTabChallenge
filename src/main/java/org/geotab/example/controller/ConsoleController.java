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


/**
 * Controller class responsible for executing scheduled vehicle data processing tasks.
 */
public class ConsoleController extends Controller {

    private final DeviceService deviceService;
    private final ScheduledExecutorService executorService;
    private int EXECUTE_INTERVAL_MINUTES = 1;

    /**
     * Constructs a ConsoleController instance.
     *
     * @param loginManager The LoginManager instance used to retrieve the GeotabApi.
     */
    public ConsoleController(LoginManager loginManager) {
        super(loginManager.getGeotabApi());
        this.deviceService = new DeviceService(geotabApi);
        this.executorService = Executors.newScheduledThreadPool(1);
    }

    /**
     * Executes the scheduled vehicle data processing task at the specified interval.
     */
    @Override
    public void execute() {
        executorService.scheduleAtFixedRate(this::processVehicleData, 0, EXECUTE_INTERVAL_MINUTES, TimeUnit.MINUTES);
    }

    /**
     * Processes vehicle data, retrieves device information, and writes them to CSV files.
     */
    private void processVehicleData() {
        List<Device> devices = deviceService.getAllDevices();
        Map<String, Supplier<List<LogRecord>>> deviceDataMap = deviceService.getDevicesCoordinates(devices);
        Map<String, Double> deviceOdometerMap = deviceService.getDevicesOdometer(devices);
        CsvUtils.writeVehiclesDataToCsv(devices, deviceDataMap, deviceOdometerMap);
    }

    /**
     * Executes the scheduled vehicle data processing task at the specified interval in minutes.
     *
     * @param minutes The interval in minutes for executing the vehicle data processing task.
     */
    public void execute(int minutes) {
        EXECUTE_INTERVAL_MINUTES = minutes;
        this.execute();
    }
}
