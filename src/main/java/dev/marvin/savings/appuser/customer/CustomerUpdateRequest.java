package dev.marvin.savings.appuser.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(name = "Customer Update Request Model")
public record CustomerUpdateRequest(

        @Schema(name = "Full Name", nullable = true, defaultValue = "Foo Bar")
        String fullName,

        @Schema(name = "Email", nullable = true, defaultValue = "foo@example.com")
        @Email
        String email,

        @Schema(name = "Password", nullable = true, defaultValue = "password")
        String password,

        @Schema(name = "Mobile Number", nullable = true, defaultValue = "254792735465")
        String mobileNumber
 ) {
}
