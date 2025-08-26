package com.app.restaurant.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.response.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(false)
                .message(MessageConstants.RECORD_NOT_FOUND)
                .error(getSafeMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Void>> handleApplicationException(ApplicationException ex) {
        log.error("Application exception: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(false)
                .message(MessageConstants.OPERATION_FAILED)
                .error(getSafeMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(ValidationException ex) {
        log.error("Validation exception: {}", ex.getMessage(), ex);
        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .status(false)
                .message(MessageConstants.VALIDATION_FAILED)
                .error(MessageConstants.VALIDATION_ERRORS_OCCURRED)
                .data(ex.getErrors())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Method argument not valid: {}", ex.getMessage(), ex);
        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .status(false)
                .message(MessageConstants.VALIDATION_FAILED)
                .error(MessageConstants.VALIDATION_ERRORS_OCCURRED)
                .data(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredJwtException(ExpiredJwtException ex) {
        log.error("JWT token expired: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(false)
                .message(MessageConstants.JWT_EXPIRED)
                .error(getSafeMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleJwtException(JwtException ex) {
        log.error("JWT error: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(false)
                .message(MessageConstants.JWT_ERROR)
                .error(getSafeMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(false)
                .message(MessageConstants.INVALID_ARGUMENT)
                .error(getSafeMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication failed: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(false)
                .message(MessageConstants.AUTHENTICATION_FAILED)
                .error(getSafeMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access denied: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(false)
                .message(MessageConstants.ACCESS_DENIED)
                .error(getSafeMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse<Void>> handleSQLException(SQLException ex) {
        log.error("Database error: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(false)
                .message(MessageConstants.DATABASE_ERROR)
                .error(getSafeMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(false)
                .message(MessageConstants.UNEXPECTED_ERROR_OCCURRED)
                .error(getSafeMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private String getSafeMessage(Throwable ex) {
        return ex.getMessage() != null ? ex.getMessage() : "No additional details available.";
    }
}
