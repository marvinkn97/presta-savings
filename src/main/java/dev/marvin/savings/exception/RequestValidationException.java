package dev.marvin.savings.exception;

public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super(message);
    }
}
