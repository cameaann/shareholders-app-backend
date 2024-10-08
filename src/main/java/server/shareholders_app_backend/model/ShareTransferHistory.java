package server.shareholders_app_backend.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

// Data-luokka, joka kuvaa osakkeiden siirtohistorian tietomallia
@Data
@Entity
@Table(name = "share_transfer_history") // Määrittelee taulun nimen tietokannassa
public class ShareTransferHistory {

    @Id // Määrittelee kentän ensisijaiseksi avaimiksi
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automaattinen arvon generointi tietokannassa
    private Long id; // Siirtohistorian tunniste

    @Column(nullable = false) // Määrittää, että kenttä ei voi olla tyhjää
    private Long fromShareholderId; // Lähettäjän osakkeenomistajan ID

    @Column(nullable = false) // Määrittää, että kenttä ei voi olla tyhjää
    private Long toShareholderId; // Vastaanottajan osakkeenomistajan ID

    @Column(nullable = false) // Määrittää, että kenttä ei voi olla tyhjää
    private int quantity; // Siirrettävien osakkeiden määrä

    @Column(nullable = false) // Määrittää, että kenttä ei voi olla tyhjää
    private LocalDate transferDate; // Siirron päivämäärä

    @Column // Kenttä, joka voi olla tyhjää
    private LocalDate paymentDate; // Maksun päivämäärä (valinnainen)

    @Column // Kenttä, joka voi olla tyhjää
    private boolean transferTax; // Indikaattori siirtoverosta

    @Column // Kenttä, joka voi olla tyhjää
    private double pricePerShare; // Osakkeen hinta per siirto

    @Column // Kenttä, joka voi olla tyhjää
    private String additionalNotes; // Lisätiedot siirrosta (valinnainen)

    @Column // Kenttä, joka voi olla tyhjää
    private double totalAmount; // Kokonaissumma siirrosta
}
