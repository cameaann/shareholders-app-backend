package server.shareholders_app_backend.service;

import server.shareholders_app_backend.model.ShareRange;
import server.shareholders_app_backend.repository.ShareRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShareRangeService {

    @Autowired
    private ShareRangeRepository shareRepository;

    public List<ShareRange> getAllShares() {
        return shareRepository.findAll();
    }

    public Optional<ShareRange> getShareById(Long id) {
        return shareRepository.findById(id);
    }

    public ShareRange saveShare(ShareRange share) {
        // If needed, you could include additional logic here
        // For example, setting up the quantity or performing validation
        return shareRepository.save(share);
    }

    public void deleteShare(Long id) {
        shareRepository.deleteById(id);
    }
}
