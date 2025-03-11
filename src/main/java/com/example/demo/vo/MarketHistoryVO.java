package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 市场历史数据VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "市场历史数据")
public class MarketHistoryVO {

    @Schema(description = "时间戳", example = "1617235200000")
    private Date timestamp;

    @Schema(description = "价格", example = "150.75")
    private Double price;

    @Schema(description = "成交量", example = "12500")
    private Long volume;
} 