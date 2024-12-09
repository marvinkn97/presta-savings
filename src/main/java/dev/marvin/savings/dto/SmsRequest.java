package dev.marvin.savings.dto;

public record SmsRequest(String to, String from, String message) {
}