package com.example.demo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 股票信息分页结果VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "股票信息分页结果")
public class StockPageVO {

    @Schema(description = "当前页码", example = "1")
    private long current;

    @Schema(description = "每页大小", example = "10")
    private long size;

    @Schema(description = "总记录数", example = "100")
    private long total;

    @Schema(description = "总页数", example = "10")
    private long pages;

    @Schema(description = "是否有上一页", example = "false")
    private boolean hasPrevious;

    @Schema(description = "是否有下一页", example = "true")
    private boolean hasNext;

    @Schema(description = "股票列表")
    private List<StockInfoVO> records;
} 