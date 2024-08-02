package com.artozersky.HackerNewsAPI.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;

import jakarta.validation.ConstraintViolationException;

/**
 * GlobalExceptionHandler handles all exceptions that occur in the application.
 * It provides specific handlers for various types of exceptions and ensures that
 * the user receives meaningful error messages.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles any general exceptions that are not specifically handled by other methods.
     *
     * @param e the exception that was thrown
     * @return a ResponseEntity containing a PostResponseDTO with an error message and an HTTP status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PostResponseDTO> handleGeneralException(Exception e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage("An unexpected error occurred: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions related to database access.
     *
     * @param e the DataAccessException that was thrown
     * @return a ResponseEntity containing a PostResponseDTO with an error message and an HTTP status code
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<PostResponseDTO> handleDataAccessException(DataAccessException e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage("Database error: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    } 

    /**
     * Handles validation errors for method arguments annotated with @Valid.
     *
     * @param ex the MethodArgumentNotValidException that was thrown
     * @param headers the HTTP headers
     * @param status the HTTP status
     * @param request the current web request
     * @return a ResponseEntity containing a map of field errors and an HTTP status code
     */
    @ExceptionHandler
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles constraint violations that occur during validation.
     *
     * @param e the ConstraintViolationException that was thrown
     * @return a ResponseEntity containing a PostResponseDTO with an error message and an HTTP status code
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<PostResponseDTO> handleConstraintViolation(ConstraintViolationException e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage("Constraint violation: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions where a requested resource is not found.
     *
     * @param e the CustomNotFoundException that was thrown
     * @return a ResponseEntity containing a PostResponseDTO with an error message and an HTTP status code
     */
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<PostResponseDTO> handleNotFoundException(CustomNotFoundException e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles data integrity violations, such as unique constraint violations.
     *
     * @param e the DataIntegrityViolationException that was thrown
     * @return a ResponseEntity containing a PostResponseDTO with an error message and an HTTP status code
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<PostResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage("Data integrity violation: " + e.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
