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
    private int alkaen; // "From"
    private int paattyen;

    @Transient // This field will not be persisted in the database
    private int quantity; // Amount of shares owned

    @ManyToOne
    @JoinColumn(name = "shareholder_id", nullable = false) // Adding nullable = false for integrity
    @JsonBackReference
    private Shareholder shareholder;

    // Calculate quantity based on alkaen and paattyen
    @PostLoad
    public void calculateQuantity() {
        this.quantity = this.paattyen - this.alkaen + 1;
    }
}
