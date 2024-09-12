package server.shareholders_app_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import server.shareholders_app_backend.dto.MetadataDTO;
import server.shareholders_app_backend.dto.ShareRangeDTO;
import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.service.ShareholderService;

import java.util.Optional;

@RestController
@RequestMapping("/api/metadata")
public class MetaDataController {
    @Autowired
    ShareholderService shareholderService;

    @GetMapping()
    public ResponseEntity<?> getMetaData() {
        Optional<Shareholder> mainCompany = shareholderService.getShareholderById(1L);
        if (mainCompany.isPresent()) {
        MetadataDTO response = new MetadataDTO(mainCompany.get().getTotalShares());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shareholder not found");
        }
    }

}
