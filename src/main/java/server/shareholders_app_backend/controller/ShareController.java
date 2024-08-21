package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.model.Share;
import server.shareholders_app_backend.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shares")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @GetMapping
    public List<Share> getAllShares() {
        return shareService.getAllShares();
    }

    @GetMapping("/{id}")
    public Optional<Share> getShareById(@PathVariable Long id) {
        return shareService.getShareById(id);
    }

    @PostMapping
    public Share createShare(@RequestBody Share share) {
        return shareService.saveShare(share);
    }

    @DeleteMapping("/{id}")
    public void deleteShare(@PathVariable Long id) {
        shareService.deleteShare(id);
    }
}