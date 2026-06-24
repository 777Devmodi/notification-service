package com.notificationservice.repository;

import com.notificationservice.entity.InAppNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InAppNotificationRepository extends JpaRepository<InAppNotification, String> {
    List<InAppNotification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(String userId);
    List<InAppNotification> findByUserIdOrderByCreatedAtDesc(String userId);
}