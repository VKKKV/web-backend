package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Stocks;
import com.example.demo.entity.Transactions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.vo.OrderRequestVO;

/**
* @author Kita
* @description 针对表【transactions】的数据库操作Service
* @createDate 2025-02-24 16:42:24
*/
public interface TransactionsService extends IService<Transactions> {

    IPage<Transactions> getHistoryPage(IPage<Transactions> page, Integer userId);
    void createOrder(OrderRequestVO orderRequest);
}
