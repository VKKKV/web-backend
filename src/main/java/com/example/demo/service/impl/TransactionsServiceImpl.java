package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Stocks;
import com.example.demo.entity.Transactions;
import com.example.demo.service.StocksService;
import com.example.demo.service.TransactionsService;
import com.example.demo.mapper.TransactionsMapper;
import com.example.demo.vo.OrderRequestVO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Kita
 * @description 针对表【transactions】的数据库操作Service实现
 * @createDate 2025-02-24 16:42:24
 */
@Service
public class TransactionsServiceImpl extends ServiceImpl<TransactionsMapper, Transactions>
        implements TransactionsService {
    @Autowired
    private TransactionsMapper transactionsMapper;

    @Override
    public IPage<Transactions> getHistoryPage(IPage<Transactions> page, Integer userId) {
        LambdaQueryWrapper<Transactions> wrapper = new LambdaQueryWrapper<>();

        //select by userId
        wrapper.eq(Transactions::getUserId, userId);

        return transactionsMapper.selectPage(page, wrapper);
    }

    @Override
    public void createOrder(OrderRequestVO orderRequest) {
        Transactions transactions = new Transactions();
        transactions.setStockId(0);
        transactions.setUserId(orderRequest.getUserId());
        transactions.setStockCode(orderRequest.getStockCode());
        transactions.setActionType(orderRequest.getType());
        transactions.setQuantity(orderRequest.getQuantity());
        transactions.setPrice(BigDecimal.valueOf(orderRequest.getPrice()));
        transactions.setStatus("PENDING");
        transactions.setOrderType(orderRequest.getOrderType());

        transactionsMapper.insert(transactions);
    }
}




