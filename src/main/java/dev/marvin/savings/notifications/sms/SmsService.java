package dev.marvin.savings.notifications.sms;

public interface SmsService {
   String sendSMS(String from, String to, String message);
}
