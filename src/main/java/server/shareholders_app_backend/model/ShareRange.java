package server.shareholders_app_backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

// Entiteetti, joka kuvaa osakealueita
@Entity
@Getter
@Setter
@NoArgsConstructor // Ilmaisee, että luokalla on oletuskonstruktori
@AllArgsConstructor // Luo konstruktorin kaikille kentille
public class ShareRange {

    @Id // Määrittelee kentän ensisijaiseksi avaimiksi
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automaattinen arvon generointi tietokannassa
    private Long id; // Osakealueen tunniste

    @Column(nullable = false) // Määrittää, että kenttä ei voi olla tyhjää
    private int quantity; // Omistettujen osakkeiden määrä

    @Column(nullable = false) // Määrittää, että kenttä ei voi olla tyhjää
    private int startNumber; // Lasketut osakkeiden alkanumerot

    @Column(nullable = false) // Määrittää, että kenttä ei voi olla tyhjää
    private int endNumber; // Lasketut osakkeiden päätunnus

    @ManyToOne // Määrittää monimutkaisen yhteyden Shareholder-oliolle
    @JoinColumn(name = "shareholder_id", nullable = false) // Määrittää yhdistettävän sarakkeen tietokannassa
    @JsonBackReference // Estää syklisen viittauksen JSON-sarjennuksessa
    private Shareholder shareholder; // Osakkeenomistaja, jolle osakealue kuuluu
}
