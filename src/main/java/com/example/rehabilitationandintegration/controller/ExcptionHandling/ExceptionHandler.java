package com.example.rehabilitationandintegration.controller.ExcptionHandling;

import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.exception.*;
import com.example.rehabilitationandintegration.model.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;


@RestControllerAdvice
@Slf4j
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TimeNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleTimeNotAllowedException(TimeNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidTimeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTimeException(InvalidTimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CancellationException.class)
    public ResponseEntity<ExceptionDto> handleCancellationException(CancellationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionDto(ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(OwnershipException.class)
    public ResponseEntity<Map<String, String>> handleOwnershipException(OwnershipException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", ex.getMessage()));
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(AlreadyException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyException(AlreadyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<Map<String, String>> handleEmailNotSentException(EmailNotSentException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", ex.getMessage()));
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<Map<String, String>> handleInvalidOtpException(InvalidOtpException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", ex.getMessage()));
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        log.error("Необработанное исключение: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionDto> entityNotFound(ResourceNotFoundException exception) {
        log.error("Action.log.entityNotFound :{} ", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(exception.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ExceptionDto> validation(MethodArgumentNotValidException exception) {
        log.error("Action.log.validation :{}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ExceptionDto(exception.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException .class)
    public ResponseEntity<ExceptionDto> handleInvalidEnumValue(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(ex.getMessage()));
    }


}
