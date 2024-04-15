package dev.marvin.savings.appuser;

import java.time.LocalDateTime;

public record UserResponse(
        Integer id,
        String userName,
        LocalDateTime joinDate,
        boolean isActive,
        boolean isNotLocked,
        String role
) {
}
