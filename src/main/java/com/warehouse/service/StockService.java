package com.warehouse.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.warehouse.common.BaseMapper;
import com.warehouse.common.BaseService;
import com.warehouse.mapper.DictMapper;
import com.warehouse.mapper.MaterialMapper;
import com.warehouse.mapper.StockItemMapper;
import com.warehouse.mapper.StockMapper;
import com.warehouse.model.Dict;
import com.warehouse.model.Material;
import com.warehouse.model.Stock;
import com.warehouse.model.StockItem;
import com.warehouse.model.StockSearch;
import com.warehouse.util.Constant;
import com.warehouse.util.Entry;

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
	@Resource
	private DictMapper dictMapper;
	@Override
	public BaseMapper<Stock> getMapper()
	{
		return stockMapper;
	}

	public void save(Stock stock) throws Exception
	{
		try
		{
			stockMapper.insert(stock);
		}
		catch (Exception e)
		{
			if (e.getMessage().contains("UNIQUE constraint failed: stock.stock_no")){
				throw new Exception("单号冲突，系统中已存在单号'"+stock.getStockNo()+"'！");
			}
		}
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
		Stock s = stockMapper.selectByPrimaryKey(id);
		List<StockItem> items = itemMapper.findItemsByStockIdForDeleteStock((Integer)id);
		if (items != null && !items.isEmpty()){
			for(StockItem i : items){
				this.updateMaterialForDeleteItem(s.getStockType(), i);
			}
		}
		itemMapper.deleteByStockId(id);
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
				//m.setTotalQuantity(item.getTotalQuantity().add(item.getQuantity())); // 不需要增加物料总数量，因为出库时总数量没有减少
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
			switch(stockType){  // 1,2,3 入库: 增加数量
			case 1: case 2: case 3:
				m.setTotalQuantity(m.getTotalQuantity().add(item.getQuantity())); // 总数量
				m.setBalance(m.getBalance().add(item.getQuantity()));// 库存数量
				break;
			case 4: case 5: case 6: //  4,5,6 出库: 删除已出库的物料，要加上出库数量，恢复到出库前的状态
				// m.setTotalQuantity(m.getTotalQuantity().subaddtract(item.getQuantity())); // 总数量不变
				m.setBalance(m.getBalance().subtract(item.getQuantity()));// 库存数量
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
				m.setTotalQuantity(item.getTotalQuantity().add(diffQuantity)); // 物料总数量=原总数+相关数量
				m.setBalance(item.getBalance().add(diffQuantity));// 物料库存数量=原库存+相关数量
				break;
			case 4: case 5: case 6: //  4,5,6 出库
				m.setTotalQuantity(item.getTotalQuantity().subtract(diffQuantity)); // 重算物料总数量
				m.setBalance(item.getBalance().subtract(diffQuantity));// 重算物料库存数量
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

	public List<StockItem> readItemFromSheet(HSSFSheet sheet, Map<String, Material> mMap, Map<String, Dict> uMap) throws Exception
	{
		List<StockItem> items = new ArrayList<StockItem>(0);
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
		Date now = new Date();
		int rowIndex = 1; // 时间行
		
		try
		{
			if (sheet != null){
				Row row = sheet.getRow(rowIndex);
				if (row == null){
					return items;
				}
				String timeStr = row.getCell(0).getStringCellValue(); // 时间行
				if (timeStr == null || "".equals(timeStr)){
					return items;
				}
				String yearAndMonth = timeStr.substring(0, timeStr.indexOf("月")).replace("年", "-"); // 变成格式：yyyy-MM
				
				rowIndex = 3; // 数据内容从第4行(索引为3)开始
				while(sheet.getRow(rowIndex) != null){
					row = sheet.getRow(rowIndex);
					StockItem item = new StockItem();
					int cellIndex = 0;
					
					String dayStr = row.getCell(cellIndex++).getStringCellValue();
					if (dayStr == null || "".equals(dayStr)){  // 第一个cell没有内容，认为到此结束
						//throw new Exception("工作表 \""+ sheet.getSheetName() + "\"的第" + (++rowIndex) + "行没有日期！");
						break;
					}
					
					item.setStockDate(yearAndMonth + "-" + dayStr.replaceAll("号", ""));  // 变成格式：yyyy-MM-dd
					
					item.setStockNo(row.getCell(cellIndex++).getStringCellValue()); // 单号
					if (item.getStockNo() == null || "".equals(item.getStockNo())){
						//throw new Exception("工作表 \""+ sheet.getSheetName() + "\"的第" + (++rowIndex) + "行没有单号！");
						item.setStockNo(item.getStockDate().replaceAll("-", "") + timeFormat.format(now));
					}
					
					item.setMaterialName(row.getCell(cellIndex++).getStringCellValue());  // 物料名
					if (item.getMaterialName() == null || "".equals(item.getMaterialName())){
						throw new Exception("工作表 \""+ sheet.getSheetName() + "\"的第" + (++rowIndex) + "行没有物料名！");
					}
					item.setSize(row.getCell(cellIndex++).getStringCellValue()); // 规格
					item.setRemark(row.getCell(cellIndex++).getStringCellValue()); // 物料说明
					
					item.setUnitName(row.getCell(cellIndex++).getStringCellValue());  //单位
					if (item.getUnitName() == null || "".equals(item.getUnitName())){
						throw new Exception("工作表 \""+ sheet.getSheetName() + "\"的第" + (++rowIndex) + "行没有单位！");
					}
					
					double quantity = row.getCell(cellIndex++).getNumericCellValue(); // 数量
					item.setQuantity(new BigDecimal(quantity));
					
					double price = row.getCell(cellIndex++).getNumericCellValue(); // 单价
					item.setUnitPrice(new BigDecimal(price));
					
					cellIndex++; // 跳过金额列
					item.setStockRemark(row.getCell(cellIndex++).getStringCellValue()); // 备注列
					
					if (uMap.get(item.getUnitName()) == null){// 不存在
						// insert new unit
						Dict d = new Dict();
						d.setDisabled(0);
						d.setName(item.getUnitName());
						d.setType(Constant.DICT_TYPE_UNIT);
						d.setCreateTime(Constant.DATETIME_FORMATTER.format(now));
						d.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
						dictMapper.insert(d);
						
						item.setUnitId(d.getId());
						uMap.put(d.getName(), d);
					}else{
						item.setUnitId(uMap.get(item.getUnitName()).getId());
					}
					
					// 检查物料是否存在
					String name_size = StringUtils.isEmpty(item.getSize()) ? item.getMaterialName():(item.getMaterialName() + "-" + item.getSize());
					Material m = mMap.get(name_size);
					if (m == null){ // 不存在
						// insert new material
						Material newM = new Material();
						newM.setName(item.getMaterialName());
						newM.setSize(StringUtils.isEmpty(item.getSize()) ? null : item.getSize());
						newM.setUnitId(uMap.get(item.getUnitName()).getId());
						newM.setDisabled(0);
						newM.setAvgUnitPrice(new BigDecimal(0));
						newM.setUnitPrice(new BigDecimal(0));
						newM.setTotalQuantity(new BigDecimal(0));
						newM.setBalance(new BigDecimal(0));
						newM.setCreateTime(Constant.DATETIME_FORMATTER.format(now));
						newM.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
						
						materialMapper.insert(newM);
						item.setMaterialId(newM.getId());
						mMap.put(newM.getName(), newM);
					}else{
						item.setMaterialId(m.getId());
						item.setUnitId((Integer)m.getUnitId());
						
						if(!item.getUnitId().equals(uMap.get(item.getUnitName()).getId())){
							throw new Exception("物料'"+ item.getMaterialName() + "'" + "的单位应为'"+ mMap.get(item.getMaterialName()).getUnitName()+"'");
						}
					}
					
					items.add(item);
					rowIndex++;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("分析工作表 \""+ sheet.getSheetName() + "\"的第" + (++rowIndex) + "行时出错！");
		}
		
		return items;
	}

	/**
	 * 保存从excel导入的数据
	 * @param list
	 */
	public void saveImportItems(List<StockItem> list, Integer stockType) throws Exception
	{
		// TODO 
		Date now = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Map<String, Stock> stockMap = new HashMap<String, Stock>();
		for(StockItem item : list){
			Stock s = stockMap.get(item.getStockNo());
			if (s == null){
				s = new Stock();
				
				// 如果stock No. 为空，则用日期作为单号
				if(StringUtils.isEmpty(item.getStockNo())){
					item.setStockNo(item.getStockDate().replaceAll("-","") + timeFormat.format(now).replaceAll(":", ""));
				}else{
					s.setStockNo(item.getStockNo());
				}
				
				// 月份为1位数的转为两位数
				s.setStockTime(Constant.DATE_FORMATTER.format(Constant.DATE_FORMATTER.parse(item.getStockDate())) + " " + timeFormat.format(now));
				s.setStockType(stockType);
				
				switch(stockType){
					case 1: s.setTypeName("新购入库");
							s.setSource(item.getStockRemark());
							s.setTarget("仓库");  // 客户、目的地
							break;
					case 2: s.setTypeName("归还入库");
							s.setSource(item.getStockRemark());
							s.setTarget("仓库");  // 客户、目的地
							break;
					case 3: s.setTypeName("退货入库");
							s.setSource(item.getStockRemark());
							s.setTarget("仓库");  // 客户、目的地
							break;
					case 4: s.setTypeName("生产出库");
							s.setSource("仓库");
							s.setTarget(item.getStockRemark());  // 客户、目的地
							break;
					case 5: s.setTypeName("借用出库");
							s.setSource("仓库");
							s.setTarget(item.getStockRemark());  // 客户、目的地
							break;
					case 6: s.setTypeName("销售出库");
							s.setSource("仓库");
							s.setTarget(item.getStockRemark());  // 客户、目的地
							break;
				}
				
				s.setCreateTime(Constant.DATETIME_FORMATTER.format(now));
				s.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
				
				item.setCreateTime(Constant.DATETIME_FORMATTER.format(now));
				item.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
				s.addItem(item);
				
				stockMap.put(s.getStockNo(), s);
			}
			else {
				s.addItem(item);
			}
		}
		
		// save
		Iterator<Stock> it = stockMap.values().iterator();
		while(it.hasNext()){
			Stock s = it.next();
			this.save(s);
		}
	}
	
}
