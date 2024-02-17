package dev.marvin.savings.notifications.sms;

public interface SmsService {
    void sendSMS(String to, String message);
}
