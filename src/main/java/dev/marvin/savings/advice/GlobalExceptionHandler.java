package dev.marvin.savings.advice;

import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.InsufficientAmountException;
import dev.marvin.savings.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientAmountException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse processInsufficientAmountException(InsufficientAmountException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorResponse processDuplicateResourceException(DuplicateResourceException e){
        return new ErrorResponse(HttpStatus.CONFLICT.toString(), e.getMessage(), LocalDateTime.now());
    }
}
