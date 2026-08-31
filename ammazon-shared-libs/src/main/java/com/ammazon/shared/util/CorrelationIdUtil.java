package com.ammazon.shared.util;

import org.slf4j.MDC;
import java.util.UUID;

/**
 * Utility class for handling correlation IDs and distributed tracing.
 */
public class CorrelationIdUtil {
    public static final String CORRELATION_ID = "correlationId";
    public static final String TRACE_ID = "traceId";
    public static final String USER_ID = "userId";

    public static String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    public static String getCorrelationId() {
        String correlationId = MDC.get(CORRELATION_ID);
        if (correlationId == null) {
            correlationId = generateCorrelationId();
            setCorrelationId(correlationId);
        }
        return correlationId;
    }

    public static void setCorrelationId(String correlationId) {
        MDC.put(CORRELATION_ID, correlationId);
    }

    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static String getUserId() {
        return MDC.get(USER_ID);
    }

    public static void setUserId(String userId) {
        MDC.put(USER_ID, userId);
    }

    public static void clear() {
        MDC.clear();
    }
}