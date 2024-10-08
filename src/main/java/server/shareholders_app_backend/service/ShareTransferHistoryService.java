package server.shareholders_app_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.shareholders_app_backend.model.ShareTransferHistory;
import server.shareholders_app_backend.repository.ShareTransferHistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShareTransferHistoryService {

    @Autowired
    private ShareTransferHistoryRepository shareTransferHistoryRepository;

    // Hakee kaikki siirtohistoriat
    public List<ShareTransferHistory> getAllTransferHistories() {
        return shareTransferHistoryRepository.findAll(); // Palauttaa kaikki siirtohistoriat
    }

    // Hakee siirtohistorian ID:n perusteella
    public Optional<ShareTransferHistory> getTransferHistoryById(Long id) {
        return shareTransferHistoryRepository.findById(id); // Palauttaa valitun siirtohistorian, jos se löytyy
    }

    // Tallentaa uuden siirtohistorian
    public ShareTransferHistory saveTransferHistory(ShareTransferHistory transferHistory) {
        return shareTransferHistoryRepository.save(transferHistory); // Tallentaa siirtohistorian tietokantaan
    }

    // Päivittää olemassa olevan siirtohistorian
    public ShareTransferHistory updateTransferHistory(Long id, ShareTransferHistory updatedTransferHistory) {
        return shareTransferHistoryRepository.findById(id)
                .map(existingTransferHistory -> {
                    // Päivittää olemassa olevat kentät uusilla arvoilla
                    existingTransferHistory.setFromShareholderId(updatedTransferHistory.getFromShareholderId());
                    existingTransferHistory.setToShareholderId(updatedTransferHistory.getToShareholderId());
                    existingTransferHistory.setQuantity(updatedTransferHistory.getQuantity());
                    existingTransferHistory.setTransferDate(updatedTransferHistory.getTransferDate());
                    existingTransferHistory.setPaymentDate(updatedTransferHistory.getPaymentDate());
                    existingTransferHistory.setTransferTax(updatedTransferHistory.isTransferTax());
                    existingTransferHistory.setPricePerShare(updatedTransferHistory.getPricePerShare());
                    existingTransferHistory.setAdditionalNotes(updatedTransferHistory.getAdditionalNotes());
                    return shareTransferHistoryRepository.save(existingTransferHistory); // Tallentaa päivitetyn
                                                                                         // siirtohistorian
                })
                .orElseThrow(() -> new RuntimeException("Siirtohistoriaa ei löytynyt ID:llä " + id)); // Poikkeus, jos
                                                                                                      // siirtohistoriaa
                                                                                                      // ei löydy
    }
}
