package dev.marvin.savings.model.dto;

public record SmsRequest(String from, String to, String message) {
}
