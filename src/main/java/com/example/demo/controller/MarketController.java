package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.result.Result;
import com.example.demo.common.result.ResultCodeEnum;
import com.example.demo.entity.MarketData;
import com.example.demo.entity.Stocks;
import com.example.demo.service.MarketDataService;
import com.example.demo.service.StocksService;
import com.example.demo.vo.MarketHistoryVO;
import com.example.demo.vo.StockInfoVO;
import com.example.demo.vo.StockPageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 行情数据控制器
 * 提供股票市场行情和基本信息查询功能
 */
@RestController
@RequestMapping("/api/v1/market")
@Tag(name = "行情数据", description = "股票行情和基本信息接口")
public class MarketController {

    @Autowired
    private MarketDataService marketDataService;

    @Autowired
    private StocksService stocksService;

    /**
     * 分页查询股票列表
     *
     * @param page     页码
     * @param pageSize 每页大小
     * @param keyword  搜索关键词
     * @return 分页结果
     */
    @Operation(summary = "分页查询股票", description = "分页获取股票列表，支持按代码或名称搜索")
    @GetMapping("/stocks")
    public Result<StockPageVO> getStockList(
            @Parameter(description = "页码", example = "1")
            @RequestParam(defaultValue = "1") Integer page,

            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize,

            @Parameter(description = "搜索关键词", example = "")
            @RequestParam(required = false) String keyword) {

        // 1. 执行分页查询
        IPage<Stocks> pageResult = stocksService.getStocksByPage(page, pageSize, keyword);

        // 2. 转换为VO对象
        List<StockInfoVO> records = pageResult.getRecords().stream()
                .map(stock -> StockInfoVO.builder()
                        .code(stock.getStockCode())
                        .stockName(stock.getStockName())
                        .market(stock.getMarket())
                        .build())
                .collect(Collectors.toList());

        // 3. 构建分页结果
        StockPageVO result = StockPageVO.builder()
                .current(pageResult.getCurrent())
                .size(pageResult.getSize())
                .total(pageResult.getTotal())
                .pages(pageResult.getPages())
                .hasPrevious(pageResult.getCurrent() > 1)
                .hasNext(pageResult.getCurrent() < pageResult.getPages())
                .records(records)
                .build();

        return Result.success(result);
    }

    /**
     * 查询历史行情数据
     *
     * @param stockCode 股票代码
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 历史行情数据列表
     */
    @Operation(summary = "查询历史行情", description = "获取指定时间范围内的股票价格数据")
    @GetMapping("/history")
    public Result<List<MarketHistoryVO>> getHistory(
            @Parameter(description = "股票代码", example = "AAPL", required = true)
            @RequestParam String stockCode,

            @Parameter(description = "开始时间", example = "2023-01-01", required = true)
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,

            @Parameter(description = "结束时间", example = "2023-01-31", required = true)
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {

        // 1. 查询股票信息
        Stocks stock = stocksService.getByStockCode(stockCode);
        if (stock == null) {
            return Result.fail(ResultCodeEnum.DATA_ERROR.getCode(), "股票不存在");
        }

        // 2. 查询历史数据
        List<MarketData> historyData = marketDataService.getHistoryData(stock.getStockId(), startTime, endTime);

        // 3. 转换为VO对象
        List<MarketHistoryVO> result = historyData.stream()
                .map(data -> MarketHistoryVO.builder()
                        .timestamp(data.getTimestamp())
                        .price(data.getPrice().doubleValue())
                        .volume(data.getVolume())
                        .build())
                .collect(Collectors.toList());

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
            @Parameter(description = "股票代码", example = "AAPL", required = true)
            @PathVariable String code) {

        // 1. 查询股票信息
        Stocks stock = stocksService.getByStockCode(code);
        if (stock == null) {
            return Result.fail(ResultCodeEnum.DATA_ERROR.getCode(), "股票不存在");
        }

        // 2. 构建返回数据
        StockInfoVO stockInfo = StockInfoVO.builder()
                .code(stock.getStockCode())
                .stockName(stock.getStockName())
                .market(stock.getMarket())
                .build();

        return Result.success(stockInfo);
    }
} 
