package com.example.librarybooking.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex,
                                                                      HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(ReservationStateException.class)
    public ResponseEntity<ApiErrorResponse> handleReservationState(ReservationStateException ex,
                                                                    HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT,
                "Conflict",
                ex.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex,
                                                                  HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex,
                                                                   HttpServletRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        String message = String.join(", ", validationErrors.values());
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                "Validation Failed",
                message,
                request.getRequestURI(),
                validationErrors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                          HttpServletRequest request) {
        Throwable root = ex.getRootCause() != null ? ex.getRootCause() : ex;
        String rootMessage = root.getMessage() != null ? root.getMessage() : ex.getMessage();

        // Try to match MySQL duplicate entry message: Duplicate entry '8892009878' for key 'books.isbn'
        Pattern p = Pattern.compile("Duplicate entry '([^']+)' for key '([^']+)'", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rootMessage);
        Map<String, String> errors = new HashMap<>();
        if (m.find()) {
            String value = m.group(1);
            String key = m.group(2); // may contain table.column or just index name
            String field = key;
            // map common index names to friendly field names
            if (key.toLowerCase().contains("isbn")) {
                field = "isbn";
            }
            String message = null;
            if ("isbn".equalsIgnoreCase(field)) {
                message = "ISBN already exists";
                errors.put("isbn", message);
            } else {
                message = "Duplicate value '" + value + "' for field " + field;
                errors.put(field, message);
            }
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "Constraint Violation", message, request.getRequestURI(), errors);
        }

        // fallback: general conflict
        return buildErrorResponse(HttpStatus.CONFLICT, "Data Integrity Violation", "Database constraint violated", request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex,
                                                                  HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "Something went wrong. Please try again later.",
                request.getRequestURI());
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status,
                                                                String error,
                                                                String message,
                                                                String path) {
        ApiErrorResponse response = new ApiErrorResponse(
                status.value(),
                error,
                message,
                path,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, status);
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status,
                                                                String error,
                                                                String message,
                                                                String path,
                                                                Map<String, String> validationErrors) {
        ApiErrorResponse response = new ApiErrorResponse(
                status.value(),
                error,
                message,
                path,
                LocalDateTime.now(),
                validationErrors
        );
        return new ResponseEntity<>(response, status);
    }
}
