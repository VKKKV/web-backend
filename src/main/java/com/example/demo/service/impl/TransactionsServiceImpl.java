package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Transactions;
import com.example.demo.service.TransactionsService;
import com.example.demo.mapper.TransactionsMapper;
import org.springframework.stereotype.Service;

/**
* @author Kita
* @description 针对表【transactions】的数据库操作Service实现
* @createDate 2025-02-24 16:42:24
*/
@Service
public class TransactionsServiceImpl extends ServiceImpl<TransactionsMapper, Transactions>
    implements TransactionsService{

}




