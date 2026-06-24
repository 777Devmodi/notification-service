package com.notificationservice.entity;

import com.notificationservice.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications", indexes = {
        @Index(name = "idx_notification_status", columnList = "status"),
        @Index(name = "idx_notification_recipient", columnList = "recipient"),
        @Index(name = "idx_notification_idempotency_key", columnList = "idempotency_key", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationStatus status;

    // Channel abstraction: EMAIL, IN_APP, SMS, etc.
    @Column(length = 20)
    private String channel;

    // Template relationship (optional)
    @Column(name = "template_id")
    private String templateId;  // UUID of NotificationTemplate

    // Idempotency key for duplicate prevention
    @Column(name = "idempotency_key", length = 100, unique = true)
    private String idempotencyKey;

    // Correlation ID for tracing across services
    @Column(name = "correlation_id", length = 100)
    private String correlationId;

    // Retry mechanism fields
    @Column(name = "retry_count", nullable = false)
    @Builder.Default
    private int retryCount = 0;

    @Column(name = "last_attempt_at")
    private LocalDateTime lastAttemptAt;

    @Column(name = "next_retry_at")
    private LocalDateTime nextRetryAt;

    @Column(name = "failure_reason", columnDefinition = "TEXT")
    private String failureReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = NotificationStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}