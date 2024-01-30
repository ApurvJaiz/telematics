package com.example.telematics.controller;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.service.MonitoringService;
import com.example.telematics.service.TelematicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telematics")
public class TelematicsController {

    @Autowired
    private TelematicsService telematicsService;

    @Autowired
    private MonitoringService monitoringService;

    @PostMapping
    public ResponseEntity<String> receiveTelematicsData(@RequestBody TelematicsData telematicsData) {
        telematicsService.saveTelematicsData(telematicsData);
        return new ResponseEntity<>("Telematics data received successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllTelematicsData() {
        return ResponseEntity.ok(telematicsService.getAllTelematicsData());
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity getTelematicsData(@PathVariable String vehicleId) {
        return ResponseEntity.ok(telematicsService.getTelematicsData(vehicleId));
    }

}
