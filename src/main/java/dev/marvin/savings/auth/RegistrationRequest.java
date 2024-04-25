package dev.marvin.savings.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Registration Request")
public record RegistrationRequest(
        @NotBlank(message = "username is required")
        @Schema(name = "username", defaultValue = "foobar")
        String username,

        @NotBlank(message = "name is required")
        @Schema(name = "name", defaultValue = "Foo Bar")
        String fullName,

        @Email(message = "email is invalid")
        @NotBlank(message = "email is required")
        @Schema(name = "email", defaultValue = "foo@example.com")
        String email,

        @NotBlank(message = "password is required")
        @Schema(name = "password", defaultValue = "password")
        String password
) {
}
