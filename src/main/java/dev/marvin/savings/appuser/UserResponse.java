package dev.marvin.savings.appuser;

public record UserResponse(String name, String email, Long joinDate, boolean isActive, String role) {
}
