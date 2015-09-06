package com.warehouse.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.warehouse.mapper.StockItemMapper;
import com.warehouse.mapper.StockMapper;
import com.warehouse.model.Stock;
import com.warehouse.model.StockItem;
import com.warehouse.model.StockSearch;

@Service
public class StockService
{
	private Logger logger = Logger.getLogger(StockService.class);
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private StockItemMapper itemMapper;
	

	public void save(Stock stock) throws Exception
	{
		stockMapper.insert(stock);
		logger.debug("---stock id=" + stock.getId());
		// 设置item的stock id
		if (stock.getItems() != null){
			for(StockItem item : stock.getItems()){
				item.setStockId(stock.getId());
			}
		}
		
		itemMapper.batchInsert(stock.getItems());
	}


	public List<StockSearch> stockinoutSearch(StockSearch stockSearch)
	{
		return stockMapper.stockinoutSearch(stockSearch);
	}
	
}
