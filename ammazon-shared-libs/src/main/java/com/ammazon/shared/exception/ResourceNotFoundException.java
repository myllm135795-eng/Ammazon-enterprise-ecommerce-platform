package com.ammazon.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a resource is not found.
 */
public class ResourceNotFoundException extends AmmazonException {
    public ResourceNotFoundException(String resource, String identifier) {
        super(String.format("%s not found with id: %s", resource, identifier),
                "RESOURCE_NOT_FOUND",
                HttpStatus.NOT_FOUND.value());
    }

    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND.value());
    }
}