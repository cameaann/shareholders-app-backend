// package server.shareholders_app_backend.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import server.shareholders_app_backend.dto.TransferRequestDto;
// import server.shareholders_app_backend.model.Shareholder;
// import server.shareholders_app_backend.repository.ShareholderRepository;

// @Service
// public class TransferService {

// @Autowired
// private ShareholderRepository shareholderRepository;

// public TransferRequestDto populateShareholderDetails(TransferRequestDto dto)
// {
// // Fetch shareholder details only if needed for validation or further
// processing
// if (dto.getFromShareholderId() != null) {
// Shareholder seller =
// shareholderRepository.findById(dto.getFromShareholderId()).orElse(null);
// // Handle logic if you need to do something with seller details
// // Note: seller information is no longer included in the DTO
// }

// if (dto.getToShareholderId() != null) {
// Shareholder buyer =
// shareholderRepository.findById(dto.getToShareholderId()).orElse(null);
// // Handle logic if you need to do something with buyer details
// // Note: buyer information is no longer included in the DTO
// }

// return dto;
// }
// }
