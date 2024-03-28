package dev.marvin.savings.model.dto;

import dev.marvin.savings.appuser.UserResponse;
import lombok.Builder;

import java.util.List;

public record CustomerResponse(
        UserResponse userResponse,
        String memberNumber,
        String mobile,
        Integer governmentId,
        String profileImageId,
        List<SavingsAccountResponse> accounts) {
}
