package dev.marvin.savings.customer;

import java.time.LocalDateTime;

public record CustomerRegistrationResponse(LocalDateTime timestamp, String status, String message) {
}
