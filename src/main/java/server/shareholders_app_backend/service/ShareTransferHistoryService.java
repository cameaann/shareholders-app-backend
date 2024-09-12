package server.shareholders_app_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.shareholders_app_backend.model.ShareTransferHistory;
import server.shareholders_app_backend.repository.ShareTransferHistoryRepository;

import java.util.List;

@Service
public class ShareTransferHistoryService {

    @Autowired
    private ShareTransferHistoryRepository shareTransferHistoryRepository;

    public List<ShareTransferHistory> getAllTransferHistories() {
        return shareTransferHistoryRepository.findAll();
    }

    public ShareTransferHistory saveTransferHistory(ShareTransferHistory transferHistory) {
        return shareTransferHistoryRepository.save(transferHistory);
    }
}
