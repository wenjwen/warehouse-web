package com.warehouse.mapper;

import com.warehouse.model.Stocktake;

public interface StocktakeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stocktake record);

    int insertSelective(Stocktake record);

    Stocktake selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stocktake record);

    int updateByPrimaryKey(Stocktake record);
}