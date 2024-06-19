package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.savingsaccount.SavingsAccountResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CustomerResponse(
        String username,
        String memberNumber,
        String name,
        String email,
        boolean emailConfirmed,
        String mobileNumber,
        boolean mobileConfirmed,
        String governmentId,
        String kraPin,
        LocalDateTime createdAt,
        boolean isDeleted,
        List<SavingsAccountResponse> accounts
) {
}
