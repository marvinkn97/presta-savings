package dev.marvin.savings.auth;

import dev.marvin.savings.appuser.customer.CustomerService;
import dev.marvin.savings.auth.confirmationtoken.ConfirmationTokenService;
import dev.marvin.savings.notifications.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerService customerService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public String registerCustomer(RegistrationRequest registrationRequest) {

        var customer = customerService.registerCustomer(registrationRequest);

        String token = confirmationTokenService.generateToken(customer);

        String link = "http://localhost:8081/savings/api/v1/auth/register/confirm?token=%s".formatted(token);

        //send email
        String emailTemplate = buildEmailTemplate(registrationRequest.fullName(), link);
        emailService.sendEmail(registrationRequest.email(), emailTemplate);
        return token;
    }

    private String buildEmailTemplate(String name, String link) {
        return """
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
                                
                """.formatted(name, link);
    }

    public void confirmEmailToken(String token) {
       confirmationTokenService.validateAndConfirmToken(token);
    }
}
