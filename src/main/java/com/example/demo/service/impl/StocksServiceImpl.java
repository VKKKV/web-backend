package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Stocks;
import com.example.demo.service.StocksService;
import com.example.demo.mapper.StocksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author Kita
* @description 针对表【stocks】的数据库操作Service实现
* @createDate 2025-02-24 16:42:24
*/
@Service
public class StocksServiceImpl extends ServiceImpl<StocksMapper, Stocks>
    implements StocksService{

    @Autowired
    private StocksMapper stocksMapper;

    @Override
    public Stocks getByStockCode(String stockCode) {
        LambdaQueryWrapper<Stocks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Stocks::getStockCode, stockCode);
        return stocksMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Stocks> getStocksByPage(int page, int pageSize, String keyword) {
        // 创建分页对象
        Page<Stocks> pageParam = new Page<>(page, pageSize);

        // 创建查询条件
        LambdaQueryWrapper<Stocks> queryWrapper = new LambdaQueryWrapper<>();

        // 如果有关键词，添加模糊查询条件
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Stocks::getStockCode, keyword)
                       .or()
                       .like(Stocks::getStockName, keyword);
        }

        // 按股票代码排序
        queryWrapper.orderByAsc(Stocks::getStockCode);

        // 执行分页查询
        return stocksMapper.selectPage(pageParam, queryWrapper);
    }
}




