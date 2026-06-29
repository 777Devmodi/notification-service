package com.notificationservice.service;

import org.springframework.stereotype.Service;

import com.notificationservice.dto.NotificationRequest;
import com.notificationservice.dto.NotificationResponse;
import com.notificationservice.entity.Notification;
import com.notificationservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final QueueService queueService;

    public NotificationResponse createNotification(NotificationRequest request){
        Notification notification = Notification.builder()
                                    .recipient(request.getRecipient())
                                    .subject(request.getSubject())
                                    .body(request.getBody())
                                    .build(); // status and timestamps handled by @PrePersist
        Notification saved = notificationRepository.save(notification);

        // enqueue for async processing 
        queueService.enqueue(saved); // default priority

        return NotificationResponse.builder()
                .id(saved.getId())
                .recipient(saved.getRecipient())
                .subject(saved.getSubject())
                .body(saved.getBody())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
