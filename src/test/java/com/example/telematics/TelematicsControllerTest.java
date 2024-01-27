package com.example.telematics;

import com.example.telematics.controller.ReportsController;
import com.example.telematics.controller.TelematicsController;
import com.example.telematics.model.TelematicsData;
import com.example.telematics.service.TelematicsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TelematicsControllerTest extends MockServiceApplicationTests {

    private MockMvc mockMvc;

    @Mock
    private TelematicsService telematicsService;

    @InjectMocks
    private TelematicsController telematicsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(telematicsController).build();
    }

    @Test
    void testReceiveTelematicsData() throws Exception {
        TelematicsData telematicsData = new TelematicsData();
        telematicsData.setVehicleId("123");
        telematicsData.setLatitude(37.7749);
        telematicsData.setLongitude(-122.4194);
        telematicsData.setFuelPercent(75.0);
        telematicsData.setSpeed(60.0);

        mockMvc.perform(post("/telematics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(telematicsData)))
                .andExpect(status().isOk());

        verify(telematicsService).saveTelematicsData(any(TelematicsData.class));
    }
}
