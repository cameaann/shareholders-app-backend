package server.shareholders_app_backend.service;

import server.shareholders_app_backend.config.ApplicationConstants;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.repository.ShareRangeRepository;
import server.shareholders_app_backend.repository.ShareholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import server.shareholders_app_backend.dto.ShareRangeDTO;

@Service
public class ShareRangeService {

    @Autowired
    private ShareRangeRepository shareRepository;

    @Autowired
    private ShareholderRepository shareholderRepository;

    public List<ShareRangeDTO> getAllShares() {
        return shareRepository.findAll()
                .stream()
                .map(ShareRangeDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ShareRange> getShareById(Long id) {
        return shareRepository.findById(id);
    }

    @Transactional
    public ShareRange addShareRange(Long shareholderId, int quantity) {
        // Find the shareholder by ID
        Shareholder shareholder = shareholderRepository.findById(shareholderId)
                .orElseThrow(() -> new NoSuchElementException("Shareholder not found"));

        // Calculate start_number and end_number
        Integer currentMaxEndNumber = shareRepository.findMaxEndNumber();
        if (currentMaxEndNumber == null) {
            currentMaxEndNumber = 0;
        }

        int startNumber = currentMaxEndNumber + 1;
        int endNumber = startNumber + quantity - 1;

        if (endNumber > ApplicationConstants.TOTAL_SHARES_IN_COMPANY) {
            throw new IllegalStateException("Not enough shares available");
        }

        // Create a new ShareRange instance with calculated values
        ShareRange shareRange = new ShareRange();
        shareRange.setQuantity(quantity);
        shareRange.setStartNumber(startNumber);
        shareRange.setEndNumber(endNumber);
        shareRange.setShareholder(shareholder);

        return shareRepository.save(shareRange);
    }

    public void deleteShare(Long id) {
        if (shareRepository.existsById(id)) {
            shareRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("ShareRange with ID " + id + " not found.");
        }
    }
}
