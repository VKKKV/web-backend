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

//    @Bean
//    public OpenAPI springShopOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("股票交易平台 API 文档")
//                        .description("基于 Knife4j + Spring Boot 的接口文档")
//                        .version("v1.0.0")
//                        .contact(new Contact().name("vkk").email("dev@trade" +
//                                ".com")))
//                .externalDocs(new ExternalDocumentation()
//                        .description("GitHub 仓库")
//                        .url("https://github.com/pending"));
//    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("APP接口")
                        .version("1.0")
                        .description("用户端APP接口")
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")));
    }


    @Bean
    public GroupedOpenApi loginAPI() {
        return GroupedOpenApi.builder().group("登录信息").
                pathsToMatch("/app/login/**", "/app/info").
                build();
    }

    @Bean
    public GroupedOpenApi personAPI() {
        return GroupedOpenApi.builder().group("个人信息").
                pathsToMatch(
                        "/app/history/**",
                        "/app/appointment/**",
                        "/app/agreement/**"
                ).
                build();
    }


    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToExclude("/api/v1/users/**")
                .build();
    }


}

