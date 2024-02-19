package dev.marvin.savings.exception;

public class InsufficientAmountException extends RuntimeException{
    public InsufficientAmountException(String message) {
        super(message);
    }
}
