package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Transactions;
import com.example.demo.service.TransactionsService;
import com.example.demo.service.UsersService;
import com.example.demo.mapper.TransactionsMapper;
import com.example.demo.vo.OrderRequestVO;
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

    @Autowired
    private UsersService usersService;

    @Override
    public IPage<Transactions> getHistoryPage(IPage<Transactions> page, Integer userId) {
        LambdaQueryWrapper<Transactions> wrapper = new LambdaQueryWrapper<>();

        //select by userId
        wrapper.eq(Transactions::getUserId, userId);

        return transactionsMapper.selectPage(page, wrapper);
    }

    @Override
    public void createOrder(OrderRequestVO orderRequest) {
        Integer userId = orderRequest.getUserId();
        String type = orderRequest.getType();
        BigDecimal price = BigDecimal.valueOf(orderRequest.getPrice());
        BigDecimal quantity = new BigDecimal(orderRequest.getQuantity().toString());
        BigDecimal totalAmount = price.multiply(quantity);

        if ("BUY".equalsIgnoreCase(type)) {
            usersService.updateUserBalance(userId, totalAmount.negate());

        } else if ("SELL".equalsIgnoreCase(type)) {
            usersService.updateUserBalance(userId, totalAmount);

        } else {
            throw new com.example.demo.common.exception.TradeException("无效的交易类型", com.example.demo.common.result.ResultCodeEnum.PARAM_ERROR.getCode());
        }

        Transactions transactions = new Transactions();
        transactions.setStockId(0);
        transactions.setUserId(userId);
        transactions.setStockCode(orderRequest.getStockCode());
        transactions.setActionType(type.toUpperCase());
        transactions.setQuantity(orderRequest.getQuantity());
        transactions.setPrice(price);
        transactions.setStatus("EXECUTED");
        transactions.setOrderType(orderRequest.getOrderType());

        transactionsMapper.insert(transactions);
    }
}




