// package server.shareholders_app_backend.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import server.shareholders_app_backend.dto.TransferRequestDto;
// import server.shareholders_app_backend.service.TransferService;

// @RestController
// @RequestMapping("/api/transfer")
// public class TransferController {

// @Autowired
// private TransferService transferService;

// @PostMapping
// public TransferRequestDto handleTransfer(@RequestBody TransferRequestDto
// transferRequestDto) {
// TransferRequestDto populatedDto =
// transferService.populateShareholderDetails(transferRequestDto);
// return populatedDto; // Return the populated DTO
// }
// }
