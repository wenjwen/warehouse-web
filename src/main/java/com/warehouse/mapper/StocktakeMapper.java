package com.warehouse.mapper;

import java.util.List;

import com.warehouse.model.Stocktake;
import com.warehouse.util.Entry;

public interface StocktakeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stocktake record);

    int insertSelective(Stocktake record);

    Stocktake selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stocktake record);

    int updateByPrimaryKey(Stocktake record);

	List<Stocktake> findAll(Stocktake s);

	List<Entry> findAllEntry();
}