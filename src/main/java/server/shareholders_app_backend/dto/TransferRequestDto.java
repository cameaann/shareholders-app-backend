package server.shareholders_app_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransferRequestDto {

    @NotNull(message = "From Shareholder ID is required")
    private Long fromShareholderId; // Seller (Luovuttaja / Myyjä)

    @NotNull(message = "To Shareholder ID is required")
    private Long toShareholderId; // Buyer (Saaja / Ostaja)

    @NotNull(message = "Quantity is required")
    private Integer quantity; // Quantity (kpl)

    @NotNull(message = "Transfer Date is required")
    private LocalDate transferDate; // Transfer Date (Saantopäivä)

    private String paymentDate; // Payment Date or Code (Maksupvm), optional
    private boolean transferTax; // Transfer Tax (Varainsiirtovero)
    private Double pricePerShare; // Price per Share (Hinta per osake), optional
    private String additionalNotes; // Additional Notes (Huom.), optional

    // Method to calculate Finnish transfer tax (Varainsiirtovero)
    public double calculateTransferTax() {
        if (transferTax) {
            double effectivePricePerShare = (pricePerShare != null) ? pricePerShare : 0.0;
            double totalValue = quantity * effectivePricePerShare;
            double taxRate = 0.02; // Finnish transfer tax rate (2%)
            return totalValue * taxRate;
        }
        return 0.0;
    }
}
