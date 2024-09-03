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

    // Remove alkaen and paattyen fields
    // private int alkaen;
    // private int paattyen;

    @Column(nullable = false)
    private int quantity; // Amount of shares owned

    @ManyToOne
    @JoinColumn(name = "shareholder_id", nullable = false)
    @JsonBackReference
    private Shareholder shareholder;

    // Calculate quantity based on some other logic if needed
    @PostLoad
    public void calculateQuantity() {
        // Implement logic if needed; you might need to set this based on some other
        // conditions
        // For example, if there is another field or calculation to determine quantity,
        // include it here
    }
}
