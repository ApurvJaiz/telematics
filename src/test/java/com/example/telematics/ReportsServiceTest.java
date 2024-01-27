package com.example.telematics;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.repository.TelematicsDataRepository;
import com.example.telematics.service.ReportsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;


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
