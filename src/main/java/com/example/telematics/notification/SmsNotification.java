package com.example.telematics.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsNotification implements Notification {
    @Override
    public void sendNotification(String message) {
        // Implementation to send SMS notification
        log.info("Sending SMS Notification: "+message);
    }
}
