package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Record;
import com.nxu.entity.Stock;
import com.nxu.entity.User;
import com.nxu.mapper.RecordMapper;
import com.nxu.mapper.StockMapper;
import com.nxu.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private RecordMapper recordMapper;

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
        if (page != null && limit != null) {
            PageHelper.startPage(page, limit);
        }
        return new PageInfo<>(stockMapper.selectStock(medicineId));
    }

    /**
     * 药品入库(批量)
     *
     * @param records 入库信息集合
     * @param user    操作用户
     * @return 批量入库结果
     */
    @Override
    @Transactional
    public int inputStock(List<com.nxu.entity.Record> records, User user) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int isSuccess = 1;  // 判断整个循环是否都没有错

        for (Record record : records) {

            String recordBirthday = sdf.format(record.getBirthday());   // 格式化页面提交过来的日期

            // 先查询是否存在相同的库存
            List<Stock> stocks = stockMapper.selectStock(record.getWhat());
            int isHave = 0;     // 标识是否有同样的库存
            for (Stock stock : stocks) {
                if (stock.getPrice() == record.getPrice() && recordBirthday.equals(sdf.format(stock.getBirthday()))) {
                    isHave = stock.getId();
                    break;
                }
            }

            record.setWho(user.getId());        // 操作用户编号
            record.setType(1);                  // 入库-1 出库-2

            int i, j;   // 库存操作结果, 记录操作结果

            if (isHave == 0) {  // 没有相同的库存
                Stock stock = new Stock();      // 创建新的库存
                stock.setMedicineId(record.getWhat());
                stock.setBirthday(record.getBirthday());
                stock.setPrice(record.getPrice());
                stock.setCount(record.getCount());

                i = stockMapper.insertStock(stock);     // 插入新的库存

            } else {    // 存在相同的库存

                Stock stock = stockMapper.getStockById(isHave);
                i = stockMapper.updateStock(stock.getId(), (stock.getCount() + record.getCount()));     // 入库 加法操作
            }

            j = recordMapper.insertRecord(record);      // 添加库存变动记录

            if (i != 1 || j != 1) {
                throw new RuntimeException("Tips：入库失败！事务回滚！");
            }
        }
        return isSuccess;
    }

    /**
     * 药品出库(单个药品)
     *
     * @param record 出库记录
     * @param user   操作用户
     * @return 出库结果
     */
    @Override
    @Transactional
    public int outputStock(Record record, User user) {

        Stock stock = stockMapper.getStockById(record.getId());

        record.setWhat(stock.getMedicineId());
        record.setType(2);          // 入库-1 出库-2
        record.setBirthday(stock.getBirthday());
        record.setFrom(1);          // 目前出库均从本地医院仓库，故供应商编号为1

        record.setWho(user.getId());

        int i = recordMapper.insertRecord(record);

        int j = stockMapper.updateStock(stock.getId(), (stock.getCount() - record.getCount()));     // 出库 减法操作

        if (i != 1 || j != 1) {
            throw new RuntimeException("Tips：入库失败！事务回滚！");
        } else {
            return 1;
        }
    }
}