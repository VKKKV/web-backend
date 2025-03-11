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
public class StockInfoVO {
    
    @Schema(description = "股票代码", example = "600000")
    private String code;
    
    @Schema(description = "股票名称", example = "浦发银行")
    private String stockName;
    
    @Schema(description = "交易所", example = "SH")
    private String market;
    
    @Schema(description = "行业分类", example = "科技")
    private String industry;
    
    @Schema(description = "上市日期", example = "1980-12-12")
    private String listDate;
    
    @Schema(description = "总股本(亿股)", example = "167.01")
    private String totalShare;
    
    @Schema(description = "流通股本(亿股)", example = "166.88")
    private String circulatingShare;
    
    @Schema(description = "公司简介", example = "苹果公司是美国的一家高科技公司，主要设计、开发和销售消费电子产品、计算机软件和在线服务。")
    private String description;
} 