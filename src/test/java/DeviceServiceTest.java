import com.geotab.api.GeotabApi;
import com.geotab.model.Id;
import com.geotab.model.entity.device.Device;
import com.geotab.model.search.DeviceSearch;
import org.geotab.example.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.geotab.api.DataStore.DeviceEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeviceServiceTest {

    private GeotabApi geotabApi;
    private DeviceService deviceService;
    private List<Device> mockDevices;

    @BeforeEach
    public void setUp() {
        geotabApi = mock(GeotabApi.class);
        deviceService = new DeviceService(geotabApi);

        // Create mock devices for testing
        mockDevices = new ArrayList<>();
        Device device1 = new Device();
        device1.setId(new Id("device1"));
        Device device2 = new Device();
        device2.setId(new Id("device2"));
        mockDevices.add(device1);
        mockDevices.add(device2);
    }

    @Test
    public void testGetAllDevices() {
        when(geotabApi.callGet(DeviceEntity, DeviceSearch.builder().build())).thenReturn(Optional.ofNullable(mockDevices));
        List<Device> result = deviceService.getAllDevices();
        assertEquals(mockDevices, result);
    }


}