package dev.marvin.savings.notifications.email;

public interface EmailService{
    String sendEmail(String to, String message);
}
