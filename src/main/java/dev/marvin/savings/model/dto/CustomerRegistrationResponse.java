package dev.marvin.savings.model.dto;

import java.time.LocalDateTime;

public record CustomerRegistrationResponse(LocalDateTime timestamp, String status, String message) {
}
