package dev.marvin.savings.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorMessage(LocalDateTime timestamp, HttpStatus status, String message, String path) {
}
