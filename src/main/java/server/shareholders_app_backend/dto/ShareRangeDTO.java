package server.shareholders_app_backend.dto;

import lombok.Getter;
import lombok.Setter;
import server.shareholders_app_backend.model.ShareRange;

// Osakealueen DTO-luokka (Data Transfer Object)
@Getter
@Setter
public class ShareRangeDTO {
    private Long id; // Osakealueen ID
    private int quantity; // Omistettujen osakkeiden määrä
    private int startNumber; // Lasketun osakkeen alkuperäinen numero
    private int endNumber; // Lasketun osakkeen loppunumero
    private Long shareholderId; // Osakkeen omistajan ID

    // Konstruktori, joka luo DTO:n ShareRange-mallista
    public ShareRangeDTO(ShareRange shareRange) {
        this.id = shareRange.getId(); // Aseta ID
        this.quantity = shareRange.getQuantity(); // Aseta osakkeiden määrä
        this.startNumber = shareRange.getStartNumber(); // Aseta alkuperäinen numero
        this.endNumber = shareRange.getEndNumber(); // Aseta loppunumero
        // Aseta omistajan ID, jos osakkeen omistaja ei ole null
        this.shareholderId = shareRange.getShareholder() != null ? shareRange.getShareholder().getId() : null;
    }

    // Tyhjäkonstruktori JSON-deserialisointia varten
    public ShareRangeDTO() {
    }
}
