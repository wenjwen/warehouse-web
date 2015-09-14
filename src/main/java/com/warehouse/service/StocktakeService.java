package com.warehouse.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.mapper.StocktakeItemMapper;
import com.warehouse.mapper.StocktakeMapper;
import com.warehouse.model.Stocktake;
import com.warehouse.util.Entry;

@Service
public class StocktakeService
{
	@Resource
	private StocktakeMapper stocktakeMapper;
	@Resource
	private StocktakeItemMapper stocktakeItemMapper;

	public void insert(Stocktake s)
	{
		stocktakeMapper.insert(s);
	}

	public List<Stocktake> findAll(Stocktake s)
	{
		return stocktakeMapper.findAll(s);
	}
	
	public List<Entry> findAllEntry(){
		return stocktakeMapper.findAllEntry();
	}

	public void updateSelective(Stocktake s)
	{
		stocktakeMapper.updateByPrimaryKeySelective(s);
	}

	public void deleteById(Integer id)
	{
		stocktakeItemMapper.deleteByStocktakeId(id);
		stocktakeMapper.deleteByPrimaryKey(id);
	}

	
}
