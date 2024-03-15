package dev.marvin.savings.model.dto;

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
