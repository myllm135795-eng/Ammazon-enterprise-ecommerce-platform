package com.ammazon.commons.events;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base class for all domain events.
 * All events in the system should extend this class.
 */
public abstract class DomainEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eventId;
    private String correlationId;
    private LocalDateTime createdAt;
    private String source;

    protected DomainEvent() {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    protected DomainEvent(String correlationId, String source) {
        this();
        this.correlationId = correlationId;
        this.source = source;
    }

    public String getEventId() {
        return eventId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getSource() {
        return source;
    }

    public abstract String getEventType();
}