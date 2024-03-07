package dev.marvin.savings.dto.customer;

import java.time.LocalDateTime;

public record CustomerRegistrationResponse(
        String memberNumber,
        String name,
        String email,
        LocalDateTime createdDate
) {
}
