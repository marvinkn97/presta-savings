package dev.marvin.savings.notifications.email;

import dev.marvin.savings.exception.NotificationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MailDevEmailService implements EmailService {
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(String to, String message) {

        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

            messageHelper.setFrom("savings@presta.co.ke");
            messageHelper.setSubject("Confirm Email");
            messageHelper.setText(message, true);
            messageHelper.setTo(to);
            messageHelper.setSentDate(new Date());

            javaMailSender.send(mailMessage);

        } catch (MessagingException e) {
            throw new NotificationException("Email not sent");
        }

    }
}
