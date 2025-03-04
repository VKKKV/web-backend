package com.example.demo.controller;

import com.example.demo.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1/trade")
@Tag(name = "交易模块", description = "用户交易操作接口")
public class TradeController {

    @PostMapping("/order")
    @Operation(summary = "提交交易订单")
    public Result<Map<String, Object>> createOrder(
        @RequestHeader String Authorization,
        @RequestBody Map<String, Object> orderRequest) {
        
        Map<String, Object> data = new HashMap<>();
        data.put("order_id", "TEST202403150001");
        data.put("status", "PROCESSING");
        data.put("executed_price", orderRequest.get("price"));
        return Result.success(data);
    }

    @GetMapping("/order/{order_id}")
    @Operation(summary = "查询订单状态")
    public Result<Map<String, Object>> getOrderStatus(
        @PathVariable String order_id) {
        
        Map<String, Object> data = new HashMap<>();
        data.put("order_id", order_id);
        data.put("status", new Random().nextBoolean() ? "SUCCESS" : "PROCESSING");
        return Result.success(data);
    }

    @GetMapping("/history")
    @Operation(summary = "获取用户交易历史")
    public Result<List<Map<String, Object>>> getTradeHistory(
        @RequestParam(required = false) Date start_time,
        @RequestParam(required = false) Date end_time) {
        
        List<Map<String, Object>> testData = new ArrayList<>();
        for(int i=1; i<=3; i++){
            Map<String, Object> item = new HashMap<>();
            item.put("stock_code", "60051" + i);
            item.put("type", i%2==0 ? "BUY" : "SELL");
            item.put("quantity", 100*i);
            item.put("price", 150.0 + i*5);
            testData.add(item);
        }
        return Result.success(testData);
    }
}