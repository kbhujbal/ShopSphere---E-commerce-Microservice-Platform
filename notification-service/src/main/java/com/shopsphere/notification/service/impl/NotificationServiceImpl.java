package com.shopsphere.notification.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsphere.common.exception.ResourceNotFoundException;
import com.shopsphere.notification.dto.NotificationDTO;
import com.shopsphere.notification.mapper.NotificationMapper;
import com.shopsphere.notification.model.Notification;
import com.shopsphere.notification.repository.NotificationRepository;
import com.shopsphere.notification.service.NotificationService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender mailSender;

    @Override
    @Transactional
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        log.info("Creating new notification for user: {}", notificationDTO.getUserId());
        Notification notification = notificationMapper.toEntity(notificationDTO);
        notification.setStatus("PENDING");
        notification = notificationRepository.save(notification);
        return notificationMapper.toDTO(notification);
    }

    @Override
    @Transactional
    public NotificationDTO updateNotification(String id, NotificationDTO notificationDTO) {
        log.info("Updating notification with id: {}", id);
        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        
        Notification updatedNotification = notificationMapper.toEntity(notificationDTO);
        updatedNotification.setId(existingNotification.getId());
        updatedNotification = notificationRepository.save(updatedNotification);
        return notificationMapper.toDTO(updatedNotification);
    }

    @Override
    @Transactional
    public void deleteNotification(String id) {
        log.info("Deleting notification with id: {}", id);
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    @Override
    public Optional<NotificationDTO> getNotificationById(String id) {
        log.info("Getting notification with id: {}", id);
        return notificationRepository.findById(id)
                .map(notificationMapper::toDTO);
    }

    @Override
    public List<NotificationDTO> getNotificationsByUserId(String userId) {
        log.info("Getting notifications for user: {}", userId);
        return notificationRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toDTO)
                .toList();
    }

    @Override
    public List<NotificationDTO> getUnreadNotificationsByUserId(String userId) {
        log.info("Getting unread notifications for user: {}", userId);
        return notificationRepository.findByUserIdAndReadFalseAndDeletedFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public NotificationDTO markAsRead(String id) {
        log.info("Marking notification as read: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        
        notification.setRead(true);
        notification = notificationRepository.save(notification);
        return notificationMapper.toDTO(notification);
    }

    @Override
    @Transactional
    public NotificationDTO markAllAsRead(String userId) {
        log.info("Marking all notifications as read for user: {}", userId);
        List<Notification> notifications = notificationRepository.findByUserIdAndReadFalseAndDeletedFalseOrderByCreatedAtDesc(userId);
        notifications.forEach(notification -> notification.setRead(true));
        notifications = notificationRepository.saveAll(notifications);
        return notifications.isEmpty() ? null : notificationMapper.toDTO(notifications.get(0));
    }

    @Override
    @Transactional
    public void sendNotification(NotificationDTO notificationDTO) {
        log.info("Sending notification: {}", notificationDTO.getId());
        Notification notification = notificationMapper.toEntity(notificationDTO);
        
        try {
            switch (notification.getType()) {
                case "EMAIL":
                    sendEmail(notification);
                    break;
                case "SMS":
                    sendSMS(notification);
                    break;
                case "PUSH":
                    sendPushNotification(notification);
                    break;
                case "IN_APP":
                    // In-app notifications are handled by the frontend
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported notification type: " + notification.getType());
            }
            
            notification.setStatus("SENT");
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            log.error("Failed to send notification: {}", e.getMessage());
            notification.setStatus("FAILED");
            notification.setErrorMessage(e.getMessage());
        }
        
        notificationRepository.save(notification);
    }

    @Override
    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional
    public void processPendingNotifications() {
        log.info("Processing pending notifications");
        List<Notification> pendingNotifications = notificationRepository.findByStatusAndDeletedFalse("PENDING");
        pendingNotifications.forEach(notification -> sendNotification(notificationMapper.toDTO(notification)));
    }

    @Override
    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    @Transactional
    public void retryFailedNotifications() {
        log.info("Retrying failed notifications");
        List<Notification> failedNotifications = notificationRepository.findByStatusAndDeletedFalse("FAILED");
        failedNotifications.forEach(notification -> sendNotification(notificationMapper.toDTO(notification)));
    }

    private void sendEmail(Notification notification) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setTo(notification.getRecipient());
        helper.setSubject(notification.getTitle());
        helper.setText(notification.getContent(), true);
        
        mailSender.send(message);
    }

    private void sendSMS(Notification notification) {
        // TODO: Implement SMS sending logic
        // This would typically involve calling an SMS service provider's API
        log.info("Sending SMS to: {}", notification.getRecipient());
    }

    private void sendPushNotification(Notification notification) {
        // TODO: Implement push notification logic
        // This would typically involve calling a push notification service provider's API
        log.info("Sending push notification to: {}", notification.getRecipient());
    }
} 