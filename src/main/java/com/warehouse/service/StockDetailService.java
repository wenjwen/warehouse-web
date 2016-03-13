package com.warehouse.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.warehouse.common.BaseMapper;
import com.warehouse.common.BaseService;
import com.warehouse.mapper.CategoryMapper;
import com.warehouse.mapper.DictMapper;
import com.warehouse.mapper.MaterialMapper;
import com.warehouse.mapper.StockDetailMapper;
import com.warehouse.model.Category;
import com.warehouse.model.Dict;
import com.warehouse.model.Material;
import com.warehouse.model.StockDetail;
import com.warehouse.util.Constant;

@Service
public class StockDetailService extends BaseService<StockDetail>
{
	private Logger logger = Logger.getLogger(StockDetailService.class);
	@Resource
	private StockDetailMapper stockDetailMapper;
	@Resource
	private MaterialMapper materialMapper;
	@Resource
	private DictMapper dictMapper;
	@Resource
	private CategoryMapper categoryMapper;
	
	@Override
	public BaseMapper<StockDetail> getMapper()
	{
		return stockDetailMapper;
	}
	
	/**
	 * 删除出入库记录
	 * 并恢复物料原来数量
	 * @param ID
	 */
	@Override
	public int deleteById(Object id)
	{
		StockDetail s = stockDetailMapper.selectByPrimaryKey(id);
		// 修改数量
		if(s != null && s.getQuantity().doubleValue() > 0)
		{
			Date now = new Date();
			Material m = materialMapper.selectByPrimaryKey(s.getMaterialId());
			if (m.getTotalQuantity() == null) m.setTotalQuantity(new BigDecimal(0));
			if (m.getBalance() == null) m.setBalance(new BigDecimal(0));
			
			switch(s.getStockType()){  // 1,2,3 入库: 删除已入库的物料，要减去入库数量，恢复到入库前的状态
			case 1: case 2: case 3:
				m.setTotalQuantity(m.getTotalQuantity().subtract(s.getQuantity())); // 恢复物料总数量
				m.setBalance(m.getBalance().subtract(s.getQuantity()));// 恢复物料库存数量
				break;
			case 4: case 5: case 6: //  4,5,6 出库: 删除已出库的物料，要加上出库数量，恢复到出库前的状态
				//m.setTotalQuantity(item.getTotalQuantity().add(item.getQuantity())); // 不需要增加物料总数量，因为出库时总数量没有减少
				m.setBalance(m.getBalance().add(s.getQuantity()));// 恢复物料库存数量
				break;
			}
			
			m.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
			materialMapper.updateByPrimaryKeySelective(m);
		}
		return super.deleteById(id);
	}

	/**
	 * 新增记录，并修改相应物料数量
	 */
	@Override
	public int insert(StockDetail s) throws Exception
	{
		Material m = materialMapper.selectByPrimaryKey(s.getMaterialId());
		if (m.getTotalQuantity() == null) m.setTotalQuantity(new BigDecimal(0));
		if (m.getBalance() == null) m.setBalance(new BigDecimal(0));
		
		Date now = new Date();
		if (s.getQuantity() != null && s.getQuantity().doubleValue() > 0){
			switch(s.getStockType()){  // 1,2,3 入库: 增加数量
			case 1: case 2: case 3:
				m.setTotalQuantity(m.getTotalQuantity().add(s.getQuantity())); // 总数量
				m.setBalance(m.getBalance().add(s.getQuantity()));// 库存数量
				break;
			case 4: case 5: case 6: //  4,5,6 出库: 删除已出库的物料，要加上出库数量，恢复到出库前的状态
				// m.setTotalQuantity(m.getTotalQuantity().subaddtract(item.getQuantity())); // 总数量不变
				m.setBalance(m.getBalance().subtract(s.getQuantity()));// 库存数量
				break;
			}
			
			m.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
			materialMapper.updateByPrimaryKeySelective(m);
		}
		
		return super.insert(s);
	}

