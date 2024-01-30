package com.example.telematics;

import com.example.telematics.exception.TelematicsValidationException;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void testDailyDistanceReportGeneration() {
        //log.info(String.valueOf(telematicsDataList));
        Map<String, Double> distanceReport = reportService.generateDailyDistanceReport(String.valueOf(LocalDate.now()));
        Assertions.assertNotNull(distanceReport);
    }

    @Test
    void testMonthlyDistanceReportGeneration() {
        //log.info(String.valueOf(telematicsDataList));
        Map<String, Double> distanceReport = reportService.generateMonthlyDistanceReport(String.valueOf(LocalDate.now().getMonthValue()), String.valueOf(LocalDate.now().getYear()));
        Assertions.assertNotNull(distanceReport);
    }

    @Test
    void testDailyOverspeedingReportGeneration() {
        //log.info(String.valueOf(telematicsDataList));
        List<String> overspeedingReport = reportService.generateDailyOverspeedingReport(String.valueOf(LocalDate.now()));
        Assertions.assertNotNull(overspeedingReport);
    }

    @Test
    void testMonthlyOverspeedingReportGeneration() {
        //log.info(String.valueOf(telematicsDataList));
        List<String> overspeedingReport = reportService.generateMonthlyOverspeedingReport(String.valueOf(LocalDate.now().getMonthValue()), String.valueOf(LocalDate.now().getYear()));
        Assertions.assertNotNull(overspeedingReport);
    }

    @Test
    void testInvalidDateFormatForMonthlyDistanceReport() {
        assertThrows(TelematicsValidationException.class,
                () -> reportService.generateMonthlyDistanceReport("13", "2024"));
    }
    @Test
    void testInvalidDateFormatForMonthlyOverspeedingReport() {
        assertThrows(TelematicsValidationException.class,
                () -> reportService.generateMonthlyOverspeedingReport("13", "2024"));
    }
    @Test
    void testInvalidDateFormatForDailyDistanceReport() {
        assertThrows(TelematicsValidationException.class,
                () -> reportService.generateDailyDistanceReport("01-01-2024")); // expected yyyy-MM-dd
    }
    @Test
    void testInvalidDateFormatForDailyOverspeedingReport() {
        assertThrows(TelematicsValidationException.class,
                () -> reportService.generateDailyOverspeedingReport("01-01-2024")); // expected yyyy-MM-dd
    }

}
