package dev.marvin.savings.advice;

import org.springframework.http.HttpStatus;

public record HttpResponse(
        Integer httpStatusCode, HttpStatus httpStatus, String reason, String message) {
}
