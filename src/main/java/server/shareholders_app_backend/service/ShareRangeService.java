package server.shareholders_app_backend.service;

import server.shareholders_app_backend.dto.ShareRangeDTO;
import server.shareholders_app_backend.dto.TransferRequestDto;
import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.repository.ShareRangeRepository;
import server.shareholders_app_backend.repository.ShareholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void transferShares(TransferRequestDto transferRequest) {
        Shareholder fromShareholder = shareholderRepository.findById(transferRequest.getFromShareholderId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Shareholder with ID " + transferRequest.getFromShareholderId() + " not found"));

        Shareholder toShareholder = shareholderRepository.findById(transferRequest.getToShareholderId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Shareholder with ID " + transferRequest.getToShareholderId() + " not found"));

        int quantityToTransfer = transferRequest.getQuantity();
        int remainingQuantity = quantityToTransfer;

        // Retrieve and sort share ranges by startNumber
        List<ShareRange> shareRanges = shareRepository.findAllOrderedByStartNumber();
        for (ShareRange shareRange : shareRanges) {
            if (shareRange.getShareholder().equals(fromShareholder)) {
                if (remainingQuantity <= 0)
                    break;

                int availableQuantity = shareRange.getQuantity();
                if (availableQuantity <= remainingQuantity) {
                    remainingQuantity -= availableQuantity;
                    shareRange.setShareholder(toShareholder);
                    shareRepository.save(shareRange);
                } else {
                    int newStartNumber = shareRange.getStartNumber() + remainingQuantity;
                    ShareRange newShareRange = new ShareRange();
                    newShareRange.setQuantity(remainingQuantity);
                    newShareRange.setStartNumber(shareRange.getStartNumber());
                    newShareRange.setEndNumber(newStartNumber - 1);
                    newShareRange.setShareholder(toShareholder);

                    shareRange.setQuantity(availableQuantity - remainingQuantity);
                    shareRange.setStartNumber(newStartNumber);

                    shareRepository.save(newShareRange);
                    shareRepository.save(shareRange);
                    remainingQuantity = 0;
                }
            }
        }

        if (remainingQuantity > 0) {
            throw new IllegalStateException("Not enough shares to transfer.");
        }
    }

    public List<ShareRangeDTO> getAllShares() {
        return shareRepository.findAllOrderedByStartNumber()
                .stream()
                .map(ShareRangeDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ShareRange> getShareById(Long id) {
        return shareRepository.findById(id);
    }

    @Transactional
    public ShareRange addShareRange(Long shareholderId, int quantity) {
        Shareholder shareholder = shareholderRepository.findById(shareholderId)
                .orElseThrow(() -> new NoSuchElementException("Shareholder with ID " + shareholderId + " not found"));

        Integer currentMaxEndNumber = shareRepository.findMaxEndNumber();
        if (currentMaxEndNumber == null) {
            currentMaxEndNumber = 0;
        }

        int startNumber = currentMaxEndNumber + 1;
        int endNumber = startNumber + quantity - 1;

        ShareRange shareRange = new ShareRange();
        shareRange.setQuantity(quantity);
        shareRange.setStartNumber(startNumber);
        shareRange.setEndNumber(endNumber);
        shareRange.setShareholder(shareholder);

        return shareRepository.save(shareRange);
    }

    @Transactional
    public void deleteShare(Long id) {
        if (shareRepository.existsById(id)) {
            shareRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("ShareRange with ID " + id + " not found.");
        }
    }
}
