package server.shareholders_app_backend.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import server.shareholders_app_backend.config.ApplicationConstants;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.text.DecimalFormat;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonGetter;

@Entity // Ilmaisee, että tämä luokka on JPA-entiteetti
@Getter // Generoi getter-metodit
@Setter // Generoi setter-metodit
@NoArgsConstructor // Ilmaisee, että luokalla on oletuskonstruktori
@AllArgsConstructor // Luo konstruktorin kaikille kentille
public class Shareholder {

    @Id // Määrittelee kentän ensisijaiseksi avaimiksi
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automaattinen arvon generointi tietokannassa
    private Long id; // Osakkeenomistajan tunniste

    @NotEmpty(message = "Name cannot be empty") // Kenttä ei voi olla tyhjää
    @Pattern(regexp = "^[a-zA-ZäöåÄÖÅ\\s']+$", message = "Name can only contain letters, spaces, apostrophes, and Finnish characters (ä, ö, å)")
    @Size(max = 100, message = "Name cannot exceed 100 characters") // Kenttä ei voi ylittää 100 merkkiä
    private String name; // Osakkeenomistajan nimi

    @NotEmpty(message = "Personal ID or Company ID cannot be empty") // Kenttä ei voi olla tyhjää
    @Pattern(regexp = "^(\\d{6}[+-A]\\d{3}[0-9A-Y]|\\d{7}-\\d)$", message = "Invalid personal identity code or business ID format")
    @Size(max = 14, message = "Personal ID or Company ID cannot exceed 14 characters") // Kenttä ei voi ylittää 14
                                                                                       // merkkiä
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Kenttä on vain kirjoitettavissa, ei luettavissa
    private String personalIdOrCompanyId; // Henkilö- tai yritystunnus

    @NotEmpty(message = "Place of residence or headquarters cannot be empty") // Kenttä ei voi olla tyhjää
    @Pattern(regexp = "^[a-zA-ZäöåÄÖÅ0-9\\s,.'-]+$", message = "Invalid place of residence or headquarters format. Allows letters, numbers, spaces, and Finnish characters (ä, ö, å)")
    @Size(max = 150, message = "Place of residence or headquarters cannot exceed 150 characters") // Kenttä ei voi
                                                                                                  // ylittää 150 merkkiä
    private String placeOfResidenceOrHeadquarters; // Asuinpaikka tai pääkonttori

    @NotEmpty(message = "Address cannot be empty") // Kenttä ei voi olla tyhjää
    @Pattern(regexp = "^[a-zA-ZäöåÄÖÅ0-9\\s,.'-]+$", message = "Invalid address format. Allows letters, numbers, spaces, and Finnish characters (ä, ö, å)")
    @Size(max = 150, message = "Address cannot exceed 150 characters") // Kenttä ei voi ylittää 150 merkkiä
    private String address; // Osoite

    @NotEmpty(message = "Email address cannot be empty") // Kenttä ei voi olla tyhjää
    @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format or missing dot in domain")
    @Size(max = 100, message = "Email address cannot exceed 100 characters") // Kenttä ei voi ylittää 100 merkkiä
    private String emailAddress; // Sähköpostiosoite

    @NotEmpty(message = "Phone number cannot be empty") // Kenttä ei voi olla tyhjää
    @Pattern(regexp = "^\\(\\+358\\)\\s?(?:\\d{2,3}[\\s-]?)?\\d{1,2}[\\s-]?\\d{3,4}[\\s-]?\\d{2,4}$", message = "Invalid phone number format. Expected format: (+358) 9 123 4567 or +358 40 123 4567")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters") // Kenttä ei voi ylittää 20 merkkiä
    private String phoneNumber; // Puhelinnumero

    @NotEmpty(message = "Bank account number cannot be empty") // Kenttä ei voi olla tyhjää
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$", message = "Invalid bank account number format")
    @Size(max = 34, message = "Bank account number cannot exceed 34 characters") // Kenttä ei voi ylittää 34 merkkiä
    private String bankAccountNumber; // Pankkitilin numero

    @OneToMany(mappedBy = "shareholder", cascade = CascadeType.ALL, orphanRemoval = true) // Määrittää
                                                                                          // yhteenmonimutkaisen suhteen
                                                                                          // ShareRange-oliolle
    @OrderBy("startNumber ASC") // Järjestää osakealueet alkanumeron mukaan nousevaan järjestykseen
    @JsonManagedReference // Hallinnoi syklisiä viittauksia JSON-sarjennuksessa
    private Set<ShareRange> shares; // Osakkeet, joita osakkeenomistajalla on

    @Transient // Kenttä, jota ei tallenneta tietokantaan
    private Integer totalShares; // Yhteensä omistetut osakkeet

    @Transient // Kenttä, jota ei tallenneta tietokantaan
    private String ownershipPercentage; // Omistusprosentti

    // Metodi lasketaan osakeyksityiskohdat, kun olio ladataan tietokannasta
    @PostLoad
    public void calculateShareDetails() {
        if (shares != null) {
            totalShares = shares.stream().mapToInt(ShareRange::getQuantity).sum(); // Lasketaan osakkeet
        } else {
            totalShares = 0; // Jos ei ole osakkeita, asetetaan nollaksi
        }

        // Lasketaan omistusprosentti
        double percentage = (totalShares.doubleValue() / ApplicationConstants.TOTAL_SHARES_IN_COMPANY) * 100; // Käytetään
                                                                                                              // vakioarvoa
        DecimalFormat df = new DecimalFormat("0.0000"); // Määritetään muoto
        ownershipPercentage = df.format(percentage) + "%"; // Muotoillaan prosentti
    }

    public Integer getTotalShares() {
        return totalShares; // Palautetaan omistettujen osakkeiden määrä
    }

    // Maskaa henkilö- tai yritystunnuksen
    @JsonGetter("personalIdOrCompanyId")
    public String getMaskedPersonalIdOrCompanyId() {
        if (personalIdOrCompanyId != null && personalIdOrCompanyId.length() > 4) {
            int length = personalIdOrCompanyId.length();
            return personalIdOrCompanyId.substring(0, length - 4) + "****"; // Maskaa viimeiset neljä merkkiä
        }
        return personalIdOrCompanyId; // Palauttaa alkuperäisen, jos se on liian lyhyt
    }
}
