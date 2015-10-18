package com.warehouse.mapper;

import java.util.List;

import com.warehouse.model.StockItem;

public interface StockItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockItem record);

    int insertSelective(StockItem record);

    StockItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockItem record);

    int updateByPrimaryKey(StockItem record);
    
    void batchInsert(List<StockItem> list);

	void deleteByStockId(Object stockId);

	List<StockItem> findItemsByStockId(Integer stockId);
}