package dev.marvin.savings.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRegistrationRequest(
        @NotBlank(message = "customer name is required")
        @Size(min = 6, message = "Minimum length is 6")
        String name,

        @Email(message = "customer email is invalid")
        @NotBlank(message = "email is required")
        String email,

        @NotBlank(message = "password is required")
        String password) {
}
