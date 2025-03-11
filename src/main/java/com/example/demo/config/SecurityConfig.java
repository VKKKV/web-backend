package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 禁用CSRF
            .authorizeHttpRequests(auth -> auth
                // 允许所有人访问的接口
                .requestMatchers("/api/v1/users/login", "/api/v1/users/register").permitAll()
                // Swagger UI相关接口
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/doc.html").permitAll()
                // 其他所有请求需要认证
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
} 