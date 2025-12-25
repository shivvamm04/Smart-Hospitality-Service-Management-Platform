package com.smart.hotel.exception;

public enum ErrorCode {

    // General
    INTERNAL_SERVER_ERROR("HM-500", "Internal server error"),
    INVALID_REQUEST("HM-400", "Invalid request"),

    // Auth
    UNAUTHORIZED("HM-401", "Unauthorized access"),
    FORBIDDEN("HM-403", "Access denied"),

    // Business
    RESOURCE_NOT_FOUND("HM-404", "Resource not found"),
    PAYMENT_FAILED("HM-410", "Payment failed");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}
