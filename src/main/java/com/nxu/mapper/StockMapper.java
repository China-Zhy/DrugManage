package com.nxu.mapper;

import com.nxu.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZhangHongYe
 */
@Mapper
public interface StockMapper {

    int insertStock(Stock stock);

    /**
     * 使用乐观锁解决多人同时出库/入库导致的库存一致性问题
     *
     * @param id      库存编号
     * @param count   最终库存数
     * @param version 库存版本
     * @return 出入库结果
     */
    int updateStock(@Param("id") int id, @Param("count") int count, @Param("version") int version);

    int deleteStock(int id);

    Stock getStockById(int id);

    List<Stock> selectStock(Integer medicineId);

}