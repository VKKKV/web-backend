package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("股票交易平台 API 文档")
                        .description("基于 Knife4j + Spring Boot 的接口文档")
                        .version("v1.0.0")
                        .contact(new Contact().name("zy")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub 仓库")
                        .url("https://github.com/VKKKV/web-backend"));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder().group("User").
                pathsToMatch("/api/v1/users/**").
                build();
    }

    @Bean
    public GroupedOpenApi tradeApi() {
        return GroupedOpenApi.builder().group("Trade").
                pathsToMatch("/api/v1/trades/**").
                build();
    }

    @Bean
    public GroupedOpenApi stockApi() {
        return GroupedOpenApi.builder().group("Stock").
                pathsToMatch("/api/v1/strategy/**").
                build();
    }
}

