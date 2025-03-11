package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 策略响应VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "策略响应")
public class StrategyResponseVO {

    @Schema(description = "策略ID", example = "STRAT20240315001")
    private String strategyId;

    @Schema(description = "策略名称", example = "均线突破策略")
    private String name;

    @Schema(description = "策略状态", example = "ACTIVE", allowableValues = {"ACTIVE", "PAUSED", "STOPPED", "ERROR"})
    private String status;
    
    @Schema(description = "创建时间", example = "2023-04-01T10:30:00Z")
    private Date createTime;
    
    @Schema(description = "最后更新时间", example = "2023-04-01T10:31:15Z")
    private Date updateTime;
    
    @Schema(description = "累计收益率", example = "0.0568")
    private Double totalReturn;
    
    @Schema(description = "年化收益率", example = "0.1245")
    private Double annualizedReturn;
    
    @Schema(description = "最大回撤", example = "0.0325")
    private Double maxDrawdown;
} 