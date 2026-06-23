package com.notificationservice.dto;

import com.notificationservice.enums.NotificationStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private String id;
    private String recipient;
    private String subject;
    private String body;
    private NotificationStatus status;
    private LocalDateTime createdAt;
}