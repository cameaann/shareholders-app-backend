package server.shareholders_app_backend.service;

import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.repository.ShareholderRepository;
import server.shareholders_app_backend.repository.ShareRangeRepository;
import server.shareholders_app_backend.config.ApplicationConstants;
import server.shareholders_app_backend.exception.ShareholderDeletionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ShareholderService {

    @Autowired
    private ShareholderRepository shareholderRepository;

    @Autowired
    private ShareRangeRepository shareRangeRepository;
    @Autowired
    private ShareRangeService shareRangeService;

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
