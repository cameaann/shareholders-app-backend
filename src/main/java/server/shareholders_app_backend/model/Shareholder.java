package server.shareholders_app_backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import java.util.Set;

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

    private Double ownershipPercentage; // Процент владения

    private String personalIdOrCompanyId; // Личный идентификатор или идентификатор компании

    private String placeOfResidenceOrHeadquarters; // Место жительства или штаб-квартира

    private String address; // Адрес акционера

    private String emailAddress; // Электронная почта акционера

    private String phoneNumber; // Номер телефона акционера

    private String bankAccountNumber; // Номер банковского счета

    @OneToMany(mappedBy = "shareholder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Share> shares;

    @Transient // Это поле не будет сохранено в базе данных
    private Integer totalShares; // Общее количество акций

    private Integer startNumber; // Начальный номер

    private Integer endNumber; // Конечный номер
     // Метод для получения общего количества акций
     public Integer getTotalShares() {
        return totalShares;
    }

    // Метод для установки общего количества акций
    public void setTotalShares(int totalShares) {
        this.totalShares = totalShares;
    }

    
}
