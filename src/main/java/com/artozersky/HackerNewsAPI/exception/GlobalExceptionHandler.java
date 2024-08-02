package com.artozersky.HackerNewsAPI.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PostResponseDTO> handleGeneralException(Exception e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage("An unexpected error occurred: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PostResponseDTO> handleValidationException(MethodArgumentNotValidException e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage("Validation failed: " + e.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<PostResponseDTO> handleNotFoundException(CustomNotFoundException e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle SQL and Data Access exceptions
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<PostResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage("Data integrity violation: " + e.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<PostResponseDTO> handleConstraintViolation(ConstraintViolationException e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage("Constraint violation: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<PostResponseDTO> handleDataAccessException(DataAccessException e) {
        PostResponseDTO errorResponse = new PostResponseDTO();
        errorResponse.setMessage("Database error: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
