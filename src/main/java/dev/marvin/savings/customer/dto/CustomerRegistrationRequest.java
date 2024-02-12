package dev.marvin.savings.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRegistrationRequest(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String password) {
}
