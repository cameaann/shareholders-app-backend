package server.shareholders_app_backend.service;

import server.shareholders_app_backend.model.Share;
import server.shareholders_app_backend.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShareService {

    @Autowired
    private ShareRepository shareRepository;

    public List<Share> getAllShares() {
        return shareRepository.findAll();
    }

    public Optional<Share> getShareById(Long id) {
        return shareRepository.findById(id);
    }

    public Share saveShare(Share share) {
        return shareRepository.save(share);
    }

    public void deleteShare(Long id) {
        shareRepository.deleteById(id);
    }
}
