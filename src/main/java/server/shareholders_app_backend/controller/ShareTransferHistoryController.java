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
@RequestMapping("/api/transfer-history")
public class ShareTransferHistoryController {

    @Autowired
    private ShareTransferHistoryService shareTransferHistoryService;

    @GetMapping
    public ResponseEntity<List<ShareTransferHistory>> getAllTransferHistories() {
        List<ShareTransferHistory> transferHistories = shareTransferHistoryService.getAllTransferHistories();
        return ResponseEntity.ok(transferHistories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShareTransferHistory> getTransferHistoryById(@PathVariable Long id) {
        Optional<ShareTransferHistory> transferHistory = shareTransferHistoryService.getTransferHistoryById(id);
        return transferHistory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShareTransferHistory> createTransferHistory(
            @RequestBody ShareTransferHistory transferHistory) {
        ShareTransferHistory createdTransferHistory = shareTransferHistoryService.saveTransferHistory(transferHistory);
        return ResponseEntity.status(201).body(createdTransferHistory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShareTransferHistory> updatePaymentDate(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        Optional<ShareTransferHistory> existingHistory = shareTransferHistoryService.getTransferHistoryById(id);
        if (existingHistory.isPresent()) {
            ShareTransferHistory transferHistory = existingHistory.get();

            // Only update the fields present in the request
            if (updates.containsKey("paymentDate")) {
                String paymentDateStr = (String) updates.get("paymentDate");
                LocalDate paymentDate = LocalDate.parse(paymentDateStr);
                transferHistory.setPaymentDate(paymentDate);
            }

            // Save the updated history
            ShareTransferHistory updatedHistory = shareTransferHistoryService.saveTransferHistory(transferHistory);
            return ResponseEntity.ok(updatedHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
