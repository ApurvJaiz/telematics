package com.example.telematics.service;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.repository.TelematicsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelematicsService {

    @Value("${telematics.ingestion.frequency}")
    private int ingestionFrequency;

    @Autowired
    private TelematicsDataRepository telematicsDataRepository;

    public void saveTelematicsData(TelematicsData telematicsData) {
        telematicsDataRepository.saveTelematicsData(telematicsData.getVehicleId(), telematicsData);
    }

    public List<TelematicsData> getAllTelematicsData() {
        return telematicsDataRepository.getAllTelematicsData();
    }
}