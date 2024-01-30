package com.example.telematics.service;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.rule.MonitoringRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class MonitoringService {

    @Autowired
    private QueryService queryService;

    @Value("${monitoring.check.frequency}")
    private String monitoringFrequency;

    // List to store monitoring rules
    private final List<MonitoringRule> monitoringRules = new ArrayList<>();

    @Scheduled(fixedDelayString = "${monitoring.check.frequency}")
    public void performMonitoring() {
       monitorTelematicsData();
    }

    // Method to add a monitoring rule
    public void addMonitoringRule(MonitoringRule rule) {
        monitoringRules.add(rule);
    }

    // Method to monitor telematics data and execute actions based on rules
    public void monitorTelematicsData() {

        LocalDateTime currentTime = LocalDateTime.now();
        int delayFrequencyInSeconds = Integer.parseInt(monitoringFrequency)/1000;
        LocalDateTime startTime = currentTime.minusSeconds(delayFrequencyInSeconds);

        List<TelematicsData> telematicsDataList = queryService.findByTimestampBetween(startTime, currentTime);

        log.debug("Monitor start time: {} -  end time {}", startTime, currentTime);
        // Apply monitoring rules to the telematics data
        for (TelematicsData telematicsData : telematicsDataList) {
            for (MonitoringRule rule : monitoringRules) {
                if (rule.isRuleTriggered(telematicsData)) {
                    // Execute the action associated with the rule
                    rule.executeAction(telematicsData);
                }
            }
        }
    }
}
