package dev.marvin.savings.notifications.email;

public interface EmailService {
    void sendEmail(String to, String message);

    default String buildEmailTemplate(String name, String emailConfirmationToken) {
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
                                            <p>Thank you for registering on our platform.</p>
                                             <p class="mt-4">This activation code <span class="badge bg-white fs-2 p-2 fw-bold">%s</span> will expire in 15 minutes for security reasons. If you do not activate your account within this time frame, you will need to request a new activation code.</p>
                                            <p>If you did not register on our platform, please ignore this email.</p>
                                            <hr class="my-4">
                                            <p class="lead">Best regards,<br> Presta Savings Team</p>
                                        </div>
                                    </div>
                                </body>
                                </html>
                                
                """.formatted(name, emailConfirmationToken);
    }
}
