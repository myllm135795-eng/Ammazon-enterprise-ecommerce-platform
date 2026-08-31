package com.ammazon.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when validation fails.
 */
public class ValidationException extends AmmazonException {
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST.value());
    }
}