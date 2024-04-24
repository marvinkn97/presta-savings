package dev.marvin.savings.notifications.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailDevEmailService implements EmailService{
    @Override
    public String sendEmail(String to, String message) {
        return null;
    }
}
