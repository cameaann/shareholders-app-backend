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

    public List<ShareTransferHistory> getAllTransferHistories() {
        return shareTransferHistoryRepository.findAll();
    }

    public Optional<ShareTransferHistory> getTransferHistoryById(Long id) {
        return shareTransferHistoryRepository.findById(id);
    }

    public ShareTransferHistory saveTransferHistory(ShareTransferHistory transferHistory) {
        return shareTransferHistoryRepository.save(transferHistory);
    }
}
