package dev.marvin.savings.appuser.confirmationtoken;

import dev.marvin.savings.appuser.AppUserRepository;
import dev.marvin.savings.appuser.customer.Customer;
import dev.marvin.savings.appuser.customer.CustomerRepository;
import dev.marvin.savings.exception.RequestValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserRepository appUserRepository;
    private final CustomerRepository customerRepository;

    public String generateToken(Customer customer) {

        if (ObjectUtils.isEmpty(customer)) {
            throw new RequestValidationException("Not Registered");
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

    @Transactional
    public void validateAndConfirmToken(String token) {

        //check is token exists
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RequestValidationException("invalid token"));


        //check if token is already confirmed
        if (ObjectUtils.isNotEmpty(confirmationToken.getConfirmedAt())) {
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
            customerRepository.save(customer);

            appUser.setEnabled(true);
            appUserRepository.save(appUser);

        } catch (Exception e) {
            throw new RequestValidationException("invalid token");
        }

    }

}
