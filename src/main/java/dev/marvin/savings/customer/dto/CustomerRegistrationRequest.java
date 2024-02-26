package dev.marvin.savings.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CustomerRegistrationRequest(
        @NotBlank(message = "name is required")
        @Min(value = 3, message = "Minimum length is 3")
        String name,

        @Email(message = "email is invalid")
        @NotBlank(message = "email is required")
        String email,

        @NotBlank(message = "password is required")
        String password) {
}
