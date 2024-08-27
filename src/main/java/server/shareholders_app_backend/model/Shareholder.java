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

    private String name;

    private Integer maara; // Number of shares

    private Double omistusPercentage; // Ownership percentage

    private String hetuYtunnus; // Personal ID or company identifier

    private String kotipaikka; // Place of residence or headquarters

    private String yhteystiedot; // Contact information

    private String tilinro; // Bank account number

    @OneToMany(mappedBy = "shareholder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Share> shares;
}
