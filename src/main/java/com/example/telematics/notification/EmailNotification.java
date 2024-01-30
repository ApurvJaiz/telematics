package com.example.telematics.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailNotification implements Notification {
    @Override
    public void sendNotification(String message) {
        // Implementation to send email notification
        log.info("Sending email Notification: "+message);
    }
}
