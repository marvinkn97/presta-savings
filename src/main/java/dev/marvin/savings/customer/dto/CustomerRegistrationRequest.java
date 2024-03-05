package dev.marvin.savings.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Customer Registration Request Model")
public record CustomerRegistrationRequest(
        @NotBlank(message = "customer name is required")
        @Min(value = 6, message = "Minimum length is 6 characters")
        @Max(value = 50, message = "Maximum length is 50 characters")
        @Schema(name = "name", defaultValue = "Foo Bar")
        String name,

        @Email(message = "customer email is invalid")
        @NotBlank(message = "email is required")
        @Schema(name = "email", defaultValue = "foo@example.com")
        String email,

        @NotBlank(message = "password is required")
        @Schema(name = "password", defaultValue = "password")
        String password) {
}
