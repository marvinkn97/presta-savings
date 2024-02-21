package dev.marvin.savings.customer.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String memberNumber,
        String name,
        String email,
        String mobile,
        Integer governmentId,
        Long createdDate
) {
}
