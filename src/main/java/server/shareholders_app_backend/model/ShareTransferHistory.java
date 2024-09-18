package server.shareholders_app_backend.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "share_transfer_history")
public class ShareTransferHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long fromShareholderId;

    @Column(nullable = false)
    private Long toShareholderId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDate transferDate;

    @Column
    private LocalDate paymentDate;

    @Column
    private boolean transferTax;

    @Column
    private double pricePerShare;

    @Column
    private String additionalNotes;

    @Column
    private double totalAmount;
}
