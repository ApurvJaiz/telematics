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

//    public double calculateDistance(TelematicsData telematicsData) {
//        // Haversine formula to calculate distance between two points on Earth
//        double earthRadius = 6371; // Earth radius in kilometers
//
//        double latitude1Radians = Math.toRadians(this.latitude);
//        double longitude1Radians = Math.toRadians(this.longitude);
//        double latitude2Radians = Math.toRadians(telematicsData.latitude);
//        double longitude2Radians = Math.toRadians(telematicsData.longitude);
//
//        // Intermediate calculations for Haversine formula
//        double haversineHalfDeltaLat = Math.sin((latitude2Radians - latitude1Radians) / 2);
//        double haversineHalfDeltaLon = Math.sin((longitude2Radians - longitude1Radians) / 2);
//
//        // Intermediate calculation for central angle
//        double aux = haversineHalfDeltaLat * haversineHalfDeltaLat +
//                Math.cos(latitude1Radians) * Math.cos(latitude2Radians) * haversineHalfDeltaLon * haversineHalfDeltaLon;
//
//        // Central angle (angular separation between points)
//        double centralAngle = 2 * Math.atan2(Math.sqrt(aux), Math.sqrt(1 - aux));
//
//        return earthRadius * centralAngle;
//    }


}
