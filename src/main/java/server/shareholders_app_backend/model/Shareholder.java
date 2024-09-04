package server.shareholders_app_backend.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import server.shareholders_app_backend.config.ApplicationConstants;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.text.DecimalFormat;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shareholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name; // Shareholder's name

    @NotEmpty(message = "Personal ID or Company ID cannot be empty")
    // @Pattern(regexp =
    // "^(?:[0-3][0-9][0-1][0-9][0-9][0-9][-+A-Y][02468][0-9]{2}[0-9A-Y]|\\d{7}-\\d)$",
    // message = "Invalid Personal ID or Company ID format")
    @Pattern(regexp = "^(\\d{2})(0[1-9]|1[0-2])(\\d{2})([-+A](?:\\d{3}[0-9A-Y]))$|^(\\d{7})-(\\d)$", message = "Invalid personal identity code or business ID format")
    private String personalIdOrCompanyId;

    @NotEmpty(message = "Place of residence or headquarters cannot be empty")
    private String placeOfResidenceOrHeadquarters; // Place of residence or headquarters

    @NotEmpty(message = "Address cannot be empty")
    private String address; // Shareholder's address

    @NotEmpty(message = "Email address cannot be empty")
    @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format or missing dot in domain")
    private String emailAddress; // Shareholder's email address

    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    private String phoneNumber; // Shareholder's phone number

    @NotEmpty(message = "Bank account number cannot be empty")
    private String bankAccountNumber; // Bank account number

    @OneToMany(mappedBy = "shareholder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ShareRange> shares;

    @Transient // This field will not be saved in the database
    private Integer totalShares;

    @Transient // This field will not be saved in the database
    private String ownershipPercentage;

    @PostLoad
    private void calculateShareDetails() {
        if (shares != null) {
            totalShares = shares.stream().mapToInt(ShareRange::getQuantity).sum();
        } else {
            totalShares = 0;
        }

        double percentage = (totalShares.doubleValue() / ApplicationConstants.TOTAL_SHARES_IN_COMPANY) * 100;

        // Format percentage with 4 decimal places
        DecimalFormat df = new DecimalFormat("0.0000");
        ownershipPercentage = df.format(percentage) + "%";
    }
}
