package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Record;
import com.nxu.entity.Stock;
import com.nxu.entity.User;

import java.util.List;

public interface StockService {

    int changeStock(int id, int count);

    Stock getStockById(int id);

    PageInfo<Stock> getSomeStock(Integer medicineId, Integer page, Integer limit);

    /**
     * 药品入库(批量药品)
     *
     * @param records 入库信息集合
     * @param user    操作用户
     * @return 批量入库结果
     */
    int inputStock(List<com.nxu.entity.Record> records, User user);

    /**
     * 药品出库(单个药品)
     *
     * @param record 出库记录
     * @param user   操作用户
     * @return 出库结果
     */
    int outputStock(Record record, User user);
}