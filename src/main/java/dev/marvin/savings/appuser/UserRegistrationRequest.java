package dev.marvin.savings.appuser;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(name = "User Registration Request")
public record UserRegistrationRequest(
        @Schema(name = "name", defaultValue = "Foo Bar")
        @NotBlank(message = "name is required")
        String name,

        @Schema(name = "email", defaultValue = "foobar@example.com")
        @Email
        @NotBlank(message = "email is required")
        String email,

        @Schema(name = "password", defaultValue = "$1234_FooB")
        @NotBlank(message = "Password is required")
        String password,

        @Schema(name = "role", defaultValue = "ADMIN")
        @NotBlank(message = "Role is required")
        String role
) {
}
