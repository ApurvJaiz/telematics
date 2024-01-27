package com.example.telematics;

import com.example.telematics.controller.ReportsController;
import com.example.telematics.controller.TelematicsController;
import com.example.telematics.model.TelematicsData;
import com.example.telematics.repository.TelematicsDataRepository;
import com.example.telematics.service.ReportsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.when;


class ReportsServiceTest extends MockServiceApplicationTests{

    @Mock
    private TelematicsDataRepository telematicsDataRepository;

    @Autowired
    private ReportsService reportsService;

    @Test
    void testGetOverspeedingVehicles() {
        //when(telematicsDataRepository.findBySpeedGreaterThan(anyDouble())).thenReturn(Collections.singletonList(createTelematicsData()));
        List<String> overspeedingReports = reportsService.getOverspeedingVehicle(Collections.singletonList(createTelematicsData()));
    }

    private TelematicsData createTelematicsData() {
        TelematicsData telematicsData = new TelematicsData();
        telematicsData.setVehicleId("Vehicle123");
        telematicsData.setSpeed(80.0);
        return telematicsData;
    }
}
