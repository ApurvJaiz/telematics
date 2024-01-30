package com.example.telematics.controller;

import com.example.telematics.service.MonitoringService;
import com.example.telematics.service.QueryService;
import com.example.telematics.service.TelematicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telematics")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private MonitoringService monitoringService;

    @GetMapping
    public ResponseEntity getAllTelematicsData() {
        return ResponseEntity.ok(queryService.getAllTelematicsData());
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity getTelematicsData(@PathVariable String vehicleId) {
        return ResponseEntity.ok(queryService.getTelematicsData(vehicleId));
    }

}
