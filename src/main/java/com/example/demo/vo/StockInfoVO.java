package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 股票信息VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "股票信息")
public class StockInfoVO{
    @Schema(description = "股票代码（唯一）", example = "600004")
    private String stockCode;

    @Schema(description = "股票全称", example = "白云机场")
    private String stockName;
}