package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@ServerEndpoint("/ws/v1/market/subscribe")
@Tag(name = "行情数据模块", description = "股票市场数据接口 (WebSocket)")
public class WebSocketController {

    private static final Logger logger = Logger.getLogger(WebSocketController.class.getName());
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    // sessionId -> Set<stockCode>
    private static final Map<String, Set<String>> clientSubscriptions = new ConcurrentHashMap<>();
    // stockCode -> Set<sessionId>
    private static final Map<String, Set<String>> stockToSessionIds = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final String STOCK_API_URL_BASE = "http://localhost:8080/api/v1/market/getstock/"; // Assuming {codes} is path param

    // Static initializer to start the scheduler
    static {
        scheduler.scheduleAtFixedRate(WebSocketController::broadcastMarketData, 5, 5, TimeUnit.MINUTES); // Broadcast every 5 seconds
        logger.info("WebSocket Market Data Scheduler started.");
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session.getId(), session);
        clientSubscriptions.put(session.getId(), Collections.synchronizedSet(new HashSet<>()));
        logger.info("WebSocket connection opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("Message from " + session.getId() + ": " + message);
        try {
            JSONObject jsonMessage = new JSONObject(message);
            String action = jsonMessage.optString("action");
            JSONArray stockCodesJson = jsonMessage.optJSONArray("stock_codes");
            Set<String> stockCodes = new HashSet<>();
            if (stockCodesJson != null) {
                for (int i = 0; i < stockCodesJson.length(); i++) {
                    stockCodes.add(stockCodesJson.getString(i).toUpperCase());
                }
            }

            if ("subscribe".equalsIgnoreCase(action)) {
                handleSubscription(session, stockCodes, true);
                 // Optionally, send current data immediately upon subscription for a better UX
                sendCurrentDataForSession(session, stockCodes);
            } else if ("unsubscribe".equalsIgnoreCase(action)) {
                handleSubscription(session, stockCodes, false);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing message from " + session.getId(), e);
        }
    }
    
    private void sendCurrentDataForSession(Session session, Set<String> stockCodes) {
        if (stockCodes == null || stockCodes.isEmpty()) {
            return;
        }
        List<JSONObject> stockDataList = fetchStockData(stockCodes);
        if (!stockDataList.isEmpty()) {
            try {
                session.getBasicRemote().sendText(new JSONArray(stockDataList).toString());
                logger.info("Sent initial data for " + stockCodes + " to session " + session.getId());
            } catch (IOException e) {
                logger.log(Level.WARNING, "Failed to send initial data to session " + session.getId(), e);
            }
        }
    }


    private void handleSubscription(Session session, Set<String> stockCodes, boolean subscribe) {
        String sessionId = session.getId();
        Set<String> currentSessionSubscriptions = clientSubscriptions.getOrDefault(sessionId, Collections.synchronizedSet(new HashSet<>()));

        for (String stockCode : stockCodes) {
            if (subscribe) {
                currentSessionSubscriptions.add(stockCode);
                stockToSessionIds.computeIfAbsent(stockCode, k -> Collections.synchronizedSet(new HashSet<>())).add(sessionId);
                logger.info("Session " + sessionId + " subscribed to " + stockCode);
            } else {
                currentSessionSubscriptions.remove(stockCode);
                Set<String> sessionsForStock = stockToSessionIds.get(stockCode);
                if (sessionsForStock != null) {
                    sessionsForStock.remove(sessionId);
                    if (sessionsForStock.isEmpty()) {
                        stockToSessionIds.remove(stockCode);
                    }
                }
                logger.info("Session " + sessionId + " unsubscribed from " + stockCode);
            }
        }
        clientSubscriptions.put(sessionId, currentSessionSubscriptions);
    }

    @OnClose
    public void onClose(Session session) {
        String sessionId = session.getId();
        sessions.remove(sessionId);
        Set<String> subscribedCodes = clientSubscriptions.remove(sessionId);
        if (subscribedCodes != null) {
            for (String stockCode : subscribedCodes) {
                Set<String> sessionsForStock = stockToSessionIds.get(stockCode);
                if (sessionsForStock != null) {
                    sessionsForStock.remove(sessionId);
                    if (sessionsForStock.isEmpty()) {
                        stockToSessionIds.remove(stockCode);
                    }
                }
            }
        }
        logger.info("WebSocket connection closed: " + sessionId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.log(Level.SEVERE, "WebSocket error for session " + session.getId(), throwable);
        // Optionally, try to close the session gracefully if an error occurs
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error closing session after error", e);
        } finally {
            // Ensure cleanup like onClose
            onClose(session);
        }
    }

    private static void broadcastMarketData() {
        Set<String> allSubscribedCodes = new HashSet<>();
        stockToSessionIds.keySet().forEach(allSubscribedCodes::add);

        if (allSubscribedCodes.isEmpty()) {
            // logger.info("No active subscriptions. Skipping data fetch.");
            return;
        }

        List<JSONObject> fetchedStockDataList = fetchStockData(allSubscribedCodes);
        if (fetchedStockDataList.isEmpty()) {
            // logger.info("No data fetched for codes: " + allSubscribedCodes);
            return;
        }
        
        // Create a map of stockCode to its data for efficient lookup
        Map<String, JSONObject> dataMap = fetchedStockDataList.stream()
            .collect(Collectors.toMap(obj -> obj.getString("stock_code"), obj -> obj));

        sessions.forEach((sessionId, session) -> {
            if (session.isOpen()) {
                Set<String> sessionSubscriptions = clientSubscriptions.get(sessionId);
                if (sessionSubscriptions != null && !sessionSubscriptions.isEmpty()) {
                    List<JSONObject> dataForThisSession = new ArrayList<>();
                    for (String subscribedCode : sessionSubscriptions) {
                        if (dataMap.containsKey(subscribedCode)) {
                            dataForThisSession.add(dataMap.get(subscribedCode));
                        }
                    }
                    
                    if (!dataForThisSession.isEmpty()) {
                        try {
                            session.getBasicRemote().sendText(new JSONArray(dataForThisSession).toString());
                            // logger.info("Sent data to session " + sessionId + " for codes: " + sessionSubscriptions);
                        } catch (IOException e) {
                            logger.log(Level.WARNING, "Failed to send data to session " + sessionId, e);
                        }
                    }
                }
            }
        });
    }

    // Fetches and transforms stock data
    private static List<JSONObject> fetchStockData(Set<String> stockCodes) {
        List<JSONObject> transformedDataList = new ArrayList<>();
        if (stockCodes.isEmpty()) {
            return transformedDataList;
        }

        String codesParam = String.join(",", stockCodes);
        try {
            URL url = new URL(STOCK_API_URL_BASE + codesParam);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000); // 5 seconds
            con.setReadTimeout(5000);   // 5 seconds

            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();

                JSONArray rawDataArray = new JSONArray(content.toString());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); // Assuming market time is Shanghai time

                for (int i = 0; i < rawDataArray.length(); i++) {
                    JSONObject rawStock = rawDataArray.getJSONObject(i);
                    JSONObject transformedStock = new JSONObject();
                    transformedStock.put("stock_code", rawStock.optString("stockCode", rawStock.optString("stock_code")));
                    transformedStock.put("name", rawStock.optString("name"));
                    transformedStock.put("price", rawStock.optString("price")); // Keep as string, client parses
                    transformedStock.put("lastPrice", rawStock.optString("lastPrice"));
                    transformedStock.put("openPrice", rawStock.optString("openPrice"));
                    transformedStock.put("high", rawStock.optString("high"));
                    transformedStock.put("low", rawStock.optString("low"));
                    transformedStock.put("volume", rawStock.optString("amount", rawStock.optString("volume"))); // Map 'amount' to 'volume'
                    
                    // Add a time field; if API provides one use it, otherwise current time.
                    // Assuming API provides 'time' field, e.g., "14:30:00"
                    // If not, we can add current server time formatted.
                    transformedStock.put("time", rawStock.optString("time", sdf.format(new Date())));


                    transformedDataList.add(transformedStock);
                }
            } else {
                logger.warning("Failed to fetch stock data. HTTP Status: " + status + " for codes: " + codesParam);
                 try (BufferedReader errorStream = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String errorLine;
                    StringBuilder errorResponse = new StringBuilder();
                    while ((errorLine = errorStream.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    logger.warning("Error response: " + errorResponse.toString());
                } catch (Exception e) {
                    logger.warning("Additionally, failed to read error stream: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception fetching stock data for codes: " + codesParam, e);
        }
        return transformedDataList;
    }
}
