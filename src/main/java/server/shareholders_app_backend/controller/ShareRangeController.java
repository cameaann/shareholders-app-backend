package server.shareholders_app_backend.controller;

import server.shareholders_app_backend.dto.ShareRangeDTO;
import server.shareholders_app_backend.dto.TransferRequestDto;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.model.ShareTransferHistory;
import server.shareholders_app_backend.service.ShareRangeService;
import server.shareholders_app_backend.service.ShareTransferHistoryService; // Tuodaan palvelu
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/shares") // Määritellään reitti, jota tämä kontrolleri käsittelee
public class ShareRangeController {

    @Autowired
    private ShareRangeService shareRangeService; // Injektoidaan ShareRangeService

    @Autowired
    private ShareTransferHistoryService shareTransferHistoryService; // Injektoidaan ShareTransferHistoryService

    // Hae kaikki osakkeet
    @GetMapping
    public ResponseEntity<List<ShareRangeDTO>> getAllShares() {
        List<ShareRangeDTO> shares = shareRangeService.getAllShares();
        return ResponseEntity.ok(shares); // Palauttaa kaikki osakkeet OK-tilassa
    }

    // Hae osake ID:n perusteella
    @GetMapping("/{id}")
    public ResponseEntity<ShareRangeDTO> getShareById(@PathVariable Long id) {
        Optional<ShareRange> share = shareRangeService.getShareById(id);
        return share.map(shareRange -> ResponseEntity.ok(new ShareRangeDTO(shareRange))) // Palauttaa löytyneen osakkeen
                .orElseGet(() -> ResponseEntity.notFound().build()); // Palauttaa 404, jos osaketta ei löydy
    }

    // Luo uusi osake
    @PostMapping
    public ResponseEntity<ShareRangeDTO> createShare(@RequestBody ShareRangeDTO shareRangeDTO) {
        try {
            ShareRange createdShareRange = shareRangeService.addShareRange(
                    shareRangeDTO.getShareholderId(),
                    shareRangeDTO.getQuantity());
            return ResponseEntity.status(HttpStatus.CREATED) // Palauttaa 201, kun osake on luotu
                    .body(new ShareRangeDTO(createdShareRange));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null); // Virhetilanne, palauttaa 400
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build(); // Palauttaa 404, jos osakkeen luominen epäonnistuu
        }
    }

    // Siirrä osakkeet
    @PostMapping("/transfer")
    public ResponseEntity<?> transferShares(@RequestBody TransferRequestDto transferRequest) {
        try {
            shareRangeService.transferShares(transferRequest);
            List<ShareTransferHistory> history = shareTransferHistoryService.getAllTransferHistories(); // Hae
                                                                                                        // siirtohistoria
            return ResponseEntity.ok(history); // Palauttaa siirtohistorian
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Virhetilanne, palauttaa 400 ja virheviestin
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build(); // Palauttaa 404, jos osakkeiden siirto epäonnistuu
        }
    }

    // Poista osake ID:n perusteella
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShare(@PathVariable Long id) {
        try {
            shareRangeService.deleteShare(id);
            return ResponseEntity.noContent().build(); // Palauttaa 204, jos poisto onnistuu
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build(); // Palauttaa 404, jos osaketta ei löydy
        }
    }
}
