package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.service.ShareholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/test")
    public ResponseEntity<Void> testValidation(@RequestBody Shareholder shareholder) {
        // Method body can remain empty; validation will be triggered upon request
        return ResponseEntity.ok().build();
    }
}