	/**
	 * 更新记录，并修改相应物料数量
	 */
	@Override
	public int update(StockDetail s)
	{
		// 更新stockdetail, 根据stock type和修改前后相差数量更新库存数量
		StockDetail old = stockDetailMapper.selectByPrimaryKey(s.getId());
		if (old.getQuantity() == null) old.setQuantity(new BigDecimal(0));
		if (old.getUnitPrice() == null) old.setUnitPrice(new BigDecimal(0));
		
		Material m = materialMapper.selectByPrimaryKey(old.getMaterialId());
		if (m.getTotalQuantity() == null) m.setTotalQuantity(new BigDecimal(0));
		if (m.getBalance() == null) m.setBalance(new BigDecimal(0));
		
		Date now = new Date();
		if (s.getQuantity() != null && !s.getQuantity().equals(old.getQuantity())){
			BigDecimal diffQuantity = old.getQuantity().subtract(s.getQuantity()); // 修改前后相差数量
			
			switch(s.getStockType()){  // 1,2,3 入库
			case 1: case 2: case 3:
				m.setTotalQuantity(m.getTotalQuantity().add(diffQuantity)); // 物料总数量=原总数+相差数量
				m.setBalance(m.getBalance().add(diffQuantity));// 物料库存数量=原库存+相差数量
				break;
			case 4: case 5: case 6: //  4,5,6 出库
				m.setTotalQuantity(m.getTotalQuantity().subtract(diffQuantity)); // 重算物料总数量
				m.setBalance(m.getBalance().subtract(diffQuantity));// 重算物料库存数量
				break;
			}
			
			m.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
			materialMapper.updateByPrimaryKeySelective(m);
		}
		
		return super.update(s);
	}
	
