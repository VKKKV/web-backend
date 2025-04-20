package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;


import com.example.demo.config.StockCodesConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.result.Result;
import com.example.demo.common.result.ResultCodeEnum;
import com.example.demo.entity.Stocks;
import com.example.demo.service.StocksService;
import com.example.demo.vo.StockDTO;
import com.example.demo.vo.StockInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
     * @param current 页码
     * @param size    每页大小
     * @param keyword 搜索关键词
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

    @Operation(summary = "获取可交易股票列表", description = "获取可交易股票列表")
    @GetMapping("/stocksList")
    public Result<List<StockInfoVO>> getTradableStocks() {
        // 实际开发中这里应调用Service层获取数据
        List<StockInfoVO> stockList = new ArrayList<>();

        // 示例数据（实际应从数据库或配置中心获取）
        stockList.add(new StockInfoVO("600000", "浦发银行"));
        stockList.add(new StockInfoVO("600004", "白云机场"));

        return Result.success(stockList);
    }


    @Operation(summary = "获取股票data", description = "获取股票data")
    @GetMapping("/stockData")
    public Result<StockInfoVO> getStockData() {

        return Result.success(null);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String PY_API = "http://localhost:5000/hkstock/";

    @GetMapping("/getstock/{codes}")
    public ResponseEntity<?> getStock(@PathVariable String codes) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            // 调用Python服务
            ResponseEntity<Map> response = restTemplate.getForEntity(PY_API + codes, Map.class);

            if (response.getStatusCode() == HttpStatus.OK &&
                    "success".equals(response.getBody().get("status"))) {

                // 转换嵌套数据结构
                Map<String, Map> rawData = (Map) response.getBody().get("data");
                List<StockDTO> stocks = new ArrayList<>();

                System.out.println(rawData);

                rawData.forEach((stockCodeKey, dataMap) -> {
                    try {
                        // Map转LinkedHashMap确保字段顺序（示例数据需有序）
                        Map<String, Object> cleanMap = new LinkedHashMap<>();

                        dataMap.forEach((k, v) -> {
                            // 过滤掉非法数值（这里可以扩展校验规则）
                            if (v != null) {
                                cleanMap.put((String) k, v);
                            }
                        });

                        StockDTO stock = objectMapper.convertValue(cleanMap, StockDTO.class);
                        stock.setStockCode(stockCodeKey);
                        stocks.add(stock);
                    } catch (Exception e) {
                        System.out.printf("股票[%s]转换失败 | 数据: %s | 异常: %s%n",
                                stockCodeKey, dataMap, e.getMessage());

                    }
                });

                return ResponseEntity.ok(stocks);
            }
            return ResponseEntity.status(500).body("数据获取异常");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // 缓存配置避免重复读取
    private static List<String> cachedStockCodes = Collections.emptyList();

    @GetMapping("/getstock/all")
    public ResponseEntity<?> getAllStockCodes() {
        try {
            if (cachedStockCodes.isEmpty()) {
                refreshStockCodes();
            }
            return ResponseEntity.ok(Map.of("codes", cachedStockCodes));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Failed to read stock codes",
                            "detail", e.getMessage()
                    ));
        }
    }

    private synchronized void refreshStockCodes() throws IOException {
        Resource resource = new ClassPathResource("/stock_codes.conf");
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = resource.getInputStream()) {
            StockCodesConfig config = mapper.readValue(is, StockCodesConfig.class);
            cachedStockCodes = config.getStockCodes() != null ?
                    config.getStockCodes() :
                    Collections.emptyList();
        } catch (JsonProcessingException e) {
            throw new IOException("Invalid JSON format in stock_codes.conf", e);
        }
    }

}
