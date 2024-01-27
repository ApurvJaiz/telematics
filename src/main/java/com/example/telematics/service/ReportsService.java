package com.example.telematics.service;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.repository.TelematicsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReportsService {

    @Value("${telematics.speed.limit ? : '80'}")
    private String speedLimit;

    @Autowired
    private TelematicsDataRepository telematicsDataRepository;

    public void generateDailyDistanceReport(LocalDate inputDate) {
        List<TelematicsData> telematicsDataList = telematicsDataRepository.getAllTelematicsData();
        List<TelematicsData> filteredData = telematicsDataList.stream()
                .filter(data -> data.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(inputDate))
                .collect(Collectors.toList());
        generateDistanceReport(inputDate+"_daily_distance_report.csv", filteredData);
    }


    public void generateMonthlyDistanceReport(int month, int year) {
        List<TelematicsData> telematicsDataList = telematicsDataRepository.getAllTelematicsData();
        List<TelematicsData> filteredData = getTelematicsData(month, year, telematicsDataList);
        generateDistanceReport(month+"_"+year+"_monthly_distance_report.csv", filteredData);
    }

    public void generateDailyOverspeedingReport(LocalDate inputDate) {
        List<TelematicsData> telematicsDataList = telematicsDataRepository.getAllTelematicsData();
        List<TelematicsData> filteredData = getTelematicsData(inputDate, telematicsDataList);
        generateOverspeedingReport(inputDate+"_1d_overspeeding_report.csv", filteredData);
    }

    public void generateMonthlyOverspeedingReport(int month, int year) {
        List<TelematicsData> telematicsDataList = telematicsDataRepository.getAllTelematicsData();
        List<TelematicsData> filteredData = getTelematicsData(month, year, telematicsDataList);
        generateOverspeedingReport(month+"_"+year+"_overspeeding_report.csv", filteredData);
    }

    public List<TelematicsData> getTelematicsData(LocalDate inputDate, List<TelematicsData> telematicsDataList) {
        return telematicsDataList.stream()
                .filter(data -> data.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(inputDate))
                .collect(Collectors.toList());
    }

    public List<TelematicsData> getTelematicsData(int month, int year, List<TelematicsData> telematicsDataList) {
        return telematicsDataList.stream()
                .filter(data -> data.getTimestamp().getYear() == year && data.getTimestamp().getMonth() == month)
                .collect(Collectors.toList());
    }

    public void generateDistanceReport(String fileName, List<TelematicsData> telematicsDataList) {

        // Mapping telematics data to vehicleId and totalDistanceCovered
        Map<String, Double> distanceByVehicle = calculateTotalDistance(telematicsDataList);

        try (Writer writer = new FileWriter(fileName)) {
            writer.write("VehicleId,TotalDistanceCovered\n");
            distanceByVehicle.forEach((vehicleId, totalDistanceCovered) -> {
                try {
                    writer.write(vehicleId + "," + totalDistanceCovered + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateOverspeedingReport(String fileName, List<TelematicsData> telematicsDataList) {
        List<String> overspeedingVehicle = getOverspeedingVehicle(telematicsDataList);
        try (Writer writer = new FileWriter(fileName)) {
            writer.write("VehicleId\n"); // CSV header
            overspeedingVehicle.forEach(vehicleId -> {
                try {
                    writer.write(vehicleId + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.write("Total = "+overspeedingVehicle.size()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getOverspeedingVehicle(List<TelematicsData> telematicsDataList) {
        return telematicsDataList.stream()
                .filter(telematicsData -> telematicsData.getSpeed() != null
                        && telematicsData.getSpeed() > Double.parseDouble(speedLimit))
                .map(TelematicsData::getVehicleId)
                .distinct()
                .collect(Collectors.toList());
    }

    public Map<String, Double> calculateTotalDistance(List<TelematicsData> telematicsDataList) {
        Map<String, Double> totalDistanceMap = new HashMap<>();

        for (int i = 0; i < telematicsDataList.size() - 1; i++) {
            TelematicsData currentData = telematicsDataList.get(i);
            TelematicsData nextData = telematicsDataList.get(i + 1);

            String vehicleId = currentData.getVehicleId();
            double distance = calculateDistance(
                    currentData.getLatitude(), currentData.getLongitude(),
                    nextData.getLatitude(), nextData.getLongitude()
            );

            // Update total distance for the corresponding vehicleId
            totalDistanceMap.put(vehicleId, totalDistanceMap.getOrDefault(vehicleId, 0.0) + distance);
        }

        return totalDistanceMap;
    }

    public double calculateDistance(Double latitude1Radians, Double longitude1Radians, Double latitude2Radians, Double longitude2Radians) {
        // Haversine formula to calculate distance between two points on Earth
        double earthRadius = 6371; // Earth radius in kilometers
        // Intermediate calculations for Haversine formula
        double haversineHalfDeltaLat = Math.sin((latitude2Radians - latitude1Radians) / 2);
        double haversineHalfDeltaLon = Math.sin((longitude2Radians - longitude1Radians) / 2);

        // Intermediate calculation for central angle
        double aux = haversineHalfDeltaLat * haversineHalfDeltaLat +
                Math.cos(latitude1Radians) * Math.cos(latitude2Radians) * haversineHalfDeltaLon * haversineHalfDeltaLon;

        // Central angle (angular separation between points)
        double centralAngle = 2 * Math.atan2(Math.sqrt(aux), Math.sqrt(1 - aux));

        return earthRadius * centralAngle;
    }

}
