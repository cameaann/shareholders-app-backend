package server.shareholders_app_backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Share {

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

    private String bankAccountNumber; 

    private int startNumber;
    private int endNumber;

    private int quantity; // Amount of shares owned

    @ManyToOne
    @JoinColumn(name = "shareholder_id", nullable = false) // Adding nullable = false for integrity
    @JsonBackReference
    private Shareholder shareholder;
}
