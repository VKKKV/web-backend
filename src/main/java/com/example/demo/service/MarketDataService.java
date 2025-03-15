package com.example.demo.service;

import com.example.demo.entity.MarketData;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Date;
import java.util.List;

/**
* @author Kita
* @description 针对表【market_data】的数据库操作Service
* @createDate 2025-02-24 16:42:24
*/
public interface MarketDataService extends IService<MarketData> {
    /**
     * 查询指定时间范围内的股票行情数据
     * @param stockId 股票ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 行情数据列表
     */
    List<MarketData> getHistoryData(Integer stockId, Date startTime, Date endTime);
}
