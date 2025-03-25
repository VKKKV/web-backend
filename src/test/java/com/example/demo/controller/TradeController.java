package com.example.demo.controller;

import com.example.demo.common.result.Result;
import com.example.demo.service.TransactionsService;
import com.example.demo.vo.OrderRequestVO;
import com.example.demo.vo.OrderResponseVO;
import com.example.demo.vo.TradeHistoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TradeController {


    @Autowired
    private TransactionsService transactionsService;

    /**
     * 提交交易订单
     * @param orderRequest 订单请求信息
     * @return 订单响应信息
     */
    @Operation(summary = "提交订单", description = "创建股票买入或卖出订单")
    @PostMapping("/order")
    public Result<?> createOrder(
            @Parameter(description = "userid")
            @RequestHeader(required = false) Integer userid,

            @Parameter(description = "订单请求", required = true)
            @RequestBody OrderRequestVO orderRequest) {
        transactionsService.createOrder(orderRequest);

        return Result.success();
    }

    /**
     * 查询订单状态
     * @param orderId 订单ID
     * @return 订单详细信息
     */
    @Operation(summary = "查询订单", description = "获取订单的详细信息和状态")
    @GetMapping("/order/{orderId}")
    public Result<OrderResponseVO> getOrderStatus(
            @Parameter(description = "订单ID", example = "ORDER123456789", required = true)
            @PathVariable String orderId) {

        OrderResponseVO response = OrderResponseVO.builder()
                .orderId(orderId)
                .status(new Random().nextBoolean() ? "SUCCESS" : "FAIL")
                .executedPrice(150.75)
                .createTime(new Date().toString())
                .updateTime(new Date().toString())
                .build();

        return Result.success(response);
    }

    /**
     * 获取交易历史记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易历史记录列表
     */
    @Operation(summary = "交易历史", description = "查询用户的历史交易记录")
    @GetMapping("/history")
    public Result<List<TradeHistoryVO>> getTradeHistory(
            @Parameter(description = "开始时间", example = "2023-01-01")
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,

            @Parameter(description = "结束时间", example = "2023-01-31")
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {

        List<TradeHistoryVO> testData = new ArrayList<>();
        for(int i=1; i<=3; i++){
            TradeHistoryVO item = TradeHistoryVO.builder()
                    .tradeId("TRADE" + (1000 + i))
                    .stockCode("60051" + i)
                    .stockName("测试股票" + i)
                    .type(i%2==0 ? "BUY" : "SELL")
                    .quantity(100*i)
                    .price(150.0 + i*5)
                    .amount((150.0 + i*5) * (100*i))
                    .tradeTime(new Date())
                    .status("SUCCESS")
                    .build();
            testData.add(item);
        }
        return Result.success(testData);
    }

}
