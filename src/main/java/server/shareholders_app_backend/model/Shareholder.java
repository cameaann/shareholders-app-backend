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
    @Pattern(regexp = "^[a-zA-ZäöåÄÖÅ\\s']+$", message = "Name can only contain letters, spaces, apostrophes, and Finnish characters (ä, ö, å)")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotEmpty(message = "Personal ID or Company ID cannot be empty")
    @Pattern(regexp = "^(\\d{6}[+-A]\\d{3}[0-9A-Y]|\\d{7}-\\d)$", message = "Invalid personal identity code or business ID format")
    @Size(max = 14, message = "Personal ID or Company ID cannot exceed 14 characters")
    private String personalIdOrCompanyId;

    @NotEmpty(message = "Place of residence or headquarters cannot be empty")
    @Pattern(regexp = "^[a-zA-ZäöåÄÖÅ0-9\\s,.'-]+$", message = "Invalid place of residence or headquarters format. Allows letters, numbers, spaces, and Finnish characters (ä, ö, å)")
    @Size(max = 150, message = "Place of residence or headquarters cannot exceed 150 characters")
    private String placeOfResidenceOrHeadquarters;

    @NotEmpty(message = "Address cannot be empty")
    @Pattern(regexp = "^[a-zA-ZäöåÄÖÅ0-9\\s,.'-]+$", message = "Invalid address format. Allows letters, numbers, spaces, and Finnish characters (ä, ö, å)")
    @Size(max = 150, message = "Address cannot exceed 150 characters")
    private String address;

    @NotEmpty(message = "Email address cannot be empty")
    @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format or missing dot in domain")
    @Size(max = 100, message = "Email address cannot exceed 100 characters")
    private String emailAddress;

    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\(\\+358\\)\\s?(?:\\d{2,3}[\\s-]?)?\\d{1,2}[\\s-]?\\d{3,4}[\\s-]?\\d{2,4}$", message = "Invalid phone number format. Expected format: (+358) 9 123 4567 or +358 40 123 4567")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phoneNumber;

    @NotEmpty(message = "Bank account number cannot be empty")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$", message = "Invalid bank account number format")
    @Size(max = 34, message = "Bank account number cannot exceed 34 characters")
    private String bankAccountNumber;

    @OneToMany(mappedBy = "shareholder", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startNumber ASC")
    @JsonManagedReference
    private Set<ShareRange> shares;

    @Transient
    private Integer totalShares;

    @Transient
    private String ownershipPercentage;

    @PostLoad
    public void calculateShareDetails() {
        if (shares != null) {
            totalShares = shares.stream().mapToInt(ShareRange::getQuantity).sum();
        } else {
            totalShares = 0;
        }

        double percentage = (totalShares.doubleValue() / ApplicationConstants.TOTAL_SHARES_IN_COMPANY) * 100;
        DecimalFormat df = new DecimalFormat("0.0000");
        ownershipPercentage = df.format(percentage) + "%";
    }

    public Integer getTotalShares() {
        return totalShares;
    }
}
