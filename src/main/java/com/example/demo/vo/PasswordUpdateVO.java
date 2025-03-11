package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 密码更新VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "密码更新请求")
public class PasswordUpdateVO {

    @Schema(description = "用户ID", example = "12345", required = true)
    private Integer userId;
    
    @Schema(description = "旧密码", example = "oldPassword123", required = true)
    private String oldPassword;

    @Schema(description = "新密码", example = "newPassword123", required = true)
    private String newPassword;
} 