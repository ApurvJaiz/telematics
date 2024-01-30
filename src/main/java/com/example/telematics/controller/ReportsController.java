package com.example.telematics.controller;

import com.example.telematics.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping("/dailyDistances")
    public ResponseEntity getDailyDistances(@RequestParam(name = "date") String date) {
        return ResponseEntity.ok(reportsService.generateDailyDistanceReport(date));
    }

    @GetMapping("/monthlyDistances")
    public ResponseEntity getTotalDistances(@RequestParam(name = "month") String month,
                                                                       @RequestParam(name = "year") String year) {
        return ResponseEntity.ok(reportsService.generateMonthlyDistanceReport(month, year));
    }

    @GetMapping("/dailyOverspeedingVehicles")
    public ResponseEntity getDailyOverspeedingVehicles(@RequestParam(name = "date") String date) {
        return ResponseEntity.ok(reportsService.generateDailyOverspeedingReport(date));
    }

    @GetMapping("/monthlyOverspeedingVehicles")
    public ResponseEntity getMonthlyOverspeedingVehicles(@RequestParam(name = "month") String month,
                                                                                   @RequestParam(name = "year") String year) {
        return ResponseEntity.ok(reportsService.generateMonthlyOverspeedingReport(month, year));
    }
//    @GetMapping("/totalDistances/{vehicleId}")
//    public ResponseEntity<TotalDistanceReport> getVehicleTotalDistances(@PathVariable String vehicleId) {
//        return ResponseEntity.ok(new TotalDistanceReport());
//    }
//
//    @GetMapping("/overspeedingVehicles/{vehicleId}")
//    public ResponseEntity<OverSpeedingReport> getVehicleOverspeedingReport(@PathVariable String vehicleId) {
//        return ResponseEntity.ok(new OverSpeedingReport());
//    }
}
