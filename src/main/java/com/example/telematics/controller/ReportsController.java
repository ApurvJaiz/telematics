package com.example.telematics.controller;

import com.example.telematics.dto.OverSpeedingReport;
import com.example.telematics.dto.TotalDistanceReport;
import com.example.telematics.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping("/dailyDistances")
    public ResponseEntity<List<TotalDistanceReport>> getDailyDistances(@RequestParam(name = "date") LocalDate date) {
        return ResponseEntity.ok(Collections.emptyList()); // Placeholder value, replace with actual logic
    }

    @GetMapping("/monthlyDistances")
    public ResponseEntity<List<TotalDistanceReport>> getTotalDistances(@RequestParam(name = "month") String month,
                                                                       @RequestParam(name = "year") String year) {
        return ResponseEntity.ok(Collections.emptyList()); // Placeholder value, replace with actual logic
    }

    @GetMapping("/dailyOverspeedingVehicles")
    public ResponseEntity<List<OverSpeedingReport>> getDailyOverspeedingVehicles(@RequestParam(name = "date") LocalDate date) {
        // Implement logic to fetch overspeeding reports for all vehicles
        return ResponseEntity.ok(Collections.emptyList()); // Placeholder value, replace with actual logic
    }

    @GetMapping("/monthlyOverspeedingVehicles")
    public ResponseEntity<List<OverSpeedingReport>> getMonthlyOverspeedingVehicles(@RequestParam(name = "month") String month,
                                                                                   @RequestParam(name = "year") String year) {
        // Implement logic to fetch overspeeding reports for all vehicles
        return ResponseEntity.ok(Collections.emptyList()); // Placeholder value, replace with actual logic
    }
//    @GetMapping("/totalDistances/{vehicleId}")
//    public ResponseEntity<TotalDistanceReport> getVehicleTotalDistances(@PathVariable String vehicleId) {
//        return ResponseEntity.ok(new TotalDistanceReport()); // Placeholder value, replace with actual logic
//    }

//    @GetMapping("/overspeedingVehicles/{vehicleId}")
//    public ResponseEntity<OverSpeedingReport> getVehicleOverspeedingReport(@PathVariable String vehicleId) {
//        // Implement logic to fetch overspeeding report for a specific vehicle
//        return ResponseEntity.ok(new OverSpeedingReport()); // Placeholder value, replace with actual logic
//    }
}
