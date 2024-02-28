package dev.marvin.savings.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorMessage(HttpStatus status, String message, LocalDateTime time) {
}
