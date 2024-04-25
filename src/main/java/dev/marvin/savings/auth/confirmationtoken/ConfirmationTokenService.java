package dev.marvin.savings.auth.confirmationtoken;

import dev.marvin.savings.appuser.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public String generateToken(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("customer cannot be null");
        }

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = ConfirmationToken
                .builder()
                .token(token)
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .customer(customer)
                .build();
        confirmationTokenRepository.save(confirmationToken);
        return token;
    }

    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("token does not exist"));
    }

    public boolean validateToken(String token) {

        //check is token exists
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("invalid token"));


        //check if token is already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new RuntimeException("token already confirmed");
        }

        //check if token is expired
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("token already expired");
        }

        return true; //token is valid
    }

    public void saveToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }
}
