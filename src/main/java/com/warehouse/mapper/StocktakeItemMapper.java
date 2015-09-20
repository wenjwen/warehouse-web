package com.warehouse.mapper;

import java.util.List;

import com.warehouse.model.StocktakeItem;

public interface StocktakeItemMapper {
	
    int insert(StocktakeItem record);

    int insertSelective(StocktakeItem record);

	void deleteByStocktakeId(Integer id);

	void batchInsert(List<StocktakeItem> list);

	List<StocktakeItem> findByStocktakeId(Integer stocktakeId);
}