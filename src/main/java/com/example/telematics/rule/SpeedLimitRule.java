package com.example.telematics.rule;

import com.example.telematics.model.TelematicsData;

public class SpeedLimitRule implements MonitoringRule {

    private double speedLimit;

    public SpeedLimitRule(double speedLimit) {
        this.speedLimit = speedLimit;
    }

    @Override
    public boolean isRuleTriggered(TelematicsData telematicsData) {
        return telematicsData.getSpeed() > speedLimit;
    }

    @Override
    public void executeAction(TelematicsData telematicsData) {
        // Example: Send a notification about speeding
        System.out.println("Notification: Vehicle " + telematicsData.getVehicleId() +
                " is exceeding the speed limit. Speed: " + telematicsData.getSpeed() + " kmph");
    }
}
