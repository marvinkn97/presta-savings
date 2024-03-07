package dev.marvin.savings.dto.customer;

import dev.marvin.savings.dto.savingsaccount.SavingsAccountResponse;
import lombok.Builder;

import java.util.List;

@Builder  //remove this builder
public record CustomerResponse(
        String memberNumber,
        String name,
        String email,
        String mobile,
        Integer governmentId,
        Long createdDate,
        Long updatedDate,
        String profileImageId,
        List<SavingsAccountResponse> accounts) {
}
