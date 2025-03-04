package com.example.demo.controller;

import com.example.demo.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/market")
@Tag(name = "行情数据模块", description = "股票市场数据接口")
public class MarketController {

    @GetMapping("/history")
    @Operation(summary = "查询历史行情")
    public Result<List<Map<String, Object>>> getHistory(
        @RequestParam String stock_code,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start_time,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_time) {
        
        // 测试数据
        List<Map<String, Object>> testData = new ArrayList<>();
        for(int i=0; i<5; i++){
            Map<String, Object> item = new HashMap<>();
            item.put("timestamp", new Date());
            item.put("price", 150.0 + i*5);
            item.put("volume", 10000 + i*2000);
            testData.add(item);
        }
        return Result.success(testData);
    }

    @GetMapping("/stocks/{code}")
    @Operation(summary = "获取股票基本信息")
    public Result<Map<String, Object>> getStockInfo(@PathVariable String code) {
        Map<String, Object> data = new HashMap<>();
        data.put("stock_name", "测试股票-" + code);
        data.put("market", code.startsWith("6") ? "SH" : "SZ");
        return Result.success(data);
    }
} 
