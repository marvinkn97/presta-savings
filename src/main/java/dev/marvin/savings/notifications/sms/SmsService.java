package dev.marvin.savings.notifications.sms;

public interface SmsService {
    void sendSMS(String endPoint, String apikey, String from, String to, String message);
}
