package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Stocks;
import com.example.demo.service.StocksService;
import com.example.demo.mapper.StocksMapper;
import org.springframework.stereotype.Service;

/**
* @author Kita
* @description 针对表【stocks】的数据库操作Service实现
* @createDate 2025-02-24 16:42:24
*/
@Service
public class StocksServiceImpl extends ServiceImpl<StocksMapper, Stocks>
    implements StocksService{

}




