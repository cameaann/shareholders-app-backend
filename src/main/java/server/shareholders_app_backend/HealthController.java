package server.shareholders_app_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Kontrolleri, joka käsittelee terveyden tarkistamiseen liittyviä pyyntöjä
@RestController
public class HealthController {

    // HTTP GET -pyyntö reitille /health palauttaa yksinkertaisen terveysviestin
    @GetMapping("/health")
    public String health() {
        return "Healthy, Healthy"; // Palauttaa viestin, joka vahvistaa palvelimen olevan toiminnassa
    }
}
