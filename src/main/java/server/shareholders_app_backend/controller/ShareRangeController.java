package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.dto.ShareRangeDTO;
import server.shareholders_app_backend.dto.TransferRequestDto;
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
    private ShareRangeService shareRangeService;

    @GetMapping
    public List<ShareRangeDTO> getAllShares() {
        return shareRangeService.getAllShares();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShareRange> getShareById(@PathVariable Long id) {
        Optional<ShareRange> share = shareRangeService.getShareById(id);
        return share.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShareRange> createShare(@RequestBody ShareRangeDTO shareRangeDTO) {
        try {
            ShareRange createdShareRange = shareRangeService.addShareRange(
                    shareRangeDTO.getShareholderId(),
                    shareRangeDTO.getQuantity());
            return ResponseEntity.ok(createdShareRange);
        } catch (IllegalStateException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferShares(@RequestBody TransferRequestDto transferRequest) {
        try {
            shareRangeService.transferShares(transferRequest);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException | NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShare(@PathVariable Long id) {
        try {
            shareRangeService.deleteShare(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
