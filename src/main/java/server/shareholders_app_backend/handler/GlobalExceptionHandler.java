package server.shareholders_app_backend.handler;

import server.shareholders_app_backend.exception.ShareholderDeletionException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import server.shareholders_app_backend.model.CustomErrorResponse;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// Tämä luokka käsittelee globaalisti kaikki sovelluksen poikkeukset
@ControllerAdvice
public class GlobalExceptionHandler {

    // Käsitellään ConstraintViolationException-tyyppiset poikkeukset
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations(); // Haetaan rajoitusloukkaukset
        List<String> errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage) // Kerätään virheviestit
                .collect(Collectors.toList());

        // Luodaan CustomErrorResponse-olio virheviesteillä
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(), // Aikaleima
                HttpStatus.BAD_REQUEST.value(), // HTTP-tila
                "Bad Request", // Virheen tyyppi
                "Validation error occurred.", // Virheen kuvaus
                errorMessages); // Yksityiskohtaiset virheviestit
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Palautetaan virhevastaus
    }

    // Käsitellään ShareholderDeletionException-tyyppiset poikkeukset
    @ExceptionHandler(ShareholderDeletionException.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleShareholderDeletionException(ShareholderDeletionException ex) {
        // Luodaan CustomErrorResponse-olio poikkeuksen viestillä
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(), // Aikaleima
                HttpStatus.BAD_REQUEST.value(), // HTTP-tila
                "Bad Request", // Virheen tyyppi
                ex.getMessage(), // Virheen kuvaus
                Collections.singletonList(ex.getMessage())); // Virheviesti
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Palautetaan virhevastaus
    }

    // Käsitellään kaikki muut poikkeukset
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleGenericException(Exception ex) {
        // Luodaan CustomErrorResponse-olio odottamattomasta virheestä
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(), // Aikaleima
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // HTTP-tila
                "Internal Server Error", // Virheen tyyppi
                "An unexpected error occurred: " + ex.getMessage(), // Virheen kuvaus
                Collections.singletonList("An unexpected error occurred.")); // Virheviesti
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // Palautetaan virhevastaus
    }
}
