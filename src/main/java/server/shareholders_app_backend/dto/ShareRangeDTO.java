package server.shareholders_app_backend.dto;

import lombok.Getter;
import lombok.Setter;
import server.shareholders_app_backend.model.ShareRange;

@Getter
@Setter
public class ShareRangeDTO {
    private Long id;
    private int quantity;
    private int startNumber;
    private int endNumber;
    private Long shareholderId;

    // Конструктор для создания DTO из модели ShareRange
    public ShareRangeDTO(ShareRange shareRange) {
        this.id = shareRange.getId();
        this.quantity = shareRange.getQuantity();
        this.startNumber = shareRange.getStartNumber();
        this.endNumber = shareRange.getEndNumber();
        this.shareholderId = shareRange.getShareholder() != null ? shareRange.getShareholder().getId() : null;
    }

    // Пустой конструктор для десериализации JSON
    public ShareRangeDTO() {
    }
}
