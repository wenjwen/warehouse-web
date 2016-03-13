/**
 * 
 */
package com.warehouse.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.warehouse.common.PageParamModel;
import com.warehouse.model.Category;
import com.warehouse.model.Dict;
import com.warehouse.model.Material;
import com.warehouse.model.StockDetail;
import com.warehouse.service.CategoryService;
import com.warehouse.service.DictService;
import com.warehouse.service.MaterialService;
import com.warehouse.service.StockDetailService;
import com.warehouse.util.AjaxResult;
import com.warehouse.util.Constant;

/**
 * @author Jerry
 * 处理出入库记录
 */
@Controller
@RequestMapping("/stockDetail")
public class StockDetailController
{
	private Logger logger = Logger.getLogger(StockDetailController.class);
	@Resource
	private StockDetailService stockDetailService;
	@Resource
	private MaterialService materialService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private DictService dictService;
	
	
	/**
	 * 列表 分页查询
	 * @param PageParamModel
	 * @param StockDetail
	 * @return PaginQueryResult
	 */
	@RequestMapping(value="/list.json")
	@ResponseBody
	public Object findStock(PageParamModel<StockDetail> ppm, StockDetail s){
		return stockDetailService.paginQuery(s, ppm.getPage(), ppm.getRows());
	}
	
	/**
	 * 直接在datagrid中添加的数据
	 * @param StockDetail
	 * @return AjaxResult
	 */
	@RequestMapping(value="/saveStock")
	@ResponseBody
	public Object saveStock(StockDetail s){
		AjaxResult result = new AjaxResult();
		try
		{
			if (s.getId() != null && s.getId() > 0){ // update
				s.setUpdateTime(Constant.DATETIME_FORMATTER.format(new Date()));
				stockDetailService.update(s);
			} else {  // insert
				if (s.getTypeName() == null || "".equals(s.getTypeName())){ 
					s.setTypeName(Constant.getStockTypeName(s.getStockType()));
				}
				// 生成单号
				if (s.getStockNo() == null || "".equals(s.getStockNo())){
					s.setStockNo(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				}
				stockDetailService.insert(s);
			}
		}
		catch (Exception e)
		{
			result.setIsError(true);
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 根据ID删除记录
	 * @param StockDetail
	 * @return
	 */
	@RequestMapping(value="/deleteStock")
	@ResponseBody
	public Object deleteStock(StockDetail s){
		AjaxResult result = new AjaxResult();
		try
		{
			stockDetailService.deleteById(s.getId());
		}
		catch (Exception e)
		{
			result.setIsError(true);
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * uploadExcel
	 * @param stockType
	 * @param excel
	 * @return
	 */
	@RequestMapping(value="/uploadExcel")
	@ResponseBody
	public Object uploadExcel(Integer stockType, @RequestParam MultipartFile excel){
		AjaxResult result = new AjaxResult();
		try
		{
			if (excel.getOriginalFilename().endsWith(".xls")){
				result.setMsg(excel.getOriginalFilename());
				// 开始读取excel数据
				HSSFWorkbook wb = new HSSFWorkbook(excel.getInputStream());
				int sheetNum = wb.getNumberOfSheets();
				if (sheetNum > 0){
					List<Material> mList = materialService.findFroImport();
					List<Dict> uList = dictService.findByType(1);
					List<Category> cList = categoryService.findAll();
					Map<String, Material> mMap = new HashMap<String, Material>(0);
					Map<String, Dict> uMap = new HashMap<String, Dict>(0);
					Map<String, Category> cMap = new HashMap<String, Category>(0);
				
					if (mList != null && mList.size() > 0){
						for(Material m : mList){
							// mMap.put(StringUtils.isEmpty(m.getSize()) ? m.getName():(m.getName() + "-" + m.getSize()), m);
							// name-size-categoryId
							mMap.put((m.getName() 
									+ (StringUtils.isEmpty(m.getSize())?"" : ("-" + m.getSize())) 
									+ (m.getCategoryId()!=null&&m.getCategoryId()>0?("-"+m.getCategoryId()):"")), m);
						}
					}
					if (uList != null && uList.size() > 0){
						for(Dict u : uList){
							uMap.put(u.getName(), u);
						}
					}
					if (cList != null && cList.size() > 0){
						for(Category c : cList){
							cMap.put(c.getName(), c);
						}
					}
					
					List<StockDetail> items = new ArrayList<StockDetail>(0);
					for (int i = 0; i < sheetNum; i++){
						items.addAll(stockDetailService.readItemFromSheet(wb.getSheetAt(i), mMap, uMap, cMap));
					}
					
					//save items
					stockDetailService.saveImportItems(items, stockType);
					
					Map<String, Object> obj = new HashMap<String, Object>(4);
					obj.put("items", items);
					// 更新material, unit, category
					obj.put("unitEntry", dictService.findByType(1)); // 1 单位
					obj.put("categoryEntry", categoryService.findAllEntry());
					obj.put("materialEntry", materialService.findAllEntry());
					
					result.setObj(obj);
					
				}
				else {
					result.setIsError(true);
					result.setMsg("无法读取xls文件中表单(sheet)！");
				}
				
			}else{
				result.setIsError(true);
				result.setMsg("目前只支持xls文件解析，请使用xls文件！");
			}
		    
		}
		catch (Exception e)
		{
			result.setIsError(true);
			result.setMsg(e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
}
