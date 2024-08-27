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

    private Integer numberOfShares; // Number of shares owned

    private Double ownershipPercentage; // Ownership percentage

    private String personalIdOrCompanyId; // Personal ID or company identifier

    private String residenceOrHeadquarters; // Place of residence or headquarters

    private String contactInformation; // Contact information

    private String bankAccountNumber; // Bank account number

    @OneToMany(mappedBy = "shareholder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Share> shares;
}
