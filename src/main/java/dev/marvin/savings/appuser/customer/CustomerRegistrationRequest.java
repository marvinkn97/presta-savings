package dev.marvin.savings.appuser.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Schema(name = "Customer Registration Request")
public record CustomerRegistrationRequest(
        @NotBlank(message = "username is required")
        @NotEmpty(message = "username is required")
        @Schema(name = "username", defaultValue = "marvin")
        String username,

        @NotBlank(message = "name is required")
        @NotEmpty(message = "name is required")
        @Schema(name = "name", defaultValue = "Marvin Nyingi")
        String fullName,

        @Email(message = "email is invalid")
        @NotEmpty(message = "email is required")
        @NotBlank(message = "email is required")
        @Schema(name = "email", defaultValue = "marvin@example.com")
        String email,

        @NotBlank(message = "password is required")
        @NotEmpty(message = "password is required")
        @Schema(name = "password", defaultValue = "password")
        String password
) {
}
