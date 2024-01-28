package com.example.telematics.service;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.repository.TelematicsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TelematicsService {

    @Autowired
    private TelematicsDataRepository telematicsDataRepository;

    public void saveTelematicsData(TelematicsData telematicsData) {
        telematicsDataRepository.saveTelematicsData(telematicsData.getVehicleId(), telematicsData);
    }

    public List<TelematicsData> getAllTelematicsData() {
        return telematicsDataRepository.getAllTelematicsData();
    }

    public List<TelematicsData> findByTimestampBetween(LocalDateTime startTime, LocalDateTime currentTime) {
        Date startDate = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        return telematicsDataRepository.getAllTelematicsData().stream()
                .filter(data -> data.getTimestamp().after(startDate) && data.getTimestamp().before(endDate))
                .collect(Collectors.toList());
    }
}