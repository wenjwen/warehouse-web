package com.warehouse.mapper;

import java.util.List;

import com.warehouse.model.Dict;

public interface DictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

	List<Dict> findByType(int type);

	List<Dict> findAllEntry();

}