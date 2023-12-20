package dev.marvin.savings.customer.dto;

public record CustomerVO(
        String name,
        String email,
        String mobile,
        String governmentId,
        String memberNumber
) {
}
