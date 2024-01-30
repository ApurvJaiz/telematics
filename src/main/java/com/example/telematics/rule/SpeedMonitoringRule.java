package com.example.telematics.rule;

import com.example.telematics.model.TelematicsData;
import com.example.telematics.notification.EmailNotification;
import com.example.telematics.notification.Notification;
import com.example.telematics.notification.SmsNotification;
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
        String message = "Notification: Vehicle " + telematicsData.getVehicleId() + " is exceeding the speed limit. Speed: " + telematicsData.getSpeed() + " kmph";
        //Sending a notification about speeding
        Notification emailNotification = new EmailNotification();
        Notification smsNotification = new SmsNotification();
        smsNotification.sendNotification(message);
        emailNotification.sendNotification(message);
    }
}
