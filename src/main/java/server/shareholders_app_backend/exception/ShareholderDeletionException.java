package server.shareholders_app_backend.exception;

// Tämä luokka edustaa poikkeusta, joka liittyy osakkeenomistajan poistamiseen
public class ShareholderDeletionException extends RuntimeException {

    // Konstruktori, joka vastaanottaa virheviestin
    public ShareholderDeletionException(String message) {
        super(message); // Kutsuu yliluokan (RuntimeException) konstruktoria viestin kanssa
    }
}
