package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录请求VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录请求")
public class LoginRequestVO {

    @Schema(description = "用户名", example = "user123", required = true)
    private String username;

    @Schema(description = "密码", example = "password123", required = true)
    private String password;
} 