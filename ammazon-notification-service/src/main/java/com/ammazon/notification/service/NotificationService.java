package com.ammazon.notification.service;

import com.ammazon.notification.event.EmailNotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Notification service for sending emails, SMS, and push notifications.
 */
@Slf4j
@Service
public class NotificationService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    /**
     * Listen to email notification events from Kafka.
     */
    @KafkaListener(topics = "email-notification", groupId = "notification-service")
    public void handleEmailNotification(EmailNotificationEvent event) {
        log.info("Processing email notification event: {}", event.getEventId());
        sendEmail(event);
    }

    /**
     * Send email.
     */
    public void sendEmail(EmailNotificationEvent event) {
        try {
            if (mailSender != null) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(event.getTo());
                message.setSubject(event.getSubject());
                message.setText(event.getBody());
                mailSender.send(message);
                log.info("Email sent to: {}", event.getTo());
            } else {
                log.info("Mail sender not configured. Email would be sent to: {}", event.getTo());
            }
        } catch (Exception e) {
            log.error("Error sending email to: {}", event.getTo(), e);
        }
    }

    /**
     * Listen to SMS notification events from Kafka.
     */
    @KafkaListener(topics = "sms-notification", groupId = "notification-service")
    public void handleSmsNotification(String event) {
        log.info("Processing SMS notification: {}", event);
        // In real scenario, call Twilio or similar SMS service
    }

    /**
     * Listen to push notification events from Kafka.
     */
    @KafkaListener(topics = "push-notification", groupId = "notification-service")
    public void handlePushNotification(String event) {
        log.info("Processing push notification: {}", event);
        // In real scenario, call Firebase Cloud Messaging or similar
    }
}