package com.warehouse.mapper;

import java.util.List;

import com.warehouse.common.BaseMapper;
import com.warehouse.model.Category;

public interface CategoryMapper extends BaseMapper<Category>{
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

	List<Category> findSelective(Category category);

	List<Category> findAllEntry();
	
	List<Category> findAll();
	
}