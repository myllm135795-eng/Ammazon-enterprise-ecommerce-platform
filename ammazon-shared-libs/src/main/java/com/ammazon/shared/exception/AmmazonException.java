package com.ammazon.shared.exception;

/**
 * Base exception class for Ammazon application.
 */
public class AmmazonException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String errorCode;
    private final int httpStatus;

    public AmmazonException(String message, String errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public AmmazonException(String message, String errorCode, int httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}