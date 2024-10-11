package server.shareholders_app_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import server.shareholders_app_backend.dto.ShareRangeDTO;
import server.shareholders_app_backend.dto.TransferRequestDto;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.model.ShareTransferHistory;
import server.shareholders_app_backend.repository.ShareRangeRepository;
import server.shareholders_app_backend.repository.ShareholderRepository;
import server.shareholders_app_backend.repository.ShareTransferHistoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShareRangeService {

    @Autowired
    private ShareRangeRepository shareRepository;

    @Autowired
    private ShareholderRepository shareholderRepository;

    @Autowired
    private ShareTransferHistoryRepository historyRepository;

    // Siirtää osakkeet osakkeenomistajien välillä
    @Transactional
    public void transferShares(TransferRequestDto transferRequest) {
        // Hakee osakkeenomistajan, jolta osakkeet siirretään
        Shareholder fromShareholder = shareholderRepository.findById(transferRequest.getFromShareholderId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Osakkeenomistajaa ID:llä " + transferRequest.getFromShareholderId() + " ei löytynyt"));

        // Hakee osakkeenomistajan, jolle osakkeet siirretään
        Shareholder toShareholder = shareholderRepository.findById(transferRequest.getToShareholderId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Osakkeenomistajaa ID:llä " + transferRequest.getToShareholderId() + " ei löytynyt"));

        int quantityToTransfer = transferRequest.getQuantity(); // Siirrettävien osakkeiden määrä
        int remainingQuantity = quantityToTransfer; // Jäljellä oleva osakkeiden määrä siirtoa varten

        // Hakee ja lajittelee osakejoukot alkuluku mukaan
        List<ShareRange> shareRanges = shareRepository.findAllOrderedByStartNumber();
        for (ShareRange shareRange : shareRanges) {
            // Tarkistaa, onko osakejoukko lähteeltä osakkeenomistajalta
            if (shareRange.getShareholder().equals(fromShareholder)) {
                if (remainingQuantity <= 0) // Jos kaikki osakkeet on siirretty, lopetetaan silmukka
                    break;

                int availableQuantity = shareRange.getQuantity(); // Saatavilla olevien osakkeiden määrä
                if (availableQuantity <= remainingQuantity) {
                    remainingQuantity -= availableQuantity; // Vähennetään siirrettävien osakkeiden määrä
                    shareRange.setShareholder(toShareholder); // Muutetaan osakkeenomistajaa
                    shareRepository.save(shareRange); // Tallennetaan muutokset
                } else {
                    // Luodaan uusi osakejoukko, jos saatavilla olevat osakkeet riittävät
                    int newStartNumber = shareRange.getStartNumber() + remainingQuantity;
                    ShareRange newShareRange = new ShareRange();
                    newShareRange.setQuantity(remainingQuantity);
                    newShareRange.setStartNumber(shareRange.getStartNumber());
                    newShareRange.setEndNumber(newStartNumber - 1);
                    newShareRange.setShareholder(toShareholder);

                    // Vähennetään siirrettävien osakkeiden määrä alkuperäisestä osakejoukosta
                    shareRange.setQuantity(availableQuantity - remainingQuantity);
                    shareRange.setStartNumber(newStartNumber);

                    shareRepository.save(newShareRange); // Tallennetaan uusi osakejoukko
                    shareRepository.save(shareRange); // Tallennetaan päivitetty alkuperäinen osakejoukko
                    remainingQuantity = 0; // Kaikki osakkeet on siirretty
                }
            }
        }

        // Tarkistetaan, että kaikki osakkeet on siirretty
        if (remainingQuantity > 0) {
            throw new IllegalStateException("Ei tarpeeksi osakkeita siirrettäväksi.");
        }

        // Tallennetaan siirtohistoria
        ShareTransferHistory history = new ShareTransferHistory();
        history.setFromShareholderId(transferRequest.getFromShareholderId());
        history.setToShareholderId(transferRequest.getToShareholderId());
        history.setQuantity(transferRequest.getQuantity());
        history.setTransferDate(transferRequest.getTransferDate()); // Varmista, että tämä on asetettu
        history.setPaymentDate(transferRequest.getPaymentDate()); // Valinnainen
        history.setTransferTax(transferRequest.isTransferTax());

        // Käytetään oletusarvoa, jos pricePerShare on null
        double pricePerShare = (transferRequest.getPricePerShare() != null) ? transferRequest.getPricePerShare() : 0.0;
        history.setPricePerShare(pricePerShare); // Valinnainen

        // Lasketaan kokonaissumma osakkeiden määrän ja osakkeen hinnan perusteella
        double totalAmount = pricePerShare * transferRequest.getQuantity();
        history.setTotalAmount(totalAmount);

        history.setAdditionalNotes(transferRequest.getAdditionalNotes()); // Valinnainen

        historyRepository.save(history); // Tallennetaan siirtohistoria
    }

    // Hakee kaikki osakejoukot
    public List<ShareRangeDTO> getAllShares() {
        return shareRepository.findAllOrderedByStartNumber()
                .stream()
                .map(ShareRangeDTO::new) // Muuntaa ShareRange-oliot ShareRangeDTO-olioiksi
                .collect(Collectors.toList());
    }

    // Hakee osakejoukon ID:n perusteella
    public Optional<ShareRange> getShareById(Long id) {
        return shareRepository.findById(id);
    }

    // Lisää uuden osakejoukon
    @Transactional
    public ShareRange addShareRange(Long shareholderId, int quantity) {
        Shareholder shareholder = shareholderRepository.findById(shareholderId)
                .orElseThrow(
                        () -> new NoSuchElementException("Osakkeenomistajaa ID:llä " + shareholderId + " ei löytynyt"));

        Integer currentMaxEndNumber = shareRepository.findMaxEndNumber();
        if (currentMaxEndNumber == null) {
            currentMaxEndNumber = 0; // Alustetaan nollaksi, jos ei ole olemassa
        }

        int startNumber = currentMaxEndNumber + 1; // Alkamisluku uudelle osakejoukolle
        int endNumber = startNumber + quantity - 1; // Loppuluku

        ShareRange shareRange = new ShareRange();
        shareRange.setQuantity(quantity);
        shareRange.setStartNumber(startNumber);
        shareRange.setEndNumber(endNumber);
        shareRange.setShareholder(shareholder); // Asetetaan osakkeenomistaja

        return shareRepository.save(shareRange); // Tallennetaan osakejoukko
    }

    // Poistaa osakejoukon ID:n perusteella
    @Transactional
    public void deleteShare(Long id) {
        if (shareRepository.existsById(id)) {
            shareRepository.deleteById(id); // Poistaa osakejoukon
        } else {
            throw new NoSuchElementException("ShareRange ID:llä " + id + " ei löytynyt.");
        }
    }
}
