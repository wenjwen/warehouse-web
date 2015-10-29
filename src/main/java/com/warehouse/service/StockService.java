package com.warehouse.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import com.warehouse.common.BaseMapper;
import com.warehouse.common.BaseService;
import com.warehouse.mapper.MaterialMapper;
import com.warehouse.mapper.StockItemMapper;
import com.warehouse.mapper.StockMapper;
import com.warehouse.model.Material;
import com.warehouse.model.Stock;
import com.warehouse.model.StockItem;
import com.warehouse.model.StockSearch;
import com.warehouse.util.Constant;

@Service
public class StockService extends BaseService<Stock>
{
	private Logger logger = Logger.getLogger(StockService.class);
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private StockItemMapper itemMapper;
	@Resource
	private MaterialMapper materialMapper;
	
	@Override
	public BaseMapper<Stock> getMapper()
	{
		return stockMapper;
	}

	public void save(Stock stock) throws Exception
	{
		stockMapper.insert(stock);
		logger.debug("---stock id=" + stock.getId());
		// 设置item的stock id
		if (stock.getItems() != null && stock.getItems().size() > 0){
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

	@Override
	public int deleteById(Object id)
	{
		// TODO 更新数量
		Stock s = stockMapper.queryById(id);
		List<StockItem> items = itemMapper.findItemsByStockIdForDeleteStock((Integer)id);
		if (items != null && !items.isEmpty()){
			for(StockItem i : items){
				this.updateMaterialForDeleteItem(s.getStockType(), i);
			}
		}
		return super.deleteById(id);
	}

	public List<StockItem> findItemsByStockId(Integer stockId)
	{
		return itemMapper.findItemsByStockId(stockId);
	}

	public void deleteItem(StockItem si, Integer stockType)
	{
		// 删除stock item, 要根据stock type更新库存数量
		StockItem item = itemMapper.selectQuantityAndBalance(si.getId());
		this.updateMaterialForDeleteItem(stockType, item);
		itemMapper.deleteByPrimaryKey(si.getId());
	}
	
	/**
	 * 删除stock item前，重新计算物料数量
	 * @param stockType
	 * @param stock item
	 */
	private void updateMaterialForDeleteItem(Integer stockType, StockItem item){
		if (item.getTotalQuantity() != null && item.getBalance() != null && item.getQuantity() != null){
			Material m = new Material();
			m.setId(item.getMaterialId());
			switch(stockType){  // 1,2,3 入库: 删除已入库的物料，要减去入库数量，恢复到入库前的状态
			case 1: case 2: case 3:
				m.setTotalQuantity(item.getTotalQuantity().subtract(item.getQuantity())); // 恢复物料总数量
				m.setBalance(item.getBalance().subtract(item.getQuantity()));// 恢复物料库存数量
				break;
			case 4: case 5: case 6: //  4,5,6 出库: 删除已出库的物料，要加上出库数量，恢复到出库前的状态
				m.setTotalQuantity(item.getTotalQuantity().add(item.getQuantity())); // 恢复物料总数量
				m.setBalance(item.getBalance().add(item.getQuantity()));// 恢复物料库存数量
				break;
			}
			materialMapper.updateByPrimaryKeySelective(m);
		}
	}

	public void insertItem(StockItem item, Integer stockType)
	{
		// 添加stock item, 根据stock type更新库存数量
		Material m = materialMapper.selectByPrimaryKey(item.getMaterialId());
		Date now = new Date();
		if (m.getTotalQuantity() != null && m.getBalance() != null && item.getQuantity() != null){
			switch(stockType){  // 1,2,3 入库: 删除已入库的物料，要减去入库数量，恢复到入库前的状态
			case 1: case 2: case 3:
				m.setTotalQuantity(m.getTotalQuantity().subtract(item.getQuantity())); // 恢复物料总数量
				m.setBalance(m.getBalance().subtract(item.getQuantity()));// 恢复物料库存数量
				break;
			case 4: case 5: case 6: //  4,5,6 出库: 删除已出库的物料，要加上出库数量，恢复到出库前的状态
				m.setTotalQuantity(m.getTotalQuantity().add(item.getQuantity())); // 恢复物料总数量
				m.setBalance(m.getBalance().add(item.getQuantity()));// 恢复物料库存数量
				break;
			}
			Material newM = new Material();
			newM.setId(m.getId());
			newM.setBalance(m.getBalance());
			newM.setTotalQuantity(m.getTotalQuantity());
			newM.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
			materialMapper.updateByPrimaryKeySelective(newM);
		}
		item.setUnitId(m.getUnitId());
		item.setCreateTime(Constant.DATETIME_FORMATTER.format(now));
		item.setUpdateTime(item.getCreateTime());
		itemMapper.insert(item);
	}

	public void updateItem(StockItem si, Integer stockType)
	{
		// 更新stock item, 根据stock type和修改前后相差数量更新库存数量
		// Material m = materialMapper.selectByPrimaryKey(item.getMaterialId());
		StockItem item = itemMapper.selectQuantityAndBalance(si.getId());
		Date now = new Date();
		if (item.getTotalQuantity() != null && item.getBalance() != null && si.getQuantity() != null){
			Material m = new Material();
			m.setId(item.getMaterialId());
			
			BigDecimal diffQuantity = si.getQuantity().subtract(item.getQuantity()); // 修改前后相关数量
			
			switch(stockType){  // 1,2,3 入库
			case 1: case 2: case 3:
				m.setTotalQuantity(m.getTotalQuantity().add(diffQuantity)); // 物料总数量=原总数+相关数量
				m.setBalance(m.getBalance().add(diffQuantity));// 物料库存数量=原库存+相关数量
				break;
			case 4: case 5: case 6: //  4,5,6 出库
				m.setTotalQuantity(m.getTotalQuantity().subtract(diffQuantity)); // 重算物料总数量
				m.setBalance(m.getBalance().subtract(diffQuantity));// 重算物料库存数量
				break;
			}
			Material newM = new Material();
			newM.setId(m.getId());
			newM.setBalance(m.getBalance());
			newM.setTotalQuantity(m.getTotalQuantity());
			newM.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
			materialMapper.updateByPrimaryKeySelective(newM);
		}
		item.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
		itemMapper.updateByPrimaryKeySelective(si);
	}

	public List<StockItem> readItemFromSheet(HSSFSheet sheet) throws Exception
	{
		List<StockItem> items = new ArrayList<StockItem>(0);
		int index = 1; // 时间行
		try
		{
			if (sheet != null){
				String timeStr = sheet.getRow(index).getCell(0).getStringCellValue(); // 时间行
				if (timeStr == null || "".equals(timeStr)){
					return items;
				}
				String yearAndMonth = timeStr.substring(0, timeStr.indexOf("月")).replace("年", "-"); // 变成格式：yyyy-MM
				
				index = 3; // 数据内容从第4行(索引为3)开始
				while(sheet.getRow(index) != null){
					Row row = sheet.getRow(index);
					StockItem item = new StockItem();
					int cellnum = 0;
					
					String dayStr = row.getCell(cellnum++).getStringCellValue();
					if (dayStr == null || "".equals(dayStr)){  // 第一个cell没有内容，认为到此结束
						break;
					}
					item.setStockDate(yearAndMonth + "-" + dayStr.replaceAll("号", ""));  // 变成格式：yyyy-MM-dd
					item.setStockNo(row.getCell(cellnum++).getStringCellValue()); // 单号
					
					String nameStr = row.getCell(cellnum++).getStringCellValue();
					if (nameStr.lastIndexOf(" ") == -1){
						item.setMaterialName(nameStr);  // 物料名
					}else{
						nameStr = nameStr.replaceFirst(" ", "\\|");
						item.setMaterialName(nameStr.split("\\|")[0]);  // 物料名
						item.setRemark(nameStr.split("\\|")[1]);  // 物料名后面以空格隔开的备注
					}
					
					item.setUnitName(row.getCell(cellnum++).getStringCellValue());  //单位
					
					double quantity = row.getCell(cellnum++).getNumericCellValue(); // 数量
					item.setQuantity(new BigDecimal(quantity));
					
					double price = row.getCell(cellnum++).getNumericCellValue(); // 单价
					item.setUnitPrice(new BigDecimal(price));
					
					cellnum++; // 路过金额列
					item.setStockRemark(row.getCell(cellnum++).getStringCellValue()); // 备注列
					
					items.add(item);
					index++;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("分析表单 \""+ sheet.getSheetName() + "\"的第" + (++index) + "行时出错！");
		}
		
		return items;
	}

	/**
	 * 保存从excel导入的数据
	 * @param list
	 */
	public void saveImportItems(List<StockItem> list)
	{
		// TODO 
		for(StockItem item : list){
			
		}
	}
	
}
