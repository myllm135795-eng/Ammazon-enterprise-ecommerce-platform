package com.ammazon.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when authorization fails.
 */
public class AuthorizationException extends AmmazonException {
    public AuthorizationException(String message) {
        super(message, "AUTHORIZATION_FAILED", HttpStatus.FORBIDDEN.value());
    }
}