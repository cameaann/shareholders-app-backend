package server.shareholders_app_backend.model;

import java.time.LocalDateTime;
import java.util.List;

// CustomErrorResponse-luokka, joka käyttää virheiden käsittelyyn
public class CustomErrorResponse {
    private LocalDateTime timestamp; // Virheen aikaleima
    private int status; // HTTP-tila
    private String error; // Virheen tyyppi
    private String message; // Virheen kuvaus
    private List<String> errorMessages; // Lista yksityiskohtaisista virheviesteistä

    // Konstruktori, joka alustaa CustomErrorResponse-olion
    public CustomErrorResponse(LocalDateTime timestamp, int status, String error, String message,
            List<String> errorMessages) {
        this.timestamp = timestamp; // Asetetaan aikaleima
        this.status = status; // Asetetaan HTTP-tila
        this.error = error; // Asetetaan virheen tyyppi
        this.message = message; // Asetetaan virheen kuvaus
        this.errorMessages = errorMessages; // Asetetaan virheviestit
    }

    // Palauttaa aikaleiman
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Asettaa aikaleiman
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Palauttaa HTTP-tilan
    public int getStatus() {
        return status;
    }

    // Asettaa HTTP-tilan
    public void setStatus(int status) {
        this.status = status;
    }

    // Palauttaa virheen tyypin
    public String getError() {
        return error;
    }

    // Asettaa virheen tyypin
    public void setError(String error) {
        this.error = error;
    }

    // Palauttaa virheen kuvauksen
    public String getMessage() {
        return message;
    }

    // Asettaa virheen kuvauksen
    public void setMessage(String message) {
        this.message = message;
    }

    // Palauttaa listan virheviesteistä
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    // Asettaa listan virheviesteistä
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
