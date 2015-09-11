package com.warehouse.mapper;

import com.warehouse.model.StocktakeItem;

public interface StocktakeItemMapper {
    int insert(StocktakeItem record);

    int insertSelective(StocktakeItem record);
}