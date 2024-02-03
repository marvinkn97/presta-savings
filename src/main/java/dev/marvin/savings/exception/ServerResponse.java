package dev.marvin.savings.exception;

public record ServerResponse(
        String statusCode,
        String message
) {
}
