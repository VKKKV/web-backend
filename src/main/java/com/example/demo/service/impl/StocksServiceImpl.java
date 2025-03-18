package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Stocks;
import com.example.demo.service.StocksService;
import com.example.demo.mapper.StocksMapper;
import com.example.demo.vo.StockInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kita
 * @description 针对表【stocks】的数据库操作Service实现
 * @createDate 2025-02-24 16:42:24
 */
@Service
public class StocksServiceImpl extends ServiceImpl<StocksMapper, Stocks>
        implements StocksService {

    @Autowired
    private StocksMapper stocksMapper;

    @Override
    public Stocks getByStockCode(String stockCode) {
        LambdaQueryWrapper<Stocks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Stocks::getStockCode, stockCode);
        return stocksMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Stocks> getStocksByPage(IPage<Stocks> page,
                                         String keyword) {
        LambdaQueryWrapper<Stocks> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(keyword)) {
            String searchKey = "%" + keyword + "%";
            wrapper.and(wq -> wq
                    .like(Stocks::getStockId, searchKey)
                    .or()
                    .like(Stocks::getStockCode, searchKey)
                    .or()
                    .like(Stocks::getStockName, searchKey)
                    .or()
                    .like(Stocks::getMarket, searchKey)
            );
        }
        return stocksMapper.selectPage(page, wrapper);
    }
}




