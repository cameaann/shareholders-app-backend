package server.shareholders_app_backend.dto;

import server.shareholders_app_backend.model.ShareRange;

public class ShareRangeDTO {
    private Long id;
    private int quantity;
    private int startNumber;
    private int endNumber;
    private Long shareholderId;

    // Constructor
    public ShareRangeDTO(ShareRange shareRange) {
        this.id = shareRange.getId();
        this.quantity = shareRange.getQuantity();
        this.startNumber = shareRange.getStartNumber();
        this.endNumber = shareRange.getEndNumber();
        this.shareholderId = shareRange.getShareholder() != null ? shareRange.getShareholder().getId() : null;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(int endNumber) {
        this.endNumber = endNumber;
    }

    public Long getShareholderId() {
        return shareholderId;
    }

    public void setShareholderId(Long shareholderId) {
        this.shareholderId = shareholderId;
    }

}
