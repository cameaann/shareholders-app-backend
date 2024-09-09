package server.shareholders_app_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransferRequestDto {
    private Long fromShareholderId; // Seller (Luovuttaja / Myyjä)
    private Long toShareholderId; // Buyer (Saaja / Ostaja)
    private int quantity; // Quantity (kpl)

    // Updated fields
    private LocalDate transferDate; // Transfer Date (Saantopäivä)
    private String paymentDate; // Payment Date or Code (Maksupvm), optional
    private String seller; // Seller (Luovuttaja / Myyjä)
    private String buyer; // Buyer (Saaja / Ostaja)
    private boolean transferTax; // Transfer Tax (Varainsiirtovero)
    private double pricePerShare; // Price per Share (Hinta per osake)
    private String additionalNotes; // Additional Notes (Huom.)

    // Method to calculate Finnish transfer tax (Varainsiirtovero)
    public double calculateTransferTax() {
        if (transferTax) {
            double totalValue = quantity * pricePerShare;
            double taxRate = 0.02; // Finnish transfer tax rate (2%)
            return totalValue * taxRate;
        }
        return 0.0;
    }
}
