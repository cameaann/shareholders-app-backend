package server.shareholders_app_backend.dto;

public class TransferRequestDto {
    private Long fromShareholderId;
    private Long toShareholderId;
    private int quantity;

    // Getters and setters
    public Long getFromShareholderId() {
        return fromShareholderId;
    }

    public void setFromShareholderId(Long fromShareholderId) {
        this.fromShareholderId = fromShareholderId;
    }

    public Long getToShareholderId() {
        return toShareholderId;
    }

    public void setToShareholderId(Long toShareholderId) {
        this.toShareholderId = toShareholderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
