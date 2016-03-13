package com.warehouse.mapper;

import java.util.List;

import com.warehouse.common.BaseMapper;
import com.warehouse.model.StockDetail;

public interface StockDetailMapper extends BaseMapper<StockDetail>{

	void batchInsert(List<StockDetail> list);
    
}