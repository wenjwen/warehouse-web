package com.warehouse.mapper;

import java.util.List;

import com.warehouse.common.BaseMapper;
import com.warehouse.model.Material;
import com.warehouse.model.StockItem;
import com.warehouse.model.StocktakeItem;
import com.warehouse.util.Entry;

public interface MaterialMapper extends BaseMapper<Material>{
    
	int deleteByPrimaryKey(Integer id);

    int insertSelective(Material record);

    Material selectByPrimaryKey(Integer id);

	List<Entry> findAllEntry();

	List<Material> findAll();
	
	List<Material> findFroImport();
	
	List<Material> findSelective(Material material);

	List<Material> selectBalanceByIds(List<StockItem> items);

	void updateForStocktake(StocktakeItem item);
    
}