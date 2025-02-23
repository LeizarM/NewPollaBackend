package com.polla.bo.polla.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir patrones de origen
        config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000"));

        // Deshabilitar credenciales
        config.setAllowCredentials(false);

        // Permitir todos los métodos
        config.addAllowedMethod("*");

        // Permitir todos los headers
        config.addAllowedHeader("*");

        // Exponer headers
        config.addExposedHeader("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}