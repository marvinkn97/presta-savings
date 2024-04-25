package dev.marvin.savings.auth;

import dev.marvin.savings.appuser.AppUser;
import dev.marvin.savings.appuser.AppUserService;
import dev.marvin.savings.appuser.Role;
import dev.marvin.savings.auth.confirmationtoken.ConfirmationTokenService;
import dev.marvin.savings.customer.Customer;
import dev.marvin.savings.customer.CustomerService;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.notifications.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomerService customerService;
    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public String registerCustomer(RegistrationRequest registrationRequest) {

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
        if (customerService.existCustomerWithEmail(registrationRequest.email())) {
            throw new DuplicateResourceException("email already taken");
        }

        var savedAppUser = appUserService.saveAppUser(appUser);

        Customer customer = Customer.builder()
                .name(registrationRequest.fullName())
                .email(registrationRequest.email())
                .appUser(savedAppUser)
                .build();

        customerService.saveCustomer(customer);

        String token = confirmationTokenService.generateToken(customer);

        String link = "http://localhost:8081/savings/api/v1/auth/register/confirm?token=%s".formatted(token);

        //send email
        String emailTemplate = """
                  <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8">
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                    <title>Account Registration Confirmation</title>
                                    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
                                </head>
                                <body>
                                    <div class="container">
                                        <div class="jumbotron mt-4">
                                            <h2 class="display-4">Welcome to Our Platform!</h2>
                                            <p class="lead">Dear %s,</p>
                                            <p>Thank you for registering on our platform. To activate your account, please click the button below:</p>
                                            <a href="%s" class="btn btn-primary btn-lg">Activate Now</a>
                                            <p class="mt-4">This activation link will expire in 15 minutes for security reasons. If you do not activate your account within this time frame, you will need to request a new activation link.</p>
                                            <p>If you did not register on our platform, please ignore this email.</p>
                                            <hr class="my-4">
                                            <p class="lead">Best regards,<br> Presta Savings Team</p>
                                        </div>
                                    </div>
                                </body>
                                </html>
                
                """.formatted(registrationRequest.fullName(), link);
        emailService.sendEmail(registrationRequest.email(), emailTemplate);
        return token;
    }

    public String confirmEmailToken(String token){
         boolean isValidToken = confirmationTokenService.validateToken(token);

         if(isValidToken){

         }
        return null;
    }
}
