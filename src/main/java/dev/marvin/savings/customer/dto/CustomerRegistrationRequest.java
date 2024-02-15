package dev.marvin.savings.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRegistrationRequest(

        @NotBlank(message = "name is required")
        String name,

        @Email(message = "email is invalid")
        @NotBlank(message = "email is required")
        String email,

        @NotBlank(message = "password is required")
        String password) {
}
