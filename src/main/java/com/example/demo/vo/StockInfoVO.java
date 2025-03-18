package com.example.demo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.Stocks;
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
public class StockInfoVO extends Stocks {
    @Schema(description = "股票唯一标识", example = "1001")
    private Integer stockId;

    @Schema(description = "股票代码（唯一）", example = "600519")
    private String stockCode;

    @Schema(description = "股票全称", example = "贵州茅台酒股份有限公司")
    private String stockName;

    @Schema(description = "所属交易所: SH-上交所, SZ-深交所", example = "SH", allowableValues = { "SH", "SZ" })
    private String market;
}