package com.notificationservice.enums;

import lombok.Getter;

@Getter
public enum Priority {
    CRITICAL("critical_notifications"),
    HIGH("high_notifications"),
    LOW("low_notifications");

    private final String queueName;

    Priority(String queueName) {
        this.queueName = queueName;
    }
}