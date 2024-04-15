package dev.marvin.savings.customer;

import java.time.LocalDateTime;

public record CustomerResponse(
        String memberNumber,
        String email,
        String mobileNumber,
        Integer governmentId,
        String kraPin,
        String profileImageId,
        String username,
        LocalDateTime joinDate,
        String role
) {
}
