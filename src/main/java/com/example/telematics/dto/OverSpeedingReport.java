package com.example.telematics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverSpeedingReport {

    private String vehicleId;
    private int count;
    private List<OverSpeedingDetails> details;
}
