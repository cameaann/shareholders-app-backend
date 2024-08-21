package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.service.ShareholderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<Shareholder> getShareholderById(@PathVariable Long id) {
        return shareholderService.getShareholderById(id);
    }

    @PostMapping
    public Shareholder createShareholder(@RequestBody Shareholder shareholder) {
        return shareholderService.saveShareholder(shareholder);
    }

    @DeleteMapping("/{id}")
    public void deleteShareholder(@PathVariable Long id) {
        shareholderService.deleteShareholder(id);
    }
}
