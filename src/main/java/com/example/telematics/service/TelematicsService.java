package com.example.telematics.service;

import com.example.telematics.exception.TelematicsValidationException;
import com.example.telematics.model.TelematicsData;
import com.example.telematics.repository.TelematicsDataRepository;
import org.apache.commons.lang3.StringUtils;
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
    public TelematicsDataRepository telematicsDataRepository;

    public void saveTelematicsData(TelematicsData telematicsData) {
        validateTelematicsData(telematicsData);
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

    public List<TelematicsData>  getTelematicsData(String vehicleId) {
        return telematicsDataRepository.getTelematicsData(vehicleId);

    }
    private void validateTelematicsData(TelematicsData telematicsData) {
        if (telematicsData == null) {
            throw new TelematicsValidationException("TelematicsData is null");
        }

        if (StringUtils.isEmpty(telematicsData.getVehicleId())) {
            throw new TelematicsValidationException("VehicleId is empty");
        }

        if (telematicsData.getLatitude() == null || telematicsData.getLongitude() == null) {
            throw new TelematicsValidationException("Latitude or Longitude is null");
        }

        if (telematicsData.getFuelPercent() == null) {
            throw new TelematicsValidationException("FuelPercent is null");
        }

        if (telematicsData.getSpeed() == null) {
            throw new TelematicsValidationException("Speed is null");
        }
    }
}