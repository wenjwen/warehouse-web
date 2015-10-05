package com.warehouse.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.warehouse.mapper.MaterialMapper;
import com.warehouse.mapper.StockItemMapper;
import com.warehouse.mapper.StockMapper;
import com.warehouse.model.Material;
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
	@Resource
	private MaterialMapper materialMapper;
	

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
		
		// update material's balance
        List<Material> mList = materialMapper.selectBalanceByIds(stock.getItems());
        Map<String, StockItem> mQualityMap = new HashMap<String, StockItem>(stock.getItems().size());
        
        for(StockItem item : stock.getItems()){
            mQualityMap.put(item.getMaterialId() + "-" + item.getUnitId(), item);
        }

        for(Material m : mList){
        	StockItem item = mQualityMap.get(m.getId() + "-" + m.getUnitId());
        	BigDecimal stockAccount = item.getQuantity();
            if (stockAccount == null || stockAccount.compareTo(new BigDecimal(0)) == 0) continue;
            
            switch(stock.getStockType()){
                case 1: // buy
                	// 总价 = （旧总数 * 旧平均单价 + 入库数量 * 单价）
                	BigDecimal totalPrice = (m.getTotalQuantity() == null ? new BigDecimal(0) : m.getTotalQuantity())
                								.multiply((m.getAvgUnitPrice() == null ? new BigDecimal(0) :m.getAvgUnitPrice()))
                								.add(item.getQuantity().multiply(item.getUnitPrice()));
                	// 总数量= （旧总数 + 入库数量）
                	BigDecimal totalQuantity = (m.getTotalQuantity() == null ? new BigDecimal(0) : m.getTotalQuantity()).add(item.getQuantity());
                	// 平均单价= 总价 / 总数量
                	m.setAvgUnitPrice(totalPrice.divide(totalQuantity,2, BigDecimal.ROUND_HALF_UP));
                	m.setUnitPrice(item.getUnitPrice());
	                m.setTotalQuantity(totalQuantity);
	                m.setBalance(m.getBalance().add(stockAccount)); break;
                case 2: case 3:  // return & salse return
                    m.setBalance(m.getBalance().add(stockAccount));
                case 4: case 5: case 6:  // stock out
                    m.setBalance(m.getBalance().subtract(stockAccount)); break;
            }
            
            materialMapper.updateByPrimaryKeySelective(m);
        }

        //materialMapper.updateMaterialBalance(mList);
	}


	public List<StockSearch> stockinoutSearch(StockSearch stockSearch)
	{
		return stockMapper.stockinoutSearch(stockSearch);
	}
	
}
