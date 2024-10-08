package server.shareholders_app_backend.service;

import server.shareholders_app_backend.dto.ShareRangeDTO;
import server.shareholders_app_backend.dto.ShareholderWithSharesDTO;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.repository.ShareholderRepository;
import server.shareholders_app_backend.exception.ShareholderDeletionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;
import java.util.Set;

@Service
public class ShareholderService {

    @Autowired
    private ShareholderRepository shareholderRepository;

    @Autowired
    private ShareRangeService shareRangeService;

    // Hakee kaikki osakkeenomistajat tietokannasta
    public List<Shareholder> getAllShareholders() {
        return shareholderRepository.findAll();
    }

    // Hakee osakkeenomistajan ID:n perusteella
    public Optional<Shareholder> getShareholderById(Long id) {
        Optional<Shareholder> shareholderOptional = shareholderRepository.findById(id);
        if (shareholderOptional.isPresent()) {
            Shareholder shareholder = shareholderOptional.get();
            Set<ShareRange> shareSet = shareholder.getShares();
            if (shareSet != null) {
                List<ShareRange> shareList = new ArrayList<>(shareSet);
                // Lajittelee osakejoukon ID:n mukaan
                shareList.sort(Comparator.comparing(ShareRange::getId));
                shareholder.setShares(new LinkedHashSet<>(shareList)); // Valinnainen osakkeiden asettaminen
            }
            return Optional.of(shareholder);
        }
        return Optional.empty(); // Jos osakkeenomistajaa ei löydy, palauttaa tyhjän vaihtoehdon
    }

    // Päivittää osakkeenomistajan tiedot
    public Shareholder updateShareholder(Long id, Shareholder updatedShareholder) {
        return shareholderRepository.findById(id)
                .map(shareholder -> {
                    shareholder.setName(updatedShareholder.getName());
                    if (updatedShareholder.getPersonalIdOrCompanyId() != null) {
                        shareholder.setPersonalIdOrCompanyId(updatedShareholder.getPersonalIdOrCompanyId());
                    }
                    shareholder
                            .setPlaceOfResidenceOrHeadquarters(updatedShareholder.getPlaceOfResidenceOrHeadquarters());
                    shareholder.setAddress(updatedShareholder.getAddress());
                    shareholder.setEmailAddress(updatedShareholder.getEmailAddress());
                    shareholder.setPhoneNumber(updatedShareholder.getPhoneNumber());
                    shareholder.setBankAccountNumber(updatedShareholder.getBankAccountNumber());
                    // Tallentaa päivitetyn osakkeenomistajan tietokantaan
                    return shareholderRepository.save(shareholder);
                })
                .orElseThrow(() -> new NoSuchElementException("Osakkeenomistajaa ei löytynyt ID:llä " + id));
    }

    // Tallentaa osakkeenomistajan ja liitetyt osakkeet
    public Shareholder saveShareholderWithShares(ShareholderWithSharesDTO dto) {
        Shareholder shareholder = dto.getShareholder();

        // Tarkistaa, onko ID jo olemassa tietokannassa
        if (shareholderRepository.existsByPersonalIdOrCompanyId(shareholder.getPersonalIdOrCompanyId())) {
            throw new IllegalArgumentException("ID on jo olemassa tietokannassa");
        }

        // Tallentaa osakkeenomistajan ensin
        Shareholder savedShareholder = shareholderRepository.save(shareholder);

        // Käsittelee osakkeet, jos niitä on olemassa
        if (dto.getShares() > 0) {
            shareRangeService.addShareRange(savedShareholder.getId(), dto.getShares());
        }

        return savedShareholder;
    }

    // Poistaa osakkeenomistajan
    public void deleteShareholder(Long id) {
        Optional<Shareholder> shareholderOptional = shareholderRepository.findById(id);
        if (shareholderOptional.isPresent()) {
            Shareholder shareholder = shareholderOptional.get();
            // Tarkistaa, onko osakkeenomistajalla liitettyjä osakkeita
            if (shareholder.getShares() != null && !shareholder.getShares().isEmpty()) {
                throw new ShareholderDeletionException(
                        "Osakkeenomistajaa ei voida poistaa, koska siihen liittyy osakkeita.");
            }
            // Poistaa osakkeenomistajan ID:n perusteella
            shareholderRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Osakkeenomistajaa ID:llä " + id + " ei löytynyt.");
        }
    }
}
