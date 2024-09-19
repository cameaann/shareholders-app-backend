package server.shareholders_app_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.shareholders_app_backend.dto.TransferRequestDto;
import server.shareholders_app_backend.service.EmailService;
import server.shareholders_app_backend.service.PdfGeneratorService;
import server.shareholders_app_backend.service.TransferService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public TransferRequestDto handleTransfer(@RequestBody TransferRequestDto transferRequestDto) throws IOException {
        TransferRequestDto populatedDto = transferService.populateShareholderDetails(transferRequestDto);

        byte[] pdf = pdfGeneratorService.generateTransferPdf(populatedDto.getAdditionalNotes());
        String paymentUrl = "https://example.com/confirm-payment"; // URL для оплаты
        String emailContent = emailService.generateEmailContentWithButton(paymentUrl, populatedDto.getToShareholderId().toString());

        try {
            emailService.sendEmailWithAttachment(populatedDto.getToShareholderId().toString(), "Share Transfer", emailContent, pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null or handle error appropriately
        }

        rabbitTemplate.convertAndSend("transferQueue", populatedDto);
        return populatedDto;
    }
}
