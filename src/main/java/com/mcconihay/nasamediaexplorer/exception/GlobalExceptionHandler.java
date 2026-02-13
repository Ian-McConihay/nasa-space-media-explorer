package com.mcconihay.nasamediaexplorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Centralized handler for application exceptions.
 * Converts exceptions into consistent HTTP responses
 * with structured error details.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles cases where a requested resource does not exist.
     *
     * @param ex the thrown exception
     * @return HTTP 404 response with error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "Resource Not Found",
                        "message", ex.getMessage()
                ));
    }

    /**
     * Handles attempts to create a resource that already exists.
     *
     * @param ex the thrown exception
     * @return HTTP 409 response with error details
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateResourceException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "Duplicate Resource",
                        "message", ex.getMessage()
                ));
    }

    /**
     * Handles all uncaught exceptions.
     * Prevents internal errors from exposing stack traces to clients.
     *
     * @param ex the thrown exception
     * @return HTTP 500 response with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "Internal Server Error",
                        "message", ex.getMessage()
                ));
    }
}