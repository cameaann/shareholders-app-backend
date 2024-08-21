package server.shareholders_app_backend.service;

import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.repository.ShareholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShareholderService {

    @Autowired
    private ShareholderRepository shareholderRepository;

    public List<Shareholder> getAllShareholders() {
        return shareholderRepository.findAll();
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
}
