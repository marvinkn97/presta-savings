package dev.marvin.savings.auth;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserService;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.auth.confimationtoken.ConfirmationTokenService;
import dev.marvin.savings.customer.Customer;
import dev.marvin.savings.customer.CustomerService;
import dev.marvin.savings.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final CustomerService customerService;
    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public String registerCustomer(RegistrationRequest registrationRequest){

        //check if username exists
        if (appUserService.existsByUserName(registrationRequest.username())) {
            throw new DuplicateResourceException("username already taken");
        }

        AppUser appUser = AppUser.builder()
                .userName(registrationRequest.username())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .role(Role.CUSTOMER)
                .joinDate(LocalDateTime.now())
                .build();

        //check if email exists
        if(customerService.existCustomerWithEmail(registrationRequest.email())){
            throw new DuplicateResourceException("email already taken");
        }

        var savedAppUser = appUserService.saveAppUser(appUser);

        Customer customer = Customer.builder()
                .email(registrationRequest.email())
                .appUser(savedAppUser)
                .build();

        customerService.saveCustomer(customer);

        String token = confirmationTokenService.generateToken(customer);

        //send email

        return token;
    }

}