	public List<StockDetail> readItemFromSheet(HSSFSheet sheet, Map<String, Material> mMap, Map<String, Dict> uMap, Map<String, Category> cMap) throws Exception
	{
		List<StockDetail> items = new ArrayList<StockDetail>(0);
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
		Date now = new Date();
		int rowIndex = 1; // 时间行
		
		try
		{
			if (sheet != null){
				if (sheet.getSheetName() != null && !"".equals(sheet.getSheetName())){
					if (cMap.get(sheet.getSheetName()) == null){
						// 创建分类
						Category newC = new Category();
						newC.setCreateTime(Constant.DATETIME_FORMATTER.format(now));
						newC.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
						newC.setCreateUser(1);
						newC.setUpdateUser(1);
						newC.setDisabled(0);
						newC.setName(sheet.getSheetName());
						newC.setRemark("导入时创建");
						categoryMapper.insert(newC);
						cMap.put(newC.getName(), newC);
					}
						
				}
				Row row = sheet.getRow(rowIndex);
				if (row == null){
					return items;
				}
				// 设置cell type，以免读取时出错
				if (row.getCell(0) != null){
					 row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				}
				String timeStr = row.getCell(0).getStringCellValue(); // 时间行
				if (timeStr == null || "".equals(timeStr)){
					return items;
				}
				String yearAndMonth = timeStr.substring(0, timeStr.indexOf("月")).replace("年", "-"); // 变成格式：yyyy-MM
				
				rowIndex = 3; // 数据内容从第4行(索引为3)开始
				while(sheet.getRow(rowIndex) != null){
					logger.debug("正在分析工作表 \""+ sheet.getSheetName() + "\"的第" + (rowIndex) + "行");
					
					row = sheet.getRow(rowIndex);
					StockDetail item = new StockDetail();
					int cellIndex = 0;
					
					// 设置cell type，以免读取时出错
					if (row.getCell(cellIndex) != null){
						 row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
					}
					String dayStr = row.getCell(cellIndex++).getStringCellValue();
					if (dayStr == null || "".equals(dayStr)){  // 第一个cell没有内容，认为到此结束
						//throw new Exception("工作表 \""+ sheet.getSheetName() + "\"的第" + (++rowIndex) + "行没有日期！");
						break;
					}
					
					item.setStockTime(yearAndMonth + "-" + dayStr.replaceAll("号", ""));  // 变成格式：yyyy-MM-dd
					
					// 设置cell type，以免读取时出错
					if (row.getCell(cellIndex) != null){
						 row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
					}
					item.setStockNo(row.getCell(cellIndex++).getStringCellValue()); // 单号
					if (item.getStockNo() == null || "".equals(item.getStockNo())){
						//throw new Exception("工作表 \""+ sheet.getSheetName() + "\"的第" + (++rowIndex) + "行没有单号！");
						item.setStockNo(item.getStockTime().replaceAll("-", "") + timeFormat.format(now));
					}
					
					// 设置cell type，以免读取时出错
					if (row.getCell(cellIndex) != null){
						 row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
					}
					item.setMaterialName(row.getCell(cellIndex++).getStringCellValue());  // 物料名
					if (item.getMaterialName() != null && item.getMaterialName().trim().contains("运费")){
						rowIndex++;
						continue;
					}
					if (item.getMaterialName() == null || "".equals(item.getMaterialName())){
						throw new Exception("工作表 \""+ sheet.getSheetName() + "\"的第" + (rowIndex+1) + "行没有物料名！");
					}
					
					// 设置cell type，以免读取时出错
					if (row.getCell(cellIndex) != null){
						 row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
					}
					item.setSize(row.getCell(cellIndex++).getStringCellValue()); // 规格
					
					// 设置cell type，以免读取时出错
					if (row.getCell(cellIndex) != null){
						 row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
					}
					item.setRemark(row.getCell(cellIndex++).getStringCellValue()); // 物料说明
					
					// 设置cell type，以免读取时出错
					if (row.getCell(cellIndex) != null){
						 row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
					}
					item.setUnitName(row.getCell(cellIndex++).getStringCellValue());  //单位
					if (item.getUnitName() == null || "".equals(item.getUnitName())){
						throw new Exception("工作表 \""+ sheet.getSheetName() + "\"的第" + (rowIndex+1) + "行没有单位！");
					}
					
					// 设置cell type，以免读取时出错
					if (row.getCell(cellIndex) != null){
						 row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_NUMERIC);
					}
					double quantity = row.getCell(cellIndex++).getNumericCellValue(); // 数量
					item.setQuantity(new BigDecimal(quantity));
					
					// 设置cell type，以免读取时出错
					if (row.getCell(cellIndex) != null){
						 row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_NUMERIC);
					}
					double price = row.getCell(cellIndex++).getNumericCellValue(); // 单价
					item.setUnitPrice(new BigDecimal(price));
					
					cellIndex++; // 跳过金额列
					
					// 设置cell type，以免读取时出错
					if (row.getCell(cellIndex) != null){
						 row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
					}
					item.setRemark(row.getCell(cellIndex++).getStringCellValue()); // 备注列
					
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
					/*String name_size_categoryId = item.getMaterialName() 
									+ (StringUtils.isEmpty(item.getSize())?"":("-" + item.getSize()))
									+ (cMap.get(sheet.getSheetName())==null?"":("-" + cMap.get(sheet.getSheetName()).getId()));*/
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
						// 设置分类
						newM.setCategoryId(cMap.get(sheet.getSheetName()).getId());
						
						materialMapper.insert(newM);
						newM.setOrderNo(newM.getId());
						materialMapper.updateByPrimaryKeySelective(newM);
						item.setMaterialId(newM.getId());
						mMap.put(StringUtils.isEmpty(newM.getSize()) ? newM.getName():(newM.getName() + "-" + newM.getSize()), newM);
						/*mMap.put((newM.getName() 
								+ (StringUtils.isEmpty(newM.getSize())?"" : ("-" + newM.getSize())) 
								+ (newM.getCategoryId()!=null&&newM.getCategoryId()>0?("-"+newM.getCategoryId()):"")), newM);*/
					}else{
						item.setMaterialId(m.getId());
						item.setUnitId((Integer)m.getUnitId());
						
						// 物料名、规格一样，但单位不同
						if(!item.getUnitId().equals(uMap.get(item.getUnitName()).getId())){
							throw new Exception("系统中已存在物料'"+ item.getMaterialName() + "'（规格为'"+(m.getSize() == null?"":m.getSize())+"',单位为'"+ m.getUnitName() +"'），请修改该行的物料名或规格再导入！");
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
			throw new Exception("分析工作表 \""+ sheet.getSheetName() + "\"的第" + (rowIndex+1) + "行时出错！" + e.getMessage());
		}
		
		return items;
	}
	
	
	/**
	 * 保存从excel导入的数据
	 * @param list
	 */
	public void saveImportItems(List<StockDetail> list, Integer stockType) throws Exception
	{
		Date now = new Date();
		// 处理一下stock detail
		for(StockDetail item : list){
			
			item.setCreateTime(Constant.DATETIME_FORMATTER.format(now));
			item.setUpdateTime(Constant.DATETIME_FORMATTER.format(now));
			
			// 月份为1位数的转为两位数
			item.setStockTime(Constant.DATE_FORMATTER.format(Constant.DATE_FORMATTER.parse(item.getStockTime())));
			item.setStockType(stockType);
			item.setTypeName(Constant.getStockTypeName(stockType));
			
			switch(stockType){
				case 1: case 2: case 3: 
						item.setSource(item.getRemark());
						item.setTarget("仓库");  // 客户、目的地
						break;
				case 4: case 5: case 6:  
						item.setSource("仓库");
						item.setTarget(item.getRemark());  // 客户、目的地
						break;
			}
			
		}
		
		// save stock detail items
		stockDetailMapper.batchInsert(list);
		
		// update material's balance
        List<Material> mList = materialMapper.selectBalanceByIds(list);
        Map<Integer, StockDetail> mQualityMap = new HashMap<Integer, StockDetail>(list.size());
        
        for(StockDetail item : list){
            mQualityMap.put(item.getMaterialId(), item);
        }

        for(Material m : mList){
        	StockDetail item = mQualityMap.get(m.getId());
        	BigDecimal stockAccount = item.getQuantity();
            if (stockAccount == null || stockAccount.compareTo(new BigDecimal(0)) == 0) continue;
            
            switch(stockType){
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
		
	}

}
