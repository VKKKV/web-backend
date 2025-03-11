package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单响应VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单响应")
public class OrderResponseVO {

    @Schema(description = "订单ID", example = "ORDER123456789")
    private String orderId;

    @Schema(description = "订单状态", example = "PROCESSING", allowableValues = {"PROCESSING", "SUCCESS", "FAILED", "CANCELED"})
    private String status;

    @Schema(description = "执行价格", example = "150.75")
    private Double executedPrice;
    
    @Schema(description = "创建时间", example = "2023-04-01T10:30:00Z")
    private String createTime;
    
    @Schema(description = "更新时间", example = "2023-04-01T10:31:15Z")
    private String updateTime;
    
    @Schema(description = "错误信息", example = "余额不足")
    private String errorMessage;
} 