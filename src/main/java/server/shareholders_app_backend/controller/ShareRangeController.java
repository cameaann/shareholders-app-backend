package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.dto.ShareRangeDTO;
import server.shareholders_app_backend.dto.TransferRequestDto;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.service.ShareRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ShareRangeDTO>> getAllShares() {
        List<ShareRangeDTO> shares = shareRangeService.getAllShares();
        return ResponseEntity.ok(shares);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShareRangeDTO> getShareById(@PathVariable Long id) {
        Optional<ShareRange> share = shareRangeService.getShareById(id);
        return share.map(shareRange -> ResponseEntity.ok(new ShareRangeDTO(shareRange)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShareRangeDTO> createShare(@RequestBody ShareRangeDTO shareRangeDTO) {
        try {
            ShareRange createdShareRange = shareRangeService.addShareRange(
                    shareRangeDTO.getShareholderId(),
                    shareRangeDTO.getQuantity());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ShareRangeDTO(createdShareRange));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferShares(@RequestBody TransferRequestDto transferRequest) {
        try {
            shareRangeService.transferShares(transferRequest);
            return ResponseEntity.ok("Shares transferred successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
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
