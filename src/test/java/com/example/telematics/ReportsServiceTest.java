package com.example.telematics;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.repository.TelematicsDataRepository;
import com.example.telematics.service.ReportsService;
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
class ReportsServiceTest extends TestDataProvider{
    @Autowired
    private ReportsService reportService;

    @Autowired
    private TelematicsDataRepository telematicsDataRepository;

    private List<TelematicsData> telematicsDataList;
    String apiURL = "/reports";

    @BeforeEach
    public void setup(){
        apiURL = "/reports";
        telematicsDataRepository.clearAllTelematicsData();
        telematicsDataList = createBulkTelematicsData(1000);
    }

    @Test
    void testDistanceReportGeneration() {
        //log.info(String.valueOf(telematicsDataList));
        Map<String, Double> distanceReport = reportService.generateDistanceReport("distance_report.csv", telematicsDataList);
        Assertions.assertNotNull(distanceReport);
    }

    @Test
    void testOverspeedingReportGeneration() {
        //log.info(String.valueOf(telematicsDataList));
        List<String> overspeedingReport = reportService.generateOverspeedingReport("overspeeding_report.csv", telematicsDataList);
        Assertions.assertNotNull(overspeedingReport);
    }
}
