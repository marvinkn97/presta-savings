package dev.marvin.savings.dto.customer;

import java.time.LocalDateTime;

public record CustomerRegistrationResponse(
        String message,
        LocalDateTime createdDate
) {}
