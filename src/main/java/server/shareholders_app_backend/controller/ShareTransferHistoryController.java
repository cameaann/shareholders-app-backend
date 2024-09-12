package server.shareholders_app_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.shareholders_app_backend.model.ShareTransferHistory;
import server.shareholders_app_backend.service.ShareTransferHistoryService;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<ShareTransferHistory> createTransferHistory(@RequestBody ShareTransferHistory transferHistory) {
        ShareTransferHistory createdTransferHistory = shareTransferHistoryService.saveTransferHistory(transferHistory);
        return ResponseEntity.status(201).body(createdTransferHistory);
    }
}
