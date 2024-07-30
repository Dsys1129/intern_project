package com.intern_project.global.exception;

import com.intern_project.global.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : {}", e);

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        e.getBindingResult().getGlobalErrors().forEach(error ->
                errors.put(error.getCode(), error.getDefaultMessage()));

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Exception", errors);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> exceptionHandler(RuntimeException e) {
        log.error("RuntimeException : {}", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e) {
        log.error("Server Error : {}", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", null);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
