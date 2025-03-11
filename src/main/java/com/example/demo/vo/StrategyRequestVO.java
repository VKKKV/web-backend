package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 策略请求VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "策略请求")
public class StrategyRequestVO {

    @Schema(description = "策略名称", example = "均线突破策略", required = true)
    private String name;

    @Schema(description = "策略描述", example = "当短期均线上穿长期均线时买入，下穿时卖出")
    private String description;

    @Schema(description = "股票代码列表", example = "[\"AAPL\", \"TSLA\"]", required = true)
    private List<String> stockCodes;

    @Schema(description = "策略类型", example = "TECHNICAL", allowableValues = {"TECHNICAL", "FUNDAMENTAL", "MIXED"})
    private String type;
    
    @Schema(description = "策略参数", example = "{\"shortPeriod\": 5, \"longPeriod\": 20}")
    private Map<String, Object> parameters;
    
    @Schema(description = "资金分配比例", example = "0.5")
    private Double allocationRatio;
    
    @Schema(description = "风险控制参数", example = "{\"stopLoss\": 0.05, \"takeProfit\": 0.1}")
    private Map<String, Object> riskControl;
} 