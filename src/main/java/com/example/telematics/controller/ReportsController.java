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
    public ResponseEntity getDailyDistances(@RequestParam(name = "date") String date) {
        return ResponseEntity.ok(reportsService.generateDailyDistanceReport(LocalDate.parse(date)));
    }

    @GetMapping("/monthlyDistances")
    public ResponseEntity getTotalDistances(@RequestParam(name = "month") String month,
                                                                       @RequestParam(name = "year") String year) {
        return ResponseEntity.ok(reportsService.generateMonthlyDistanceReport(Integer.parseInt(month),
                Integer.parseInt(year)));
    }

    @GetMapping("/dailyOverspeedingVehicles")
    public ResponseEntity getDailyOverspeedingVehicles(@RequestParam(name = "date") String date) {
        return ResponseEntity.ok(reportsService.generateDailyOverspeedingReport(LocalDate.parse(date)));
    }

    @GetMapping("/monthlyOverspeedingVehicles")
    public ResponseEntity getMonthlyOverspeedingVehicles(@RequestParam(name = "month") String month,
                                                                                   @RequestParam(name = "year") String year) {
        return ResponseEntity.ok(reportsService.generateMonthlyOverspeedingReport(Integer.parseInt(month), Integer.parseInt(year)));
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
