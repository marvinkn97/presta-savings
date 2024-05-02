package dev.marvin.savings.auth.confirmationtoken;

import dev.marvin.savings.appuser.AppUserService;
import dev.marvin.savings.appuser.customer.Customer;
import dev.marvin.savings.appuser.customer.CustomerService;
import dev.marvin.savings.exception.RequestValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserService appUserService;
    private final CustomerService customerService;

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

    @Transactional
    public void validateAndConfirmToken(String token) {

        //check is token exists
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RequestValidationException("invalid token"));


        //check if token is already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new RequestValidationException("token already confirmed");
        }

        //check if token is expired
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RequestValidationException("token already expired, request new activation token");
        }

        try {
            var customer = confirmationToken.getCustomer();
            var appUser = customer.getAppUser();

            confirmationToken.setConfirmedAt(LocalDateTime.now());
            confirmationTokenRepository.save(confirmationToken);

            customer.setEmailConfirmed(true);
            customerService.saveCustomer(customer);

            appUser.setEnabled(true);
            appUserService.saveAppUser(appUser);

        } catch (Exception e) {
            throw new RequestValidationException("token could not be validated");
        }

    }

}
