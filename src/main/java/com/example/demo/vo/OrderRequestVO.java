package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单请求VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单请求")
public class OrderRequestVO {
    @Schema(description = "用户ID", example = "test", required = true)
    private Integer userId;

    @Schema(description = "股票代码", example = "600004", required = true)
    private String stockCode;

    @Schema(description = "交易类型", example = "BUY", required = true, allowableValues = {"BUY", "SELL"})
    private String type;

    @Schema(description = "交易数量", example = "100", required = true)
    private Integer quantity;

    @Schema(description = "价格", example = "150.75", required = true)
    private Double price;

    @Schema(description = "订单类型", example = "LIMIT", allowableValues = {"MARKET", "LIMIT"})
    private String orderType;
}