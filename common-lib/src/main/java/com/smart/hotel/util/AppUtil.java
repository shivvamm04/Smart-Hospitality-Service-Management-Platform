package com.smart.hotel.util;

import java.time.Instant;
import java.util.UUID;

public final class AppUtil {

    // Private constructor to prevent instantiation
    private AppUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Generate random UUID
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    // Get current timestamp
    public static Instant currentTimestamp() {
        return Instant.now();
    }

    // Check if string is null or blank
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isBlank();
    }
}

