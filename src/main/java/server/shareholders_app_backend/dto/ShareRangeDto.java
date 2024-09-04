package server.shareholders_app_backend.dto;

public class ShareRangeDto {
    private Long shareholderId;
    private int quantity;

    // Getters and setters
    public Long getShareholderId() {
        return shareholderId;
    }

    public void setShareholderId(Long shareholderId) {
        this.shareholderId = shareholderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
