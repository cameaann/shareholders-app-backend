package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.dto.ShareRangeDTO;
import server.shareholders_app_backend.dto.ShareholderWithSharesDTO;
import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.service.ShareholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/shareholders")
public class ShareholderController {

    @Autowired
    private ShareholderService shareholderService;

    @GetMapping
    public List<Shareholder> getAllShareholders() {
        return shareholderService.getAllShareholders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shareholder> getShareholderById(@PathVariable Long id) {
        try {
            Shareholder shareholder = shareholderService.getShareholderById(id).orElseThrow();
            return ResponseEntity.ok(shareholder);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Shareholder> createShareholderWithShares(@RequestBody ShareholderWithSharesDTO dto) {
        try {
            Shareholder createdShareholder = shareholderService.saveShareholderWithShares(dto);
            return ResponseEntity.ok(createdShareholder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShareholder(@PathVariable Long id, @RequestBody Shareholder updatedShareholder) {
        try {
            Shareholder shareholder = shareholderService.updateShareholder(id, updatedShareholder);
            return ResponseEntity.ok(shareholder);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShareholder(@PathVariable Long id) {
        try {
            shareholderService.deleteShareholder(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
