package com.mjsasha.lesdoc.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    private final ServicesProperties servicesProperties;

    public CorsConfig(ServicesProperties servicesProperties) {
        this.servicesProperties = servicesProperties;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(servicesProperties.getOrchestratorOrigin());
            }
        };
    }
}
