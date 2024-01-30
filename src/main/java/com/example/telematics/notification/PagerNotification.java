package com.example.telematics.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PagerNotification implements Notification {
    @Override
    public void sendNotification(String message) {
        // Implementation to send pager notification
        log.info("Sending Pager Notification: "+message);
    }
}
