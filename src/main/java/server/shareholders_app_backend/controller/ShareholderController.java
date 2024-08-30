package server.shareholders_app_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.service.ShareholderService;

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
    public Optional<Shareholder> getShareholderById(@PathVariable Long id) {
        return shareholderService.getShareholderById(id);
    }

    @PostMapping("/addOrUpdate")
    public ResponseEntity<Shareholder> createOrUpdateShareholder(@RequestBody Shareholder shareholder) {
        Shareholder updatedShareholder = shareholderService.saveShareholder(shareholder);
        return ResponseEntity.ok(updatedShareholder);
    }

    @DeleteMapping("/{id}")
    public void deleteShareholder(@PathVariable Long id) {
        shareholderService.deleteShareholder(id);
    }

    @GetMapping("/calculateTotalShares")
    public ResponseEntity<Shareholder> calculateTotalShares(@RequestParam Long id) {
        Optional<Shareholder> optionalShareholder = shareholderService.getShareholderById(id);
        if (optionalShareholder.isPresent()) {
            Shareholder updatedShareholder = shareholderService.calculateAndSaveTotalShares(optionalShareholder.get());
            return ResponseEntity.ok(updatedShareholder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allIds")
    public ResponseEntity<List<Long>> getAllShareholderIds() {
        List<Long> ids = shareholderService.getAllShareholderIds();
        return ResponseEntity.ok(ids);
    }
}
