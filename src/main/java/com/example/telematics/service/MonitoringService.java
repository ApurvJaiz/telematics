package com.example.telematics.service;
import com.example.telematics.model.TelematicsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.telematics.rule.MonitoringRule;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private TelematicsService telematicsService;

    // List to store monitoring rules
    private List<MonitoringRule> monitoringRules = new ArrayList<>();

    // Method to add a monitoring rule
    public void addMonitoringRule(MonitoringRule rule) {
        monitoringRules.add(rule);
    }

    // Method to monitor telematics data and execute actions based on rules
    public void monitorTelematicsData() {
        // Retrieve telematics data from the TelematicsService
        List<TelematicsData> telematicsDataList = telematicsService.getAllTelematicsData();

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
