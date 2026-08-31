package com.ammazon.notification.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Email notification event.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationEvent {
    private String eventId;
    private String to;
    private String subject;
    private String body;
    private String templateName;
    private Object templateData;
    private long createdAt;
}