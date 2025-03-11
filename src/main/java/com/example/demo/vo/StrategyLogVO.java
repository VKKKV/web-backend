package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 策略日志VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "策略日志")
public class StrategyLogVO {

    @Schema(description = "日志ID", example = "LOG123456789")
    private String logId;

    @Schema(description = "策略ID", example = "STRAT20240315001")
    private String strategyId;

    @Schema(description = "时间戳", example = "2023-04-01T10:30:00Z")
    private Date timestamp;

    @Schema(description = "操作类型", example = "BUY", allowableValues = {"BUY", "SELL", "SIGNAL", "ERROR", "INFO"})
    private String action;
    
    @Schema(description = "股票代码", example = "AAPL")
    private String stockCode;
    
    @Schema(description = "数量", example = "100")
    private Integer quantity;
    
    @Schema(description = "价格", example = "150.75")
    private Double price;
    
    @Schema(description = "日志级别", example = "INFO", allowableValues = {"INFO", "WARNING", "ERROR"})
    private String level;
    
    @Schema(description = "日志消息", example = "触发均线交叉买入信号")
    private String message;
} 