package dev.marvin.savings.exception.advice;

import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.InsufficientAmountException;
import dev.marvin.savings.exception.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InsufficientAmountException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ServerResponse processInsufficientAmountException(InsufficientAmountException e) {
        return new ServerResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ServerResponse processDuplicateResourceException(DuplicateResourceException e){
        return new ServerResponse(HttpStatus.CONFLICT.toString(), e.getMessage(), LocalDateTime.now());
    }
}
