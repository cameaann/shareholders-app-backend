package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.dto.ShareRangeDto;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.service.ShareRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
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
    public ResponseEntity<ShareRange> getShareById(@PathVariable Long id) {
        Optional<ShareRange> share = shareService.getShareById(id);
        return share.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShareRange> createShare(@RequestBody ShareRangeDto shareRangeDto) {
        try {
            ShareRange createdShareRange = shareService.addShareRange(
                    shareRangeDto.getShareholderId(),
                    shareRangeDto.getQuantity());
            return ResponseEntity.ok(createdShareRange);
        } catch (IllegalStateException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShare(@PathVariable Long id) {
        try {
            shareService.deleteShare(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
