package server.shareholders_app_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public String Health(){
        return "Healthy, Healthy";
    }
}
