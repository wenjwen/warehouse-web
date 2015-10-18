package com.warehouse.mapper;

import java.util.List;

import com.warehouse.common.BaseMapper;
import com.warehouse.model.Stock;
import com.warehouse.model.StockSearch;

public interface StockMapper extends BaseMapper<Stock>{
    
	int deleteByPrimaryKey(Integer id);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

	List<StockSearch> stockinoutSearch(StockSearch stockSearch);
}