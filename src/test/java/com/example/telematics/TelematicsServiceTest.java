package com.example.telematics;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.repository.TelematicsDataRepository;
import com.example.telematics.service.ReportsService;
import com.example.telematics.service.TelematicsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class TelematicsServiceTest extends TestDataProvider{

    @Autowired
    private TelematicsService telematicsService;

    @Autowired
    private ReportsService reportService;

    @Autowired
    private TelematicsDataRepository telematicsDataRepository;

    private List<TelematicsData> telematicsData;

    @BeforeEach
    public void setup(){
        telematicsData = createBulkTelematicsData(1000);
    }

    @Test
    void testTelematicsDataEntry() {
        TelematicsData telematicsData = createSampleTelematicsData();
        telematicsDataRepository.saveTelematicsData(telematicsData.getVehicleId(), telematicsData);
        List<TelematicsData> storedData = telematicsService.getTelematicsData(telematicsData.getVehicleId());
        Assertions.assertNotNull(storedData);
        Assertions.assertEquals(telematicsData, storedData.get(0));
    }

    @Test
    void testDistanceReportGeneration() {
        log.info(String.valueOf(telematicsData));
        Map<String, Double> distanceReport = reportService.generateDistanceReport("distance_report.csv", telematicsData);
        Assertions.assertNotNull(distanceReport);
    }

    @Test
    void testOverspeedingReportGeneration() {
        log.info(String.valueOf(telematicsData));
        List<String> overspeedingReport = reportService.generateOverspeedingReport("overspeeding_report.csv", telematicsData);
        Assertions.assertNotNull(overspeedingReport);
    }
}
