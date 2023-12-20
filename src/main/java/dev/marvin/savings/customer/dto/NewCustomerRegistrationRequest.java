package dev.marvin.savings.customer.dto;

public record NewCustomerRegistrationRequest(
        String name,
        String email,
        String mobile,
        String governmentId
) {
}
