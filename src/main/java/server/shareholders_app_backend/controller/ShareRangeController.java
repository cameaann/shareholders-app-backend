package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.service.ShareRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shares")
public class ShareRangeController {

    @Autowired
    private ShareRangeService shareService;

    @GetMapping
    public List<ShareRange> getAllShares() {
        return shareService.getAllShares();
    }

    @GetMapping("/{id}")
    public Optional<ShareRange> getShareById(@PathVariable Long id) {
        return shareService.getShareById(id);
    }

    @PostMapping
    public ShareRange createShare(@RequestBody ShareRange share) {
        // Ensure that the quantity is set correctly or handled as needed
        // If needed, you could perform additional validation here
        return shareService.saveShare(share);
    }

    @DeleteMapping("/{id}")
    public void deleteShare(@PathVariable Long id) {
        shareService.deleteShare(id);
    }
}
