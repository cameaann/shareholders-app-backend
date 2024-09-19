package server.shareholders_app_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachment) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true); // Enable HTML

        // Add attachment
        helper.addAttachment("transfer.pdf", new ByteArrayResource(attachment));

        mailSender.send(message);
    }

    public String generateEmailContentWithButton(String paymentUrl, String shareholderEmail) {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String confirmationUrl = paymentUrl + "?email=" + shareholderEmail;
        return "<html>" +
                "<body>" +
                "<p>Дата сообщения: " + currentDate + "</p>" +
                "<p>Пожалуйста, нажмите на кнопку ниже, чтобы подтвердить оплату:</p>" +
                "<a href=\"" + confirmationUrl + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: white; background-color: blue; text-align: center; text-decoration: none; border-radius: 5px;\">Подтвердить оплату</a>" +
                "</body>" +
                "</html>";
    }
}
