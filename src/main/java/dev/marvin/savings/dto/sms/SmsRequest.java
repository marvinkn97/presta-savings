package dev.marvin.savings.dto.sms;

public record SmsRequest(String from, String to, String message) {
}
