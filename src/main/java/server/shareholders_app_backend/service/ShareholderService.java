package server.shareholders_app_backend.service;

import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.repository.ShareholderRepository;
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
            // Convert Set to List
            Set<ShareRange> shareSet = shareholder.getShares();
            if (shareSet != null) {
                List<ShareRange> shareList = new ArrayList<>(shareSet);
                // Sort the List
                shareList.sort(Comparator.comparing(ShareRange::getId));
                // If you need to preserve the order, set it back as a LinkedHashSet
                shareholder.setShares(new LinkedHashSet<>(shareList)); // Optional
            }
            return Optional.of(shareholder);
        }
        return Optional.empty();
    }

    public Shareholder saveShareholder(Shareholder shareholder) {
        return shareholderRepository.save(shareholder);
    }

    public void deleteShareholder(Long id) {
        shareholderRepository.deleteById(id);
    }
}
