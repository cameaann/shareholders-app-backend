package server.shareholders_app_backend.dto;

import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.model.Shareholder;
import java.util.Set;

public class ShareholderWithSharesDTO {
    private Shareholder shareholder;
    private Set<ShareRangeDTO> shares;

    // Getters and setters
    public Shareholder getShareholder() {
        return shareholder;
    }

    public void setShareholder(Shareholder shareholder) {
        this.shareholder = shareholder;
    }

    public Set<ShareRangeDTO> getShares() {
        return shares;
    }

    public void setShares(Set<ShareRangeDTO> shares) {
        this.shares = shares;
    }
}
