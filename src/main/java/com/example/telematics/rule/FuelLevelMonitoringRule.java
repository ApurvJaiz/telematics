package com.example.telematics.rule;

import com.example.telematics.model.TelematicsData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FuelLevelMonitoringRule implements MonitoringRule {

    private double lowFuelLimit;

    public FuelLevelMonitoringRule(double lowFuelLimit) {
        this.lowFuelLimit = lowFuelLimit;
    }

    @Override
    public boolean isRuleTriggered(TelematicsData telematicsData) {
        return telematicsData.getFuelPercent() < lowFuelLimit;
    }

    @Override
    public void executeAction(TelematicsData telematicsData) {
        // Example: Send a notification about low fuel
        log.warn("Notification: Vehicle " + telematicsData.getVehicleId() +
                " is low on fuel. Fuel: " + telematicsData.getFuelPercent() + "% ");
    }
}
