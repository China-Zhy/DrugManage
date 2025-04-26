package com.nxu.mapper;

import com.nxu.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockMapper {

    int insertStock(Stock stock);

    int updateStock(@Param("id") int id, @Param("count") int count);

    Stock getStockById(int id);

    List<Stock> selectStock(Integer medicineId);
}