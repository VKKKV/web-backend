package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI().info(
                new Info()
                        .title("股票交易平台 API 文档")
                        .version("v1.0")
                        .description("基于 Knife4j + Spring Boot 的接口文档"));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户管理")
                .pathsToMatch("/api/v1/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi tradeApi() {
        return GroupedOpenApi.builder()
                .group("交易管理")
                .pathsToMatch("/api/v1/trade/**")
                .build();
    }

    @Bean
    public GroupedOpenApi marketApi() {
        return GroupedOpenApi.builder()
                .group("行情数据")
                .pathsToMatch("/api/v1/market/**")
                .build();
    }
}

