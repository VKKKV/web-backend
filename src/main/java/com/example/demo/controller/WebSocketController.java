package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@ServerEndpoint("/ws/v1/market/subscribe")
@Tag(name = "行情数据模块", description = "股票市场数据接口")
public class WebSocketController {
    private static final Logger LOGGER = Logger.getLogger(WebSocketController.class.getName());

    private static final Map<String, List<Double>> STOCK_BASE = Map.of(
            "AAPL", Arrays.asList(185.0, 5.0),
            "TSLA", Arrays.asList(250.0, 8.0)
    );

    // 线程安全的连接管理
    private static final ConcurrentHashMap<Session, ScheduledExecutorService> SESSION_MAP = new ConcurrentHashMap<>();

    // 用户订阅的股票代码
    private static final ConcurrentHashMap<Session, Set<String>> USER_SUBSCRIPTIONS = new ConcurrentHashMap<>();

    // 用户数据模式设置 (true: 使用随机测试数据, false: 使用真实数据)
    private static final ConcurrentHashMap<Session, Boolean> USER_TEST_MODE = new ConcurrentHashMap<>();

    @OnOpen
    @Operation(summary = "建立WebSocket连接", description = "客户端连接WebSocket服务器时调用")
    public void onOpen(Session session) {
        LOGGER.info("新的WebSocket连接已建立: " + session.getId());
        // 每个连接独立处理
        SESSION_MAP.put(session, Executors.newSingleThreadScheduledExecutor());
        USER_SUBSCRIPTIONS.put(session, new HashSet<>());
        USER_TEST_MODE.put(session, true); // 默认使用测试数据
    }

    @OnMessage
    @Operation(summary = "接收WebSocket消息", description = "处理客户端发送的消息，包括订阅/取消订阅和数据模式设置")
    public void onMessage(String message, Session session) {
        try {
            LOGGER.info("收到消息: " + message);
            JSONObject json = new JSONObject(message);

            // 处理心跳消息
            if (json.has("type") && "ping".equals(json.getString("type"))) {
                JSONObject pong = new JSONObject();
                pong.put("type", "pong");
                pong.put("timestamp", System.currentTimeMillis());
                sendMessage(session, pong.toString());
                return;
            }

            // 处理数据模式切换
            if (json.has("action") && "setDataMode".equals(json.getString("action"))) {
                boolean useTestData = json.getBoolean("useTestData");
                USER_TEST_MODE.put(session, useTestData);

                // 发送确认消息
                JSONObject confirmation = new JSONObject();
                confirmation.put("action", "dataModeSet");
                confirmation.put("useTestData", useTestData);
                sendMessage(session, confirmation.toString());

                LOGGER.info("用户 " + session.getId() + " 设置数据模式: " + (useTestData ? "测试数据" : "真实数据"));
                return;
            }

            // 处理订阅/取消订阅请求
            if (json.has("action")) {
                String action = json.getString("action");

                if ("subscribe".equals(action) && json.has("stock_codes")) {
                    // 获取用户的订阅集合
                    Set<String> subscriptions = USER_SUBSCRIPTIONS.get(session);

                    // 添加新的订阅
                    List<Object> codesList = json.getJSONArray("stock_codes").toList();
                    List<String> codes = codesList.stream()
                            .map(Object::toString)
                            .filter(STOCK_BASE::containsKey) // 只订阅支持的股票
                            .toList();

                    subscriptions.addAll(codes);
                    LOGGER.info("用户订阅股票: " + codes);

                    // 如果这是第一次订阅，启动数据推送
                    if (subscriptions.size() > 0 && !SESSION_MAP.get(session).isShutdown()) {
                        startDataPush(session, subscriptions);
                    }

                    // 发送订阅确认
                    JSONObject confirmation = new JSONObject();
                    confirmation.put("action", "subscribed");
                    confirmation.put("stock_codes", codes);
                    sendMessage(session, confirmation.toString());
                }
                else if ("unsubscribe".equals(action) && json.has("stock_codes")) {
                    // 取消订阅
                    Set<String> subscriptions = USER_SUBSCRIPTIONS.get(session);
                    List<Object> codesList = json.getJSONArray("stock_codes").toList();

                    codesList.stream()
                            .map(Object::toString)
                            .forEach(subscriptions::remove);

                    LOGGER.info("用户取消订阅股票: " + codesList);

                    // 发送取消订阅确认
                    JSONObject confirmation = new JSONObject();
                    confirmation.put("action", "unsubscribed");
                    confirmation.put("stock_codes", codesList);
                    sendMessage(session, confirmation.toString());
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "处理消息时出错", e);
            try {
                // 发送错误响应
                JSONObject error = new JSONObject();
                error.put("error", "处理请求时出错");
                error.put("message", e.getMessage());
                sendMessage(session, error.toString());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "发送错误响应时出错", ex);
            }
        }
    }

