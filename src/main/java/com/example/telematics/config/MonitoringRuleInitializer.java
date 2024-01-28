package com.example.telematics.config;

import com.example.telematics.rule.FuelLevelMonitoringRule;
import com.example.telematics.rule.MonitoringRule;
import com.example.telematics.rule.SpeedMonitoringRule;
import com.example.telematics.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MonitoringRuleInitializer {

    @Value("${telematics.speed.limit}")
    private String speedLimit;

    @Value("${telematics.low.fuel.limit}")
    private String lowFuelLimit;

    private final MonitoringService monitoringService;

    @Autowired
    public MonitoringRuleInitializer(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @PostConstruct
    public void initializeMonitoringRules() {
        // Add monitoring rules during application initialization
        addSpeedMonitoringRule();
        addFuelLevelMonitoringRule();
        // Add more rules as needed
    }

    private void addSpeedMonitoringRule() {
        MonitoringRule speedRule = new SpeedMonitoringRule(Double.parseDouble(speedLimit));
        monitoringService.addMonitoringRule(speedRule);
    }

    private void addFuelLevelMonitoringRule() {
        MonitoringRule fuelRule = new FuelLevelMonitoringRule(Double.parseDouble(lowFuelLimit));
        monitoringService.addMonitoringRule(fuelRule);
    }
}

