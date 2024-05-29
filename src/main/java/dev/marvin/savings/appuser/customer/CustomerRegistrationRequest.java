package dev.marvin.savings.appuser.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "Customer Registration Request")
public record CustomerRegistrationRequest(

        @NotEmpty(message = "username must not be null or empty")
        @NotBlank(message = "username must not be blank")
        @Size(min = 3, message = "username must have at least 3 characters")
        @Schema(name = "username", defaultValue = "marvin")
        String username,

        @NotEmpty(message = "name must not be null or empty")
        @NotBlank(message = "name must not be blank")
        @Size(min = 6, message = "name must have at least 6 characters")
        @Schema(name = "fullName", defaultValue = "Marvin Nyingi")
        String fullName,

        @Email(message = "provide a valid email address")
        @NotBlank(message = "email must not be blank")
        @Schema(name = "email", defaultValue = "marvin@example.com")
        String email,

        @NotEmpty(message = "password must not be null or empty")
        @NotBlank(message = "password must not be blank")
        @Size(min = 6, message = "password must have at least 6 characters")
        @Schema(name = "password", defaultValue = "password")
        String password,


        @NotEmpty(message = "mobile number must not be null or empty")
        @NotBlank(message = "mobile number must not be blank")
        @Min(value = 12, message = "provide valid mobile number")
        @Pattern(regexp = "254[0-9]{9}")
        @Schema(name = "mobileNumber", nullable = true, defaultValue = "254792735465")
        String mobileNumber,


        @NotEmpty(message = "government id must not be null or empty")
        @NotBlank(message = "government id must not be blank")
        @Schema(name = "governmentId", nullable = true, defaultValue = "22345313")
        String governmentId,

        @NotEmpty(message = "kra pin must not be null or empty")
        @NotBlank(message = "kra pin must not be blank")
        @Schema(name = "kraPin", nullable = true, defaultValue = "A0998349823C")
        String kraPin
) {
}
