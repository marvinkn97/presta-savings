package dev.marvin.savings.appuser.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(name = "Customer Update Request Model")
public record CustomerUpdateRequest(

        @Schema(name = "fullName", nullable = true, defaultValue = "Foo Bar")
        String fullName,

        @Schema(name = "email", nullable = true, defaultValue = "foo@example.com")
        @Email
        String email,

        @Schema(name = "password", nullable = true, defaultValue = "password")
        String password,

        @Schema(name = "mobileNumber", nullable = true, defaultValue = "254792735465")
        String mobileNumber
 ) {
}
