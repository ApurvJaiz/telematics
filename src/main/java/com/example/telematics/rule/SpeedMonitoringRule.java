package com.example.telematics.rule;

import com.example.telematics.model.TelematicsData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpeedMonitoringRule implements MonitoringRule {

    private double speedLimit;

    public SpeedMonitoringRule(double speedLimit) {
        this.speedLimit = speedLimit;
    }

    @Override
    public boolean isRuleTriggered(TelematicsData telematicsData) {
        return telematicsData.getSpeed() > speedLimit;
    }

    @Override
    public void executeAction(TelematicsData telematicsData) {
        // Example: Send a notification about speeding
        log.warn("Notification: Vehicle " + telematicsData.getVehicleId() +
                " is exceeding the speed limit. Speed: " + telematicsData.getSpeed() + " kmph");
    }
}
