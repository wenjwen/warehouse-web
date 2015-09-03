package com.warehouse.mapper;

import java.util.List;

import com.warehouse.model.Material;
import com.warehouse.util.Entry;

public interface MaterialMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(Material record);

    int insertSelective(Material record);

    Material selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Material record);

    int updateByPrimaryKey(Material record);

	List<Entry> findAllEntry();

	List<Material> findAll();
	
	List<Material> findSelective(Material material);
    
}