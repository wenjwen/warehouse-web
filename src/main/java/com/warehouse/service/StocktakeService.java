package com.warehouse.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.mapper.MaterialMapper;
import com.warehouse.mapper.StocktakeItemMapper;
import com.warehouse.mapper.StocktakeMapper;
import com.warehouse.model.Stocktake;
import com.warehouse.model.StocktakeItem;
import com.warehouse.util.Entry;

@Service
public class StocktakeService
{
	@Resource
	private StocktakeMapper stocktakeMapper;
	@Resource
	private StocktakeItemMapper stocktakeItemMapper;
	@Resource
	private MaterialMapper materialMapper;

	public void insert(Stocktake s)
	{
		stocktakeMapper.insert(s);
		// 生成盘点条目
		List<Entry> mEntry = materialMapper.findAllEntry();
		if (mEntry != null && mEntry.size() > 0){
			List<StocktakeItem> itemList = new ArrayList<StocktakeItem>(mEntry.size());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			for(Entry m : mEntry){
				StocktakeItem item = new StocktakeItem();
				item.setStocktakeId(s.getId());
				item.setMaterialId(m.getId());
				item.setUnitId(new Integer(m.getExtraValue1().toString()));
				item.setBalance(new Double(m.getExtraValue3().toString()));
				item.setCreateTime(df.format(now));
				item.setUpdateTime(df.format(now));
				itemList.add(item);
			}
			// batch insert
			stocktakeItemMapper.batchInsert(itemList);
		}
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

	public Stocktake findById(Integer stocktakeId)
	{
		return stocktakeMapper.selectByPrimaryKey(stocktakeId);
	}

	public List<StocktakeItem> findItemsByStocktakeId(Integer stocktakeId)
	{
		return stocktakeItemMapper.findByStocktakeId(stocktakeId);
	}

	
}
