package dev.marvin.savings.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(LocalDateTime timestamp, Integer httpStatusCode, String reason, String message) {
}
