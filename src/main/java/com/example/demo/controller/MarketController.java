package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;


import com.example.demo.common.exception.ServiceException;
import com.example.demo.common.exception.TradeException;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
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
    private StocksService stocksService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String PY_API = "http://localhost:5000/";

    // 缓存配置避免重复读取
    private static List<String> cachedStockCodes = Collections.emptyList();

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
    @Cacheable(value = "stockPage", key = "#current + '-' + #size + (#keyword ?: '')")
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
    @Cacheable(value = "stockInfo", key = "#code")
    public Result<StockInfoVO> getStockInfo(
            @Parameter(description = "股票代码", example = "00001", required = true)
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


    @Operation(summary = "获取多个股票实时数据", description = "通过Python API获取多个股票的实时市场数据")
    @GetMapping("/getstock/{codes}")
//    @Cacheable(value = "stockData", key = "#codes")
    public ResponseEntity<?> getStock(@PathVariable String codes) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            // 调用Python服务
            String url = PY_API + "hkstock/" + codes;
            ResponseEntity<Map> response = restTemplate.getForEntity(url,
                    Map.class);

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


    @GetMapping("/getstock/all")
    @Cacheable(value = "stockCodes", key = "'all'") //️改为缓存实际数据而非ResponseEntity
    public List<String> getAllStockCodes() {
        try {
            if (cachedStockCodes.isEmpty()) {
                refreshStockCodes();
            }
            return cachedStockCodes; // 直接返回数据集合
        } catch (Exception e) {
            throw new TradeException("股票代码获取失败", ResultCodeEnum.FAIL.getCode()); 
        }
    }

    // 分时数据调用
    @Operation(summary = "获取股票分时K线数据", description = "通过Python API获取指定股票的分时K线数据")
    @GetMapping("/timekline/{codes}")
    @Cacheable(value = "stockTimeKline", key = "#codes")
    public ResponseEntity<?> getTimeKline(@PathVariable String codes) {
        return processKlineRequest(codes, "timekline");
    }

    // 日K线数据调用
    @Operation(summary = "获取股票日K线数据", description = "通过Python API获取指定股票的日K线数据")
    @GetMapping("/daykline/{codes}")
    @Cacheable(value = "stockDayKline", key = "#codes")
    public ResponseEntity<?> getDayKline(@PathVariable String codes) {
        return processKlineRequest(codes, "daykline");
    }

    // 统一处理K线数据请求
    private ResponseEntity<?> processKlineRequest(String codes, String type) {
        RestTemplate restTemplate = new RestTemplate();
        String url = PY_API + type + "/" + codes;

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();

                if (!"success".equals(body.get("status"))) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(body.get("message"));
                }

                Map<String, Object> rawData = (Map) body.get("data");
                List<KlineDTO> resultList = new ArrayList<>();

                rawData.forEach((stockCodeKey, dataNode) -> {
                    try {
                        KlineDTO dto = new KlineDTO();
                        dto.setStockCode(stockCodeKey);

                        if (type.equals("timekline")) {
                            Map<String, Object> dataMap = (Map<String, Object>) dataNode;
                            List<List<String>> timeValues = (List<List<String>>) dataMap.get("time_data");
                            String date = (String) dataMap.get("date");
                            dto.setDate(date);
                            List<TimeValue> values = timeValues.stream()
                                    .map(entry -> new TimeValue(
                                            entry.get(0),
                                            entry.get(1),
                                            Integer.parseInt(entry.get(2))
                                    ))
                                    .collect(Collectors.toList());
                            dto.setTimeValues(values);
                        } else {
                            // 处理日K线结构
                            List<List<String>> kLines = (List) dataNode;
                            List<DayKValue> kValues = kLines.stream()
                                    .map(item -> new DayKValue(
                                            item.get(0),  // 日期
                                            item.get(1),  // 今开
                                            item.get(2),  // 今收
                                            item.get(3),  // 最高
                                            item.get(4),  // 最低
                                            Double.parseDouble(item.get(5)) // 成交量
                                    ))
                                    .collect(Collectors.toList());
                            dto.setDayKValues(kValues);
                        }

                        resultList.add(dto);
                    } catch (Exception e) {
                        System.err.printf("Processing failed for %s | Error: %s%n",
                                stockCodeKey, e.getMessage());
                    }
                });
                return ResponseEntity.ok(resultList);
            }
            return ResponseEntity.status(response.getStatusCode()).body("Service error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("调用失败: " + e.getMessage());
        }
    }

    // DTO定义
    @Data
    static class KlineDTO {
        private String stockCode;
        private String date;       // 仅分时数据使用
        private List<TimeValue> timeValues;  // 分时数据点
        private List<DayKValue> dayKValues;  // 日K线数据
    }

    @Data
    @AllArgsConstructor
    static class TimeValue {
        private String time;      // 时间（HHmm）
        private String price;     // 当前价格
        private int volume;       // 成交量（股）
    }

    @Data
    @AllArgsConstructor
    static class DayKValue {
        private String date;      // 日期（yyyy-MM-dd）
        private String open;      // 开盘价
        private String close;     // 收盘价
        private String high;      // 最高价
        private String low;       // 最低价
        private double volume;    // 成交量（元）
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
