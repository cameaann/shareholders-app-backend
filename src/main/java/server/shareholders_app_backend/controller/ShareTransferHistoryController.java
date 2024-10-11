package server.shareholders_app_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.shareholders_app_backend.model.ShareTransferHistory;
import server.shareholders_app_backend.service.ShareTransferHistoryService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transfer-history") // Määritellään reitti, jota tämä kontrolleri käsittelee
public class ShareTransferHistoryController {

    @Autowired
    private ShareTransferHistoryService shareTransferHistoryService; // Injektoidaan ShareTransferHistoryService

    // Hae kaikki siirtohistoriat
    @GetMapping
    public ResponseEntity<List<ShareTransferHistory>> getAllTransferHistories() {
        List<ShareTransferHistory> transferHistories = shareTransferHistoryService.getAllTransferHistories();
        return ResponseEntity.ok(transferHistories); // Palauttaa kaikki siirtohistoriat
    }

    // Hae siirtohistoria ID:n perusteella
    @GetMapping("/{id}")
    public ResponseEntity<ShareTransferHistory> getTransferHistoryById(@PathVariable Long id) {
        Optional<ShareTransferHistory> transferHistory = shareTransferHistoryService.getTransferHistoryById(id);
        return transferHistory.map(ResponseEntity::ok) // Palauttaa löytyneen siirtohistorian
                .orElseGet(() -> ResponseEntity.notFound().build()); // Palauttaa 404, jos siirtohistoriaa ei löydy
    }

    // Luo uusi siirtohistoria
    @PostMapping
    public ResponseEntity<ShareTransferHistory> createTransferHistory(
            @RequestBody ShareTransferHistory transferHistory) {
        ShareTransferHistory createdTransferHistory = shareTransferHistoryService.saveTransferHistory(transferHistory);
        return ResponseEntity.status(201).body(createdTransferHistory); // Palauttaa 201, kun siirtohistoria on luotu
    }

    // Päivitä maksupäivämäärä siirtohistorian perusteella
    @PutMapping("/{id}")
    public ResponseEntity<ShareTransferHistory> updatePaymentDate(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        Optional<ShareTransferHistory> existingHistory = shareTransferHistoryService.getTransferHistoryById(id);
        if (existingHistory.isPresent()) {
            ShareTransferHistory transferHistory = existingHistory.get();

            // Päivitetään vain ne kentät, jotka ovat pyynnössä
            if (updates.containsKey("paymentDate")) {
                String paymentDateStr = (String) updates.get("paymentDate");
                LocalDate paymentDate = LocalDate.parse(paymentDateStr);
                transferHistory.setPaymentDate(paymentDate);
            }

            // Tallenna päivitetty historia
            ShareTransferHistory updatedHistory = shareTransferHistoryService.saveTransferHistory(transferHistory);
            return ResponseEntity.ok(updatedHistory); // Palauttaa päivitetyn siirtohistorian
        } else {
            return ResponseEntity.notFound().build(); // Palauttaa 404, jos siirtohistoriaa ei löydy
        }
    }
}
