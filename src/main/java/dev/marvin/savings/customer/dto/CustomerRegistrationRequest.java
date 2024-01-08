package dev.marvin.savings.customer.dto;

public record CustomerRegistrationRequest(
        String name, String email, String password, Integer mobile, Integer governmentId) {
}
