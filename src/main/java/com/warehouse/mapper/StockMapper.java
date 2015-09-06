package com.warehouse.mapper;

import java.util.List;

import com.warehouse.model.Stock;
import com.warehouse.model.StockSearch;

public interface StockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

	List<StockSearch> stockinoutSearch(StockSearch stockSearch);
}