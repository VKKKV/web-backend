package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Stocks;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Kita
* @description 针对表【stocks】的数据库操作Service
* @createDate 2025-02-24 16:42:24
*/
public interface StocksService extends IService<Stocks> {
    /**
     * 根据股票代码查询股票信息
     * @param stockCode 股票代码
     * @return 股票信息
     */
    Stocks getByStockCode(String stockCode);

    /**
     * 分页查询股票信息
     *
     * @param page     页码
     * @param keyword  搜索关键词（可选）
     * @return 分页结果
     */
    IPage<Stocks> getStocksByPage(IPage<Stocks> page, String keyword);

    Integer getStockIdByStockCode(String stockCode);
}
