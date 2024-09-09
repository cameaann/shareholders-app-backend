package server.shareholders_app_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.shareholders_app_backend.dto.TransferRequestDto;
import server.shareholders_app_backend.model.Shareholder;
import server.shareholders_app_backend.repository.ShareholderRepository;

@Service
public class TransferService {

    @Autowired
    private ShareholderRepository shareholderRepository;

    public TransferRequestDto populateShareholderDetails(TransferRequestDto dto) {
        if (dto.getFromShareholderId() != null) {
            Shareholder seller = shareholderRepository.findById(dto.getFromShareholderId()).orElse(null);
            if (seller != null) {
                dto.setSeller(seller.getName());
            }
        }

        if (dto.getToShareholderId() != null) {
            Shareholder buyer = shareholderRepository.findById(dto.getToShareholderId()).orElse(null);
            if (buyer != null) {
                dto.setBuyer(buyer.getName());
            }
        }

        return dto;
    }
}
