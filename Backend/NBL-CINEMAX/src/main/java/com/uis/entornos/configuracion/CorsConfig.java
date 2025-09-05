package com.uis.entornos.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permite el acceso CORS a toda la API desde cualquier origen
                registry.addMapping("/api/**") // Aplica a toda tu API bajo /api/
                        .allowedOrigins("*") // Permite cualquier origen (frontend)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite estos métodos HTTP
                        .allowedHeaders("*"); // Permite cualquier cabecera
            }
        };
    }
}