package com.example.telematics.service;

import com.example.telematics.exception.TelematicsApplicationException;
import com.example.telematics.exception.TelematicsValidationException;
import com.example.telematics.model.TelematicsData;
import com.example.telematics.repository.TelematicsDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ReportsService {

    @Value("${telematics.speed.limit}")
    private String speedLimit;

    @Autowired
    private TelematicsDataRepository telematicsDataRepository;

    public Map<String, Double> generateDailyDistanceReport(String inputDate) {
        List<TelematicsData> telematicsDataList = telematicsDataRepository.getAllTelematicsData();
        List<TelematicsData> filteredData = getTelematicsData(validateDateInput(inputDate), telematicsDataList);
        return generateDistanceReport(inputDate+"_daily_distance_report.csv", filteredData);
    }


    public Map<String, Double> generateMonthlyDistanceReport(String month, String year) {
        validateMonthInput(month, year);
        List<TelematicsData> telematicsDataList = telematicsDataRepository.getAllTelematicsData();
        List<TelematicsData> filteredData = getTelematicsData(Integer.parseInt(month), Integer.parseInt(year), telematicsDataList);
        return generateDistanceReport(month+"_"+year+"_monthly_distance_report.csv", filteredData);
    }

    public List<String> generateDailyOverspeedingReport(String inputDate) {
        List<TelematicsData> telematicsDataList = telematicsDataRepository.getAllTelematicsData();
        List<TelematicsData> filteredData = getTelematicsData(validateDateInput(inputDate), telematicsDataList);
        return generateOverspeedingReport(inputDate+"_daily_overspeeding_report.csv", filteredData);
    }

    public List<String> generateMonthlyOverspeedingReport(String month, String year) {
        validateMonthInput(month, year);
        List<TelematicsData> telematicsDataList = telematicsDataRepository.getAllTelematicsData();
        List<TelematicsData> filteredData = getTelematicsData(Integer.parseInt(month), Integer.parseInt(year), telematicsDataList);
        return generateOverspeedingReport(month+"_"+year+"_monthly_overspeeding_report.csv", filteredData);
    }

    public List<TelematicsData> getTelematicsData(LocalDate inputDate, List<TelematicsData> telematicsDataList) {
        return telematicsDataList.stream()
                .filter(data -> data.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(inputDate))
                .collect(Collectors.toList());
    }

    public List<TelematicsData> getTelematicsData(int month, int year, List<TelematicsData> telematicsDataList) {
        return telematicsDataList.stream()
                .filter(data -> data.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear() == year)
                .filter(data -> data.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() == month)
                .collect(Collectors.toList());
    }

    public Map<String, Double> generateDistanceReport(String fileName, List<TelematicsData> telematicsDataList) {

        // Mapping telematics data to vehicleId and totalDistanceCovered
        Map<String, Double> distanceByVehicle = calculateTotalDistance(telematicsDataList);

        try (Writer writer = new FileWriter(fileName)) {
            writer.write("Vehicle ID,Total Distance Covered (KM)\n");
            distanceByVehicle.forEach((vehicleId, totalDistanceCovered) -> {
                try {
                    writer.write(vehicleId + "," + totalDistanceCovered + "\n");
                } catch (Exception e) {
                    throw new TelematicsApplicationException("Error while writing to CSV distance report", e);
                }
            });
        } catch (Exception e) {
            throw new TelematicsApplicationException("Error while generating distance report", e);
        }
        return distanceByVehicle;
    }

    public List<String> generateOverspeedingReport(String fileName, List<TelematicsData> telematicsDataList) {
        List<String> overspeedingVehicle = getOverspeedingVehicle(telematicsDataList);
        try (Writer writer = new FileWriter(fileName)) {
            writer.write("Vehicle ID\n"); // CSV header
            overspeedingVehicle.forEach(vehicleId -> {
                try {
                    writer.write(vehicleId + "\n");
                } catch (Exception e) {
                    throw new TelematicsApplicationException("Error while writing to CSV Over speeding report", e);
                }
            });
            writer.write("Total = "+overspeedingVehicle.size()+"\n");
        } catch (Exception e) {
            throw new TelematicsApplicationException("Error while generating over speeding report", e);
        }
        return overspeedingVehicle;
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

    private LocalDate validateDateInput(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            throw new TelematicsValidationException("Invalid date value: " + date);
        }
    }

    private void validateMonthInput(String month, String year) {
        try {
            int mm = Integer.parseInt(month);
            int yyyy = Integer.parseInt(year);
            if(mm < 1 || mm > 12) {
                throw new TelematicsValidationException("Invalid month: " + month);
            }
            if(yyyy < 1970 || yyyy > Integer.parseInt(String.valueOf(Year.now()))) {
                throw new TelematicsValidationException("Invalid year: " + year);
            }
        } catch (Exception e) {
            throw new TelematicsValidationException("Invalid month/year value: " + month + "/"+year);
        }
    }

}
