package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

	@Value("${cors.origins:http://localhost:5173,http://localhost:3000,https://ds-list-front-production.up.railway.app}")
	private String corsOrigins;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				String[] origins = corsOrigins.split(","); // Divide os valores em múltiplas origens
				registry.addMapping("/**").allowedMethods("*").allowedOrigins(origins);
			}
		};
	}
}