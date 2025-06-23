package com.restaurant.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许跨域请求的来源
        config.addAllowedOriginPattern("*");
        
        // 允许跨域请求的HTTP方法
        config.addAllowedMethod("*");
        
        // 允许跨域请求携带的头信息
        config.addAllowedHeader("*");
        
        // 允许跨域请求携带cookie
        config.setAllowCredentials(true);
        
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}