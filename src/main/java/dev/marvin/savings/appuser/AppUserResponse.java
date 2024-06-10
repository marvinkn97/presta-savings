package dev.marvin.savings.appuser;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppUserResponse(
        Integer id,
        String username,
        boolean isEnabled,
        boolean isLocked,
        String role,
        LocalDateTime createdAt
) {
}
