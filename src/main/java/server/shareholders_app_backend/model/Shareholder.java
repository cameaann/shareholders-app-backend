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

    private String name; // Shareholder's name

    private Double ownershipPercentage; // Ownership percentage

    private String personalIdOrCompanyId; // Personal ID or company identifier

    private String placeOfResidenceOrHeadquarters; // Place of residence or headquarters

    private String address; // Address of the shareholder

    private String emailAddress; // Email address of the shareholder

    private String phoneNumber; // Phone number of the shareholder

    private String bankAccountNumber; // Bank account number

    @OneToMany(mappedBy = "shareholder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Share> shares;

    @Transient // This field will not be persisted in the database
    private Integer totalShares;

    @PostLoad
    private void calculateTotalShares() {
        if (shares != null) {
            totalShares = shares.stream().mapToInt(Share::getQuantity).sum();
        } else {
            totalShares = 0;
        }
    }
}
