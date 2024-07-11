package dev.marvin.savings.serviceImpl;

import dev.marvin.savings.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailDevEmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(String to, String message) {

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

        try {
            messageHelper.setFrom("savings@presta.co.ke");
            messageHelper.setSubject("Confirm Email");
            messageHelper.setText(message, true);
            messageHelper.setTo(to);
            messageHelper.setSentDate(new Date());

            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            log.info(e.getMessage());
        }
    }
}
