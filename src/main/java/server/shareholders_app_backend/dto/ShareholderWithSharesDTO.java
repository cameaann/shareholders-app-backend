package server.shareholders_app_backend.dto;

import server.shareholders_app_backend.model.Shareholder;

// Osakkeen omistajan DTO-luokka, joka sisältää osakkeenomistajan tiedot ja omistettujen osakkeiden määrän
public class ShareholderWithSharesDTO {
    private Shareholder shareholder; // Osakkeen omistaja
    private Integer shares; // Omistettujen osakkeiden määrä

    // Getter osakkeen omistajalle
    public Shareholder getShareholder() {
        return shareholder;
    }

    // Setter osakkeen omistajalle
    public void setShareholder(Shareholder shareholder) {
        this.shareholder = shareholder;
    }

    // Setter osakkeiden määrälle
    public void setShares(Integer shares) {
        this.shares = shares;
    }

    // Getter osakkeiden määrälle
    public int getShares() {
        return shares;
    }
}
