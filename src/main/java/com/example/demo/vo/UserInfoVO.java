package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.math.BigDecimal;

/**
 * 用户信息VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户信息")
public class UserInfoVO {

    @Schema(description = "用户ID", example = "12345")
    private Integer userId;

    @Schema(description = "用户名", example = "user123")
    private String username;
    
    @Schema(description = "邮箱", example = "user@example.com")
    private String email;
    
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
    
    @Schema(description = "注册时间", example = "2023-01-01T10:00:00Z")
    private Date registerTime;

    @Schema(description = "账户余额", example = "10000.00")
    private BigDecimal balance;

}