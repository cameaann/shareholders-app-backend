package server.shareholders_app_backend.dto;

import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.model.Shareholder;
import java.util.Set;

public class ShareholderWithSharesDTO {
    private Shareholder shareholder;
    private Integer shares;

    // Getters and setters
    public Shareholder getShareholder() {
        return shareholder;
    }

    public void setShareholder(Shareholder shareholder) {
        this.shareholder = shareholder;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public int getShares() {
        return shares;
    }

}
