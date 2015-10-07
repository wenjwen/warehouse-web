package com.warehouse.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.mapper.MaterialMapper;
import com.warehouse.mapper.StocktakeItemMapper;
import com.warehouse.mapper.StocktakeMapper;
import com.warehouse.model.Stocktake;
import com.warehouse.model.StocktakeItem;
import com.warehouse.util.Constant;
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
			//List<StocktakeItem> itemList = new ArrayList<StocktakeItem>(mEntry.size());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			for(Entry m : mEntry){
				StocktakeItem item = new StocktakeItem();
				item.setStocktakeId(s.getId());
				item.setMaterialId(m.getId());
				item.setUnitId(new Integer(m.getExtraValue1().toString()));
				item.setBalance(new BigDecimal(m.getExtraValue3().toString()));
				item.setCreateTime(df.format(now));
				item.setUpdateTime(df.format(now));
				//itemList.add(item);
				stocktakeItemMapper.insert(item);
			}
			// batch insert
			// stocktakeItemMapper.batchInsert(itemList);
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

	public void updateItem(StocktakeItem item)
	{
		/*if (item.getBalance() != null && item.getQuantity() != null){
			item.setDifferQuantity(item.getQuantity().subtract(item.getBalance()));
			item.setResult(item.getDifferQuantity().doubleValue() < 0 ? "-1" : (item.getDifferQuantity().doubleValue() == 0 ? "0" : "1"));  // -1:盘亏 0:正常 1:盘盈
		}*/
		item.setUpdateTime(Constant.DATETIME_FORMATTER.format(new Date()));
		stocktakeItemMapper.update(item);
	}

	public List<StocktakeItem> findItemsByParam(StocktakeItem item)
	{
		return stocktakeItemMapper.findItemsByParam(item);
	}

	public void submmit(Integer stocktakeId) throws Exception
	{
		Date now = new Date();
		// 更新库存；更改盘点提交标志
		List<StocktakeItem> items = stocktakeItemMapper.findByStocktakeId(stocktakeId);
		//materialMapper.batchUpdateForStocktake(items);
		for(StocktakeItem item : items){  // 计算盘点结果
			if (item.getBalance() != null && item.getQuantity() != null){
				item.setDifferQuantity(item.getQuantity().subtract(item.getBalance()));
				item.setResult(item.getDifferQuantity().doubleValue() < 0 ? "-1" : (item.getDifferQuantity().doubleValue() == 0 ? "0" : "1"));  // -1:盘亏 0:正常 1:盘盈
				item.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
				stocktakeItemMapper.update(item);
				
				materialMapper.updateForStocktake(item);
			}
		}
		Stocktake st = new Stocktake();
		st.setId(stocktakeId);
		st.setSubmitted(1); // 已提交
		st.setSubmitTime(Constant.DATETIME_FORMATTER.format(now));
		stocktakeMapper.updateByPrimaryKeySelective(st);
	}

	
}
