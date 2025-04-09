package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Stock;
import com.nxu.mapper.StockMapper;
import com.nxu.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public int addStock(Stock stock) {
        return stockMapper.insertStock(stock);
    }

    @Override
    public int changeStock(int id, int count) {
        return stockMapper.updateStock(id, count);
    }

    @Override
    public Stock getStockById(int id) {
        return stockMapper.getStockById(id);
    }

    @Override
    public PageInfo<Stock> getSomeStock(Integer medicineId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Stock> stocks = stockMapper.selectStock(medicineId);
        return new PageInfo<>(stocks);
    }
}