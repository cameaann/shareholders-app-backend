package server.shareholders_app_backend.service;

import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.repository.ShareholderRepository;
import server.shareholders_app_backend.exception.ShareholderDeletionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShareholderService {

    @Autowired
    private ShareholderRepository shareholderRepository;

    public List<Shareholder> getAllShareholders() {
        return shareholderRepository.findAll();
    }

    public Optional<Shareholder> getShareholderById(Long id) {
        Optional<Shareholder> shareholderOptional = shareholderRepository.findById(id);
        if (shareholderOptional.isPresent()) {
            Shareholder shareholder = shareholderOptional.get();
            Set<ShareRange> shareSet = shareholder.getShares();
            if (shareSet != null) {
                List<ShareRange> shareList = new ArrayList<>(shareSet);
                shareList.sort(Comparator.comparing(ShareRange::getId));
                shareholder.setShares(new LinkedHashSet<>(shareList)); // Optional
            }
            return Optional.of(shareholder);
        }
        return Optional.empty();
    }

    public Shareholder updateShareholder(Long id, Shareholder updatedShareholder) {
        return shareholderRepository.findById(id)
                .map(shareholder -> {
                    shareholder.setName(updatedShareholder.getName());
                    shareholder.setPersonalIdOrCompanyId(updatedShareholder.getPersonalIdOrCompanyId());
                    shareholder
                            .setPlaceOfResidenceOrHeadquarters(updatedShareholder.getPlaceOfResidenceOrHeadquarters());
                    shareholder.setAddress(updatedShareholder.getAddress());
                    shareholder.setEmailAddress(updatedShareholder.getEmailAddress());
                    shareholder.setPhoneNumber(updatedShareholder.getPhoneNumber());
                    shareholder.setBankAccountNumber(updatedShareholder.getBankAccountNumber());
                    return shareholderRepository.save(shareholder);
                })
                .orElseThrow(() -> new NoSuchElementException("Shareholder not found with id " + id));
    }

    public Shareholder saveShareholder(Shareholder shareholder) {
        if (shareholderRepository.existsByPersonalIdOrCompanyId(shareholder.getPersonalIdOrCompanyId())) {
            throw new IllegalArgumentException("ID already exists in the database");
        }
        return shareholderRepository.save(shareholder);
    }

    public void deleteShareholder(Long id) {
        Optional<Shareholder> shareholderOptional = shareholderRepository.findById(id);
        if (shareholderOptional.isPresent()) {
            Shareholder shareholder = shareholderOptional.get();
            if (shareholder.getShares() != null && !shareholder.getShares().isEmpty()) {
                throw new ShareholderDeletionException("Cannot delete shareholder with associated shares.");
            }
            shareholderRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Shareholder with ID " + id + " not found.");
        }
    }
}
