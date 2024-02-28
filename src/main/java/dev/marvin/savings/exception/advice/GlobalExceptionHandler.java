package dev.marvin.savings.exception.advice;

import dev.marvin.savings.exception.*;
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
    public ErrorMessage handleInsufficientAmountException(InsufficientAmountException e) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorMessage handleDuplicateResourceException(DuplicateResourceException e){
        return new ErrorMessage(HttpStatus.CONFLICT, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException e){
        return new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(DatabaseOperationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleDatabaseOperationException(DatabaseOperationException e){
       return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), LocalDateTime.now());
    }
}
