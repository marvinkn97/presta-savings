package dev.marvin.savings.auth;

import dev.marvin.savings.auth.confirmationtoken.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ConfirmationTokenService confirmationTokenService;

    public void confirmEmailToken(String token) {
       confirmationTokenService.validateAndConfirmToken(token);
    }
}
