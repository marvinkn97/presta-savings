package dev.marvin.savings.notifications.sms;

public record SmsRequest(String from, String to, String message) {
}
