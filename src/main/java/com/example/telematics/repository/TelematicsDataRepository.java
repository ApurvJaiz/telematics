package com.example.telematics.repository;

import com.example.telematics.model.TelematicsData;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TelematicsDataRepository {
    private static TelematicsDataRepository instance;
    private Map<String, List<TelematicsData>> telematicsDataMap;
    private TelematicsDataRepository() {
        telematicsDataMap = new HashMap<>();
    }
    public static synchronized TelematicsDataRepository getInstance() {
        if(instance == null) {
            instance = new TelematicsDataRepository();
        }
        return instance;
    }

    public void saveTelematicsData(String vehicleId, TelematicsData telematicsData) {
        if(Objects.isNull(telematicsData.getTimestamp())) {
            telematicsData.setTimestamp(new Date(System.currentTimeMillis()));
        }
        telematicsDataMap.computeIfAbsent(vehicleId, key -> new ArrayList<>()).add(telematicsData);
    }

    public List<TelematicsData> getTelematicsData(String vehicleId) {
        return telematicsDataMap.get(vehicleId);
    }

    public List<TelematicsData> getAllTelematicsData() {
        return telematicsDataMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public void clearAllTelematicsData() {
        telematicsDataMap.clear();
    }

    public List<TelematicsData> findBySpeedGreaterThan(double speed) {
        return null;
    }
}
