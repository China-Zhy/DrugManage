package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Stock;

public interface StockService {

    int addStock(Stock stock);

    int changeStock(int id, int count);

    Stock getStockById(int id);

    PageInfo<Stock> getSomeStock(Integer medicineId, Integer page, Integer limit);
}