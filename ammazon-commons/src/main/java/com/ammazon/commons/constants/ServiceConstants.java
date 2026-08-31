package com.ammazon.commons.constants;

/**
 * Service-wide constants.
 */
public final class ServiceConstants {
    private ServiceConstants() {
    }

    // API Versions
    public static final String API_VERSION_V1 = "v1";
    public static final String API_PREFIX = "/api/" + API_VERSION_V1;

    // Common Headers
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    public static final String TRACE_ID_HEADER = "X-Trace-ID";
    public static final String USER_ID_HEADER = "X-User-ID";
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // Timeouts
    public static final long HTTP_TIMEOUT_SECONDS = 30;
    public static final long SERVICE_CALL_TIMEOUT_SECONDS = 15;

    // Cache
    public static final String CACHE_PRODUCTS = "products";
    public static final String CACHE_USERS = "users";
    public static final String CACHE_CART = "cart";
    public static final long CACHE_TTL_MINUTES = 30;

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
}