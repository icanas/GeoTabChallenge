import com.geotab.api.GeotabApi;
import com.geotab.model.entity.device.Device;
import com.geotab.model.search.DeviceSearch;
import org.geotab.example.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.geotab.api.DataStore.DeviceEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DeviceServiceTest {

    private GeotabApi geotabApi;
    private DeviceService deviceService;
    private List<Device> mockDevices;

    @BeforeEach
    public void setUp() {
        geotabApi = Mockito.mock(GeotabApi.class);
        deviceService = new DeviceService(geotabApi);

        mockDevices = new ArrayList<>();
        mockDevices.add(new Device());
        mockDevices.add(new Device());
    }

    @Test
    public void testGetAllDevices() {
        when(geotabApi.callGet(DeviceEntity, DeviceSearch.builder().build())).thenReturn(Optional.ofNullable(mockDevices));
        List<Device> result = deviceService.getAllDevices();
        assertEquals(mockDevices, result);
    }

}