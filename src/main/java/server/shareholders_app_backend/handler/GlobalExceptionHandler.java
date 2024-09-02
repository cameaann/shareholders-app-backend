package server.shareholders_app_backend.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import server.shareholders_app_backend.model.CustomErrorResponse;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        // Extract the first validation error message
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String errorMessage = violations.stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Validation error");

        // Create a custom error response
        CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Other exception handlers can be added here
}
