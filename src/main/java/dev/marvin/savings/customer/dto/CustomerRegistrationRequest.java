package dev.marvin.savings.customer.dto;

public record CustomerRegistrationRequest(String name, String email, String mobile, String governmentId) {
}
