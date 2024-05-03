package dev.marvin.savings.appuser.customer;

public record CustomerResponse(
        String username,
        boolean isNotLocked,
        boolean isEnabled,
        String memberNumber,
        String email,
        String emailConfirmed,
        String mobileNumber,
        String mobileConfirmed,
        Integer governmentId,
        String kraPin,
        String profileImageId
) {
}
