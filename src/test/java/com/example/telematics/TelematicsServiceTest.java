package com.example.telematics;

import com.example.telematics.exception.TelematicsValidationException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @BeforeEach
    public void setup(){
        telematicsDataRepository.clearAllTelematicsData();
    }

    @Test
    void testTelematicsDataEntry() {
        TelematicsData telematicsData = createSampleTelematicsData();
        insertAndValidateTelematicsData(telematicsData);
    }

    @Test
    void testEmptyVehicleIdTelematicsDataEntry() {
        TelematicsData telematicsData = createSampleTelematicsData();
        telematicsData.setVehicleId("");
        invalidTelematicsDataValidation(telematicsData);
    }

    private void invalidTelematicsDataValidation(TelematicsData telematicsData) {
        assertThrows(TelematicsValidationException.class, () -> telematicsService.saveTelematicsData(telematicsData));
        List<TelematicsData> storedData = telematicsService.getTelematicsData(telematicsData.getVehicleId());
        Assertions.assertNull(storedData);
    }

    @Test
    void testInvalidTelematicsDataEntry() {
        TelematicsData telematicsData = createSampleTelematicsData();
        telematicsData.setFuelPercent(null);
        invalidTelematicsDataValidation(telematicsData);
    }

    @Test
    void testNullTelematicsDataEntry() {
        invalidTelematicsDataValidation(new TelematicsData());
    }

    private void insertAndValidateTelematicsData(TelematicsData telematicsData) {
        telematicsService.saveTelematicsData(telematicsData);
        List<TelematicsData> storedData = telematicsService.getTelematicsData(telematicsData.getVehicleId());
        Assertions.assertNotNull(storedData);
        Assertions.assertEquals(telematicsData, storedData.get(0));
    }
}
