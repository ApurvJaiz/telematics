package com.example.telematics.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TelematicsData {

    private String vehicleId;
    private Date timestamp;
    private Double latitude;
    private Double longitude;
    private Double fuelPercent;
    private Double speed;
}
