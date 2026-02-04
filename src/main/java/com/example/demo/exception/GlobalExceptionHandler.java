package com.example.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/*
@RestControllerAdvice is a specialized annotation in the Spring Framework (introduced in 4.3) designed to handle
exceptions globally across all @RestController components.
It is a convenience annotation that combines @ControllerAdvice and @ResponseBody.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    Placed above specific methods to tell Spring which error that method handles.
    How the Flow Works:
    1. The Trigger: A user sends a request (e.g., GET /users/99).
    2. The Error: The Service layer can't find user 99 and throws a NotFoundException.
    3. The Interception: Before the error reaches the user, GlobalExceptionHandler catches it.
    4. The Formatting: The handleNotFound method is called. It uses the build() helper method to create an ApiError object.
    5. The Response: The user receives a clean JSON object and a proper HTTP status code (like 404) instead of a generic "Internal Server Error."
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    // Bean validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().isEmpty()
                ? "Validation failed"
                : ex.getBindingResult().getFieldErrors().get(0).getField() + " " +
                ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        return build(HttpStatus.BAD_REQUEST, msg, req);
    }

    // DB constraint errors (NOT NULL, unique constraint, FK, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, "Data integrity violation", req);
    }

    // Catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknown(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", req);
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message, HttpServletRequest req) {

        ApiError body = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message,
                req.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }
}
