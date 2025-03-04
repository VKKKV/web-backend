package com.example.demo.controller;

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
import java.util.stream.Collectors;


@ServerEndpoint("/ws/v1/market/subscribe")
@Tag(name = "行情数据模块", description = "股票市场数据接口")
public class WebSocketController {
    private static final Map<String, List<Double>> STOCK_BASE = Map.of(
            "AAPL", Arrays.asList(185.0, 5.0),
            "TSLA", Arrays.asList(250.0, 8.0)
    );

    // 线程安全的连接管理
    private static final ConcurrentHashMap<Session, ScheduledExecutorService> SESSION_MAP = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        // 每个连接独立处理
        SESSION_MAP.put(session, Executors.newSingleThreadScheduledExecutor());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject json = new JSONObject(message);
        List<String> codes = json.getJSONArray("stock_codes").toList()
                .stream().map(Object::toString).toList();

        // 为当前会话创建独立定时任务
        ScheduledExecutorService scheduler = SESSION_MAP.get(session);
        scheduler.scheduleAtFixedRate(() -> {
            JSONObject mergedData = new JSONObject();
            codes.forEach(code -> {
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
            });
            sendMessage(session, mergedData.toString());
        }, 0, 3, TimeUnit.SECONDS);
    }

    @OnClose
    public void onClose(Session session) {
        // 关闭时清理资源
        ScheduledExecutorService scheduler = SESSION_MAP.remove(session);
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

    private void sendMessage(Session session, String message) {
        if (session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
