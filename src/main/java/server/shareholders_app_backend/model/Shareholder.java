package server.shareholders_app_backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import java.text.DecimalFormat;
import java.util.Set;
import server.shareholders_app_backend.config.ApplicationConstants;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shareholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Имя акционера

    private String personalIdOrCompanyId; // Личный идентификатор или идентификатор компании

    private String placeOfResidenceOrHeadquarters; // Место проживания или головной офис

    private String address; // Адрес акционера

    private String emailAddress; // Электронная почта акционера

    private String phoneNumber; // Телефонный номер акционера

    private String bankAccountNumber; // Номер банковского счета

    @OneToMany(mappedBy = "shareholder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ShareRange> shares;

    @Transient // Это поле не будет сохранено в базе данных
    private Integer totalShares;

    @Transient // Это поле не будет сохранено в базе данных
    private String ownershipPercentage;

    @PostLoad
    private void calculateShareDetails() {
        if (shares != null) {
            totalShares = shares.stream().mapToInt(ShareRange::getQuantity).sum();
        } else {
            totalShares = 0;
        }

        double percentage = (totalShares.doubleValue() / ApplicationConstants.TOTAL_SHARES_IN_COMPANY) * 100;

        // Форматируем процент с 4 десятичными знаками
        DecimalFormat df = new DecimalFormat("0.0000");
        ownershipPercentage = df.format(percentage) + "%";
    }
}
