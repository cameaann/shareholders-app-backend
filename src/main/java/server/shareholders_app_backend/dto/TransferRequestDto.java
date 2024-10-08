package server.shareholders_app_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

// Tietosiirto-olio (DTO) osakkeiden omistusoikeuden siirtoa varten
@Data
@NoArgsConstructor
public class TransferRequestDto {

    @NotNull(message = "Luovuttajan ID on pakollinen")
    private Long fromShareholderId; // Myyjä (Luovuttaja)

    @NotNull(message = "Saajan ID on pakollinen")
    private Long toShareholderId; // Ostaja (Saaja)

    @NotNull(message = "Määrä on pakollinen")
    private Integer quantity; // Määrä (kpl)

    @NotNull(message = "Siirtopäivämäärä on pakollinen")
    private LocalDate transferDate; // Siirtopäivämäärä (Saantopäivä)

    private LocalDate paymentDate; // Maksupäivämäärä (Maksupvm), valinnainen
    private boolean transferTax; // Varainsiirtovero (Transfer Tax)
    private Double pricePerShare; // Hinta per osake (Hinta per osake), valinnainen
    private String additionalNotes; // Lisätiedot (Huom.), valinnainen

    // Metodi Suomen varainsiirtoveron (Varainsiirtovero) laskemiseksi
    public double calculateTransferTax() {
        // Tarkista, onko varainsiirtovero sovellettavissa
        if (transferTax) {
            double effectivePricePerShare = (pricePerShare != null) ? pricePerShare : 0.0; // Käytä annettua hintaa tai
                                                                                           // oletuksena 0.0
            double totalValue = quantity * effectivePricePerShare; // Laske osakkeiden kokonaisarvo
            double taxRate = 0.02; // Suomen varainsiirtoveroprosentti (2%)
            return totalValue * taxRate; // Laske ja palauta varainsiirtovero
        }
        return 0.0; // Palauta 0, jos varainsiirtoveroa ei sovelleta
    }
}
