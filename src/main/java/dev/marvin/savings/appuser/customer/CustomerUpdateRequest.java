package dev.marvin.savings.appuser.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(name = "Customer Update Request Model")
public record CustomerUpdateRequest(

        @Schema(name = "name update", nullable = true, defaultValue = "Foo Bar")
        String name,

        @Schema(name = "email update", nullable = true, defaultValue = "foo@example.com")
        @Email
        String email,

        String password,

        String mobile,

        String governmentId,

        String profileImage) {
}
