package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 交易历史VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "交易历史")
public class TradeHistoryVO {

    @Schema(description = "交易ID", example = "TRADE123456789")
    private String tradeId;

    @Schema(description = "股票代码", example = "600004")
    private String stockCode;

    @Schema(description = "股票名称", example = "苹果公司")
    private String stockName;

    @Schema(description = "交易类型", example = "BUY", allowableValues = {"BUY", "SELL"})
    private String type;

    @Schema(description = "交易数量", example = "100")
    private Integer quantity;

    @Schema(description = "交易价格", example = "150.75")
    private Double price;
    
    @Schema(description = "交易金额", example = "15075.00")
    private Double amount;
    
    @Schema(description = "交易时间", example = "2023-04-01T10:30:00Z")
    private Date tradeTime;
    
    @Schema(description = "交易状态", example = "SUCCESS", allowableValues = {"SUCCESS", "FAILED", "CANCELED"})
    private String status;
} 