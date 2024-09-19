package server.shareholders_app_backend.service;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.shareholders_app_backend.dto.TransferRequestDto;
import jakarta.mail.MessagingException;

@Service
public class TransferListener {
    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "transferQueue")
    public void handleTransfer(TransferRequestDto transferRequestDto) throws MessagingException, IOException {
        byte[] pdf = pdfGeneratorService.generateTransferPdf(transferRequestDto.getAdditionalNotes());
        String paymentUrl = "https://example.com/confirm-payment"; // URL для оплаты
        String emailContent = emailService.generateEmailContentWithButton(paymentUrl, transferRequestDto.getToShareholderId().toString());

        try {
            emailService.sendEmailWithAttachment(transferRequestDto.getToShareholderId().toString(), "Share Transfer", emailContent, pdf);
        } catch (MessagingException e) {
            // Handle the exception, e.g., log it
            e.printStackTrace();
        }
    }
}
