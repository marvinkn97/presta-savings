package dev.marvin.savings.exception.advice;

import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.ErrorMessage;
import dev.marvin.savings.exception.InsufficientAmountException;
import dev.marvin.savings.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleDatabaseOperationException(Exception e, WebRequest webRequest) {
        return new ErrorMessage(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), webRequest.getDescription(false));
    }

    @ExceptionHandler(InsufficientAmountException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInsufficientAmountException(InsufficientAmountException e, WebRequest webRequest) {
        return new ErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage(), webRequest.getDescription(false));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorMessage handleDuplicateResourceException(DuplicateResourceException e, WebRequest webRequest) {
        return new ErrorMessage(LocalDateTime.now(), HttpStatus.CONFLICT, e.getMessage(), webRequest.getDescription(false));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        return new ErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND, e.getMessage(), webRequest.getDescription(false));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

        errorList.forEach((error)-> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
