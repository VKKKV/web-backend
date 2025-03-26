package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.result.Result;
import com.example.demo.common.result.ResultCodeEnum;
import com.example.demo.entity.Stocks;
import com.example.demo.service.StocksService;
import com.example.demo.vo.StockInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 行情数据控制器
 * 提供股票市场行情和基本信息查询功能
 */
@RestController
@RequestMapping("/api/v1/market")
@Tag(name = "行情数据", description = "股票行情和基本信息接口")
public class MarketController {

    @Autowired
    private StocksService stocksService;

    /**
     * 分页查询股票列表
     *
     * @param current     页码
     * @param size 每页大小
     * @param keyword  搜索关键词
     * @return 分页结果
     */
    @Operation(summary = "分页查询股票", description = "分页获取股票列表，支持按代码或名称搜索")
    @GetMapping("/stocks")
    public Result<IPage<Stocks>> getStockList(
            @Parameter(description = "页码", example = "1")
            @RequestParam(defaultValue = "1") Integer current,

            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") Integer size,

            @Parameter(description = "搜索关键词")
            @RequestParam(required = false) String keyword) {
        Page<Stocks> page = new Page<>(current, size);
        // 1. 执行分页查询
        IPage<Stocks> result = stocksService.getStocksByPage(page, keyword);
        return Result.success(result);
    }

    /**
     * 获取股票基本信息
     *
     * @param code 股票代码
     * @return 股票基本信息
     */
    @Operation(summary = "获取股票信息", description = "查询股票的基本资料和市场信息")
    @GetMapping("/stocks/{code}")
    public Result<StockInfoVO> getStockInfo(
            @Parameter(description = "股票代码", example = "600004", required = true)
            @PathVariable String code) {

        // 1. 查询股票信息
        Stocks stock = stocksService.getByStockCode(code);
        if (stock == null) {
            return Result.fail(ResultCodeEnum.DATA_ERROR.getCode(), "股票不存在");
        }

        // 2. 构建返回数据
        StockInfoVO stockInfo = StockInfoVO.builder()
                .stockCode(stock.getStockCode())
                .stockName(stock.getStockName())
                .build();

        return Result.success(stockInfo);
    }
} 
