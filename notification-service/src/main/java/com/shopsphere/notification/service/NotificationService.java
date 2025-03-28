package com.shopsphere.notification.service;

import java.util.List;
import java.util.Optional;

import com.shopsphere.notification.dto.NotificationDTO;

public interface NotificationService {
    NotificationDTO createNotification(NotificationDTO notificationDTO);
    NotificationDTO updateNotification(String id, NotificationDTO notificationDTO);
    void deleteNotification(String id);
    Optional<NotificationDTO> getNotificationById(String id);
    List<NotificationDTO> getNotificationsByUserId(String userId);
    List<NotificationDTO> getUnreadNotificationsByUserId(String userId);
    NotificationDTO markAsRead(String id);
    NotificationDTO markAllAsRead(String userId);
    void sendNotification(NotificationDTO notificationDTO);
    void processPendingNotifications();
    void retryFailedNotifications();
} 