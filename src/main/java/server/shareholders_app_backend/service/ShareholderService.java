package server.shareholders_app_backend.service;

import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.repository.ShareholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShareholderService {

    @Autowired
    private ShareholderRepository shareholderRepository;

    public List<Shareholder> getAllShareholders() {
        List<Shareholder> shareholders = shareholderRepository.findAll();
        shareholders.forEach(this::calculateAndSaveTotalShares);
        return shareholders;
    }

    public Optional<Shareholder> getShareholderById(Long id) {
        return shareholderRepository.findById(id);
    }

    public Shareholder saveShareholder(Shareholder shareholder) {
        return shareholderRepository.save(shareholder);
    }

    public void deleteShareholder(Long id) {
        shareholderRepository.deleteById(id);
    }

    public int calculateSharesInRange(Long id) {
        Optional<Shareholder> optionalShareholder = shareholderRepository.findById(id);
        if (optionalShareholder.isPresent()) {
            Shareholder shareholder = optionalShareholder.get();
            Integer startNumber = shareholder.getStartNumber();
            Integer endNumber = shareholder.getEndNumber();
            if (startNumber != null && endNumber != null) {
                return endNumber - startNumber + 1;
            }
        }
        return 0;
    }

    public Shareholder calculateAndSaveTotalShares(Shareholder shareholder) {
        Integer startNumber = shareholder.getStartNumber();
        Integer endNumber = shareholder.getEndNumber();
        if (startNumber != null && endNumber != null) {
            int totalShares = endNumber - startNumber + 1;
            shareholder.setTotalShares(totalShares);
            return shareholderRepository.save(shareholder);
        }
        return shareholder;
    }

    public List<Long> getAllShareholderIds() {
        return shareholderRepository.findAll().stream()
                .map(Shareholder::getId)
                .collect(Collectors.toList());
    }
}
