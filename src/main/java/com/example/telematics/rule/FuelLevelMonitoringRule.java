package com.example.telematics.rule;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.notification.EmailNotification;
import com.example.telematics.notification.Notification;
import com.example.telematics.notification.SmsNotification;
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
        String message = "Notification: Vehicle " + telematicsData.getVehicleId() + " is low on fuel. Fuel: " + telematicsData.getFuelPercent() + "% ";
        //Send a notification about low fuel
        Notification emailNotification = new EmailNotification();
        Notification smsNotification = new SmsNotification();
        smsNotification.sendNotification(message);
        emailNotification.sendNotification(message);
    }
}
