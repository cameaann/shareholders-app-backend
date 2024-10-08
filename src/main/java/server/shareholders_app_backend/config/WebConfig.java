package server.shareholders_app_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Määrittelee tämän luokan konfiguraatioluokaksi
public class WebConfig implements WebMvcConfigurer {

    // CORS-asetusten määrittely
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Määritellään, että kaikki reitit (/**) sallitaan
        registry.addMapping("/**")
                .allowedOrigins("*") // Sallitaan kaikki alkuperät
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Sallitaan nämä HTTP-menetelmät
                .allowedHeaders("*"); // Sallitaan kaikki otsikot (tai määritellään tarvittavat)
    }
}
