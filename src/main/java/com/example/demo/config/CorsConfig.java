package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
	public class CorsConfig {

//	    @Bean
//	    public WebMvcConfigurer corsConfigurer() {
//	        return new WebMvcConfigurer() {
//	            @Override
//	            public void addCorsMappings(CorsRegistry registry) {
//	                registry.addMapping("/**")
//	                        .allowedOrigins("http://localhost:4200")
//	                        .allowedMethods("*")
//	                        .allowedHeaders("*");
//	            }
//	        };
//	    }
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOriginPatterns(List.of("*"));
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
	    config.setAllowedHeaders(List.of("*"));
	    config.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}
	}
