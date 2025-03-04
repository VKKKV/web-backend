package com.example.demo.controller;

import com.example.demo.common.result.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/strategy")
@Tag(name = "自动化交易模块", description = "量化策略管理接口")
public class StrategyController {

    @PostMapping
    @Operation(summary = "创建交易策略")
    public ResponseResult<Map<String, Object>> createStrategy(
        @RequestHeader String Authorization,
        @RequestBody Map<String, Object> strategy) {
        
        Map<String, Object> data = new HashMap<>();
        data.put("strategy_id", "STRAT20240315001");
        data.put("status", "ACTIVE");
        return ResponseResult.success(data);
    }

    @PutMapping("/{strategy_id}")
    @Operation(summary = "启动/停止策略")
    public ResponseResult<String> controlStrategy(
        @PathVariable String strategy_id,
        @RequestParam String action) {
        
        return ResponseResult.success("策略状态已更新：" + action);
    }

    @GetMapping("/logs")
    @Operation(summary = "获取策略执行日志")
    public ResponseResult<List<Map<String, Object>>> getStrategyLogs(
        @RequestParam String strategy_id) {
        
        List<Map<String, Object>> logs = new ArrayList<>();
        for(int i=1; i<=3; i++){
            Map<String, Object> log = new HashMap<>();
            log.put("timestamp", new Date());
            log.put("action", i%2==0 ? "BUY" : "SELL");
            log.put("quantity", 100*i);
            logs.add(log);
        }
        return ResponseResult.success(logs);
    }
} 