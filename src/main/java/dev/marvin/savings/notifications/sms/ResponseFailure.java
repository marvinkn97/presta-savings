package dev.marvin.savings.notifications.sms;

import java.time.LocalDateTime;

public record ResponseFailure(LocalDateTime timestamp, String status, String error, String message, String path) {
}
