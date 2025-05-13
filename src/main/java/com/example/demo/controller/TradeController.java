package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.result.Result;
import com.example.demo.entity.Stocks;
import com.example.demo.entity.Transactions;
import com.example.demo.service.TransactionsService;
import com.example.demo.vo.OrderRequestVO;
import com.example.demo.vo.OrderResponseVO;
import com.example.demo.vo.TradeHistoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 交易控制器
 * 提供股票交易下单、查询等功能
 */
@RestController
@RequestMapping("/api/v1/trade")
@Tag(name = "交易管理", description = "股票交易和订单管理接口")
public class TradeController {
    @Autowired
    private TransactionsService transactionsService;

    /**
     * 提交交易订单
     *
     * @param orderRequest 订单请求信息
     * @return 订单响应信息
     */
    @Operation(summary = "提交订单", description = "创建股票买入或卖出订单")
    @PostMapping("/order")
    public Result<OrderResponseVO> createOrder(
            @Parameter(description = "订单请求", required = true)
            @RequestBody OrderRequestVO orderRequest) {
        transactionsService.createOrder(orderRequest);
        System.out.println("post order");
        return Result.success();
    }

    /**
     * 查询订单状态
     *
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
                .status(new Random().nextBoolean() ? "SUCCESS" : "PROCESSING")
                .executedPrice(150.75)
                .createTime(new Date().toString())
                .updateTime(new Date().toString())
                .build();

        return Result.success(response);
    }

    /**
     * 获取交易历史记录
     *
     * @return 交易历史记录列表
     */
    @Operation(summary = "Page交易历史", description = "查询用户的历史交易记录")
    @GetMapping("/history")
    public Result<IPage<Transactions>> getTradeHistoryPage(
            @Parameter(description = "页码", example = "1")
            @RequestParam(defaultValue = "1") Integer current,

            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") Integer size,

            @Parameter(description = "userId")
            @RequestParam() Integer userId
    ) {
        Page<Transactions> page = new Page<>(current, size);

        IPage<Transactions> result = transactionsService.getHistoryPage(page,userId);
        return Result.success(result);
    }
}