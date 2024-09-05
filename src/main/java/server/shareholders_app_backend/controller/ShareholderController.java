package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.service.ShareRangeService;
import server.shareholders_app_backend.service.ShareholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/shareholders")
public class ShareholderController {

    @Autowired
    private ShareholderService shareholderService;

    @Autowired
    private ShareRangeService shareRangeService;

    @GetMapping
    public List<Shareholder> getAllShareholders() {
        return shareholderService.getAllShareholders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shareholder> getShareholderById(@PathVariable Long id) {
        Optional<Shareholder> shareholder = shareholderService.getShareholderById(id);
        if (shareholder.isPresent()) {
            return ResponseEntity.ok(shareholder.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Shareholder> createShareholder(@RequestBody Shareholder shareholder) {
        Shareholder savedShareholder = shareholderService.saveShareholder(shareholder);
        return ResponseEntity.ok(savedShareholder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShareholder(@PathVariable Long id) {
        shareholderService.deleteShareholder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{shareholderId}/addShareRange")
    public ResponseEntity<ShareRange> addShareRange(@PathVariable Long shareholderId, @RequestParam int quantity) {
        try {
            ShareRange newShareRange = shareRangeService.addShareRange(shareholderId, quantity);
            return ResponseEntity.ok(newShareRange);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        }
    }
     @PutMapping("/{id}")
    public ResponseEntity<Shareholder> updateShareholder(@PathVariable Long id, @RequestBody Shareholder updatedShareholder) {
        try {
            Shareholder shareholder = shareholderService.updateShareholder(id, updatedShareholder);
            return ResponseEntity.ok(shareholder);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/test")
    public ResponseEntity<Void> testValidation(@RequestBody Shareholder shareholder) {
        // Method body can remain empty; validation will be triggered upon request
        return ResponseEntity.ok().build();
    }
}
