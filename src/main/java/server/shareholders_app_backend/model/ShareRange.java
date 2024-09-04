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
public class ShareRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity; // Amount of shares owned

    @Column(nullable = false)
    private int startNumber; // Calculated start number of shares

    @Column(nullable = false)
    private int endNumber; // Calculated end number of shares

    @ManyToOne
    @JoinColumn(name = "shareholder_id", nullable = false)
    @JsonBackReference
    private Shareholder shareholder;

    // No need for @PostLoad if not calculating quantity directly
}
