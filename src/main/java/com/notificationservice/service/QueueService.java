package com.notificationservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.notificationservice.entity.Notification;
import com.notificationservice.enums.Priority;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueService {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;


    /**
     * Enqueues a notification ID to the appropriate Redis list based on priority.
     * @param notification the persisted notification
     * @param priority the priority level
     */
    public void enqueue(Notification notification, Priority priority){
        String queueName = priority.getQueueName();
        // we will push a simple json object with the notification Id 
        Map<String,String> message = new HashMap<>();
        message.put("notificationId",notification.getId());

        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            redisTemplate.opsForList().leftPush(queueName, jsonMessage);
            log.info("Enqueued notification {} to {} queue", notification.getId(), queueName);
        } catch (Exception e) {
             log.error("Failed to serialize notification ID for queue: {}", notification.getId(), e);
            throw new RuntimeException("Serialization error while enqueuing", e);
        }
      }

       /**
     * Overloaded method: determine priority from notification metadata (for now default to HIGH).
     */
     public void enqueue(Notification notification) {
        // Later we can implement logic to set priority based on fields; for now default HIGH
        enqueue(notification, Priority.HIGH);
    }   
}