    private void startDataPush(Session session, Set<String> codes) {
        ScheduledExecutorService scheduler = SESSION_MAP.get(session);

        // 取消之前的任务（如果有）
        scheduler.shutdownNow();

        // 创建新的调度器
        scheduler = Executors.newSingleThreadScheduledExecutor();
        SESSION_MAP.put(session, scheduler);

        // 定时推送数据
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // 获取最新的订阅列表
                Set<String> subscriptions = USER_SUBSCRIPTIONS.get(session);
                if (subscriptions == null || subscriptions.isEmpty()) {
                    return;
                }

                // 获取用户的数据模式
                boolean useTestData = USER_TEST_MODE.getOrDefault(session, true);

                JSONObject mergedData = new JSONObject();
                subscriptions.forEach(code -> {
                    if (useTestData) {
                        // 使用随机测试数据
                        generateTestData(mergedData, code);
                    } else {
                        // 使用真实数据 (这里可以添加真实数据获取逻辑)
                        generateRealData(mergedData, code);
                    }
                });

                sendMessage(session, mergedData.toString());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "推送数据时出错", e);
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    // 生成随机测试数据
    private void generateTestData(JSONObject mergedData, String code) {
        List<Double> base = STOCK_BASE.get(code);
        if (base == null) return;

        double price = base.get(0) + (Math.random() * 2 - 1) * base.get(1);
        mergedData.put(code, new JSONObject()
                .put("timestamp", System.currentTimeMillis())
                .put("open", price - Math.random() * 2)
                .put("close", price + Math.random() * 2)
                .put("high", price + Math.random() * 3)
                .put("low", price - Math.random() * 3)
                .put("volume", 10000 + (int)(Math.random() * 5000))
        );
    }

    // 生成真实数据 (示例实现，实际应从外部数据源获取)
    private void generateRealData(JSONObject mergedData, String code) {
        // 这里应该是从真实数据源获取数据的逻辑
        // 目前使用模拟数据，但波动更小，更接近真实市场
        List<Double> base = STOCK_BASE.get(code);
        if (base == null) return;

        // 使用更小的波动范围
        double basePrice = base.get(0);
        double volatility = base.get(1) * 0.2; // 降低波动性

        double change = (Math.random() * 2 - 1) * volatility;
        double price = basePrice + change;

        // 确保价格不会为负
        price = Math.max(price, 0.01);

        // 计算开盘价、收盘价、最高价和最低价，使其更符合真实市场规律
        double open = price - (Math.random() * 0.5) * change;
        double close = price + (Math.random() * 0.5) * change;
        double high = Math.max(open, close) + Math.random() * volatility * 0.3;
        double low = Math.min(open, close) - Math.random() * volatility * 0.3;

        // 成交量与价格变化成正比
        int volume = 5000 + (int)(Math.abs(change) / volatility * 10000);

        mergedData.put(code, new JSONObject()
                .put("timestamp", System.currentTimeMillis())
                .put("open", open)
                .put("close", close)
                .put("high", high)
                .put("low", low)
                .put("volume", volume)
        );
    }

    @OnClose
    @Operation(summary = "关闭WebSocket连接", description = "客户端断开连接时调用，清理资源")
    public void onClose(Session session) {
        LOGGER.info("WebSocket连接已关闭: " + session.getId());
        // 关闭时清理资源
        ScheduledExecutorService scheduler = SESSION_MAP.remove(session);
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
        USER_SUBSCRIPTIONS.remove(session);
        USER_TEST_MODE.remove(session);
    }

    private void sendMessage(Session session, String message) {
        if (session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "发送消息时出错", e);
            }
        }
    }
}
