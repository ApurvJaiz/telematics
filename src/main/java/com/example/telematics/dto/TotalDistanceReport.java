package com.example.telematics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalDistanceReport {

    private String vehicleId;
    private Double distance;

    public TotalDistanceReport(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
