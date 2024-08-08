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

import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsResponseDTOImpl;

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
    public ResponseEntity<NewsPostsResponseDTOImpl> handleGeneralException(Exception e) {
        NewsPostsResponseDTOImpl errorResponse = new NewsPostsResponseDTOImpl();
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
    public ResponseEntity<NewsPostsResponseDTOImpl> handleDataAccessException(DataAccessException e) {
        NewsPostsResponseDTOImpl errorResponse = new NewsPostsResponseDTOImpl();
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
    public ResponseEntity<NewsPostsResponseDTOImpl> handleConstraintViolation(ConstraintViolationException e) {
        NewsPostsResponseDTOImpl errorResponse = new NewsPostsResponseDTOImpl();
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
    public ResponseEntity<NewsPostsResponseDTOImpl> handleNotFoundException(CustomNotFoundException e) {
        NewsPostsResponseDTOImpl errorResponse = new NewsPostsResponseDTOImpl();
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
    public ResponseEntity<NewsPostsResponseDTOImpl> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        NewsPostsResponseDTOImpl errorResponse = new NewsPostsResponseDTOImpl();
        errorResponse.setMessage("Data integrity violation: " + e.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CacheRetrievalException.class)
    public ResponseEntity<NewsPostsResponseDTOImpl> handleCacheRetrievalException(CacheRetrievalException e) {
        NewsPostsResponseDTOImpl errorResponse = new NewsPostsResponseDTOImpl();
        errorResponse.setMessage("Error retrieving data from cache: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
}

    @ExceptionHandler(DatabaseFetchException.class)
    public ResponseEntity<NewsPostsResponseDTOImpl> handleDatabaseFetchException(DatabaseFetchException e) {
        NewsPostsResponseDTOImpl errorResponse = new NewsPostsResponseDTOImpl();
        errorResponse.setMessage("Error fetching data from the database: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<NewsPostsResponseDTOImpl> handleCustomServiceException(CustomServiceException e) {
        NewsPostsResponseDTOImpl errorResponse = new NewsPostsResponseDTOImpl();
        errorResponse.setMessage("An error occurred: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
