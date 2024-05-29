package dev.marvin.savings.appuser.customer;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomerResponse(
        String username,
        String memberNumber,
        String email,
        boolean emailConfirmed,
        String mobileNumber,
        boolean mobileConfirmed,
        String governmentId,
        String kraPin,
        LocalDateTime createdAt
) {
}
