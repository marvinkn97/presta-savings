package dev.marvin.savings.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Customer Registration Request Model")
public record CustomerRegistrationRequest(
        @NotBlank(message = "customer name is required")
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
