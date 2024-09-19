package server.shareholders_app_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class PaymentConfirmationController {

    @PostMapping("/confirm-payment")
    public String confirmPayment(@RequestParam String email) {
        String confirmationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Здесь вы можете сохранить дату подтверждения в базу данных или выполнить другие действия
        return "Payment confirmed for " + email + " at " + confirmationDate;
    }
}
