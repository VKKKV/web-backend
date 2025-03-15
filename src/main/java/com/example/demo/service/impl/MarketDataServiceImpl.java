package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.MarketData;
import com.example.demo.service.MarketDataService;
import com.example.demo.mapper.MarketDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author Kita
* @description 针对表【market_data】的数据库操作Service实现
* @createDate 2025-02-24 16:42:24
*/
@Service
public class MarketDataServiceImpl extends ServiceImpl<MarketDataMapper, MarketData> implements MarketDataService {

    @Autowired
    private MarketDataMapper marketDataMapper;

    @Override
    public List<MarketData> getHistoryData(Integer stockId, Date startTime, Date endTime) {
        LambdaQueryWrapper<MarketData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MarketData::getStockId, stockId)
                   .ge(MarketData::getTimestamp, startTime)
                   .le(MarketData::getTimestamp, endTime)
                   .orderByAsc(MarketData::getTimestamp);

        return marketDataMapper.selectList(queryWrapper);
    }
}




