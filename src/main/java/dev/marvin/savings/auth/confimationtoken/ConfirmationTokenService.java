package dev.marvin.savings.auth.confimationtoken;

import dev.marvin.savings.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public String generateToken(Customer customer) {

        if(customer != null) {

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
        return "token not generated";
    }
}
