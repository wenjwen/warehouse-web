package com.warehouse.mapper;

import com.warehouse.model.TakeStock;

public interface TakeStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TakeStock record);

    int insertSelective(TakeStock record);

    TakeStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TakeStock record);

    int updateByPrimaryKey(TakeStock record);
}