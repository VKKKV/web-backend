package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册请求VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "注册请求")
public class RegisterRequestVO {

    @Schema(description = "用户名", example = "user123", required = true)
    private String username;

    @Schema(description = "密码", example = "password123", required = true)
    private String password;
    
    @Schema(description = "邮箱", example = "user@example.com")
    private String email;
    
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
} 