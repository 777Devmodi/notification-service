package com.notificationservice.repository;

import com.notificationservice.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, String> {
    Optional<NotificationTemplate> findByName(String name);
}