package com.warehouse.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.warehouse.common.PageParamModel;
import com.warehouse.model.Dict;
import com.warehouse.model.Stock;
import com.warehouse.model.StockItem;
import com.warehouse.service.DictService;
import com.warehouse.service.MaterialService;
import com.warehouse.service.StockService;
import com.warehouse.util.AjaxResult;
import com.warehouse.util.Constant;
import com.warehouse.util.Entry;

@Controller
public class StockController
{
	private Logger logger = Logger.getLogger(StockController.class);
	
	@Resource
	private StockService stockService;
	@Resource
	private MaterialService materialService;
	@Resource
	private DictService dictService;
	
	/**
	 * 打开出入库管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="stockList")
	public Object toStockListPage(ModelMap model){
		return "/stock/list";
	}
	
	/**
	 * 打开出入库详细页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="stock/items")
	public Object toStockItemsPage(ModelMap model, Integer stockId, Integer stockType){
		model.addAttribute("stockId", stockId);
		model.addAttribute("stockType", stockType);
		return "/stock/items";
	}
	
	/**
	 * 打开导入stock item页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="stock/itemsImport")
	public Object toStockItemsImportPage(ModelMap model, Integer stockType){
		model.addAttribute("importStockType", stockType);
		return "/stock/itemsImport";
	}
	
	@RequestMapping(value="stock/list.json")
	@ResponseBody
	public Object findStock(PageParamModel<Stock> ppm, Stock s){
		// 分页查询
		return stockService.paginQuery(s, ppm.getPage(), ppm.getRows());
	}
	
	@RequestMapping(value="stock/items.json")
	@ResponseBody
	public Object findStockItems(PageParamModel<Stock> ppm, Integer stockId){
		return stockService.findItemsByStockId(stockId);
	}
	
	/**
	 * 直接在datagrid中添加的stock
	 * @param s
	 * @return
	 */
	@RequestMapping(value="saveStock")
	@ResponseBody
	public Object saveStock(Stock s){
		AjaxResult result = new AjaxResult();
		try
		{
			if (s.getId() != null && s.getId() > 0){ // update
				s.setUpdateTime(Constant.DATETIME_FORMATTER.format(new Date()));
				stockService.update(s);
			} else {  // insert
				if (s.getTypeName() == null || "".equals(s.getTypeName())){ 
					s.setTypeName(this.getStockTypeName(s.getStockType()));
				}
				// 生成单号
				if (s.getStockNo() == null || "".equals(s.getStockNo())){
					s.setStockNo(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				}
				stockService.insert(s);
			}
		}
		catch (Exception e)
		{
			result.setIsError(true);
			e.printStackTrace();
		}
		
		return result;
	}
	
	private String getStockTypeName(Integer stockType)
	{
		switch(stockType){
		case 1: return "新购入库";
		case 2: return "归还入库";
		case 3: return "退货入库";
		case 4: return "生产出库";
		case 5: return "借用出库";
		case 6: return "销售出库";
		default: return null ;
		}
	}

	/**
	 * 直接在datagrid中添加的stock item
	 * @param s
	 * @return
	 */
	@RequestMapping(value="saveStockItem")
	@ResponseBody
	public Object saveStockItem(HttpServletRequest request, StockItem item){
		AjaxResult result = new AjaxResult();
		try
		{
			item.setStockId(Integer.parseInt(request.getParameter("stockId")));
			
			if (item.getId() != null && item.getId() > 0){ // update
				item.setUpdateTime(Constant.DATETIME_FORMATTER.format(new Date()));
				stockService.updateItem(item, Integer.parseInt(request.getParameter("stockType")));
			} else {  // insert
				item.setCreateTime(Constant.DATETIME_FORMATTER.format(new Date()));
				stockService.insertItem(item, Integer.parseInt(request.getParameter("stockType")));
			}
		}
		catch (Exception e)
		{
			result.setIsError(true);
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	@RequestMapping(value="deleteStock")
	@ResponseBody
	public Object deleteStock(Stock s){
		AjaxResult result = new AjaxResult();
		try
		{
			stockService.deleteById(s.getId());
		}
		catch (Exception e)
		{
			result.setIsError(true);
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value="deleteStockItem")
	@ResponseBody
	public Object deleteStockItem(HttpServletRequest request, StockItem item){
		AjaxResult result = new AjaxResult();
		try
		{
			stockService.deleteItem(item, Integer.parseInt(request.getParameter("stockType")));
		}
		catch (Exception e)
		{
			result.setIsError(true);
			e.printStackTrace();
		}
		
		return result;
	}
	

	@RequestMapping(value="stockin")
	public String toStockinPage(ModelMap model, @RequestParam(value="type")Integer type){
		// generate stock No.
		model.addAttribute("stockNo", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		model.addAttribute("stockType", type);

		return "/stock/in";
	}
	
	@RequestMapping(value="stockout")
	public String toStockoutPage(ModelMap model, @RequestParam(value="type")Integer type){
		// generate stock No.
		model.addAttribute("stockNo", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		model.addAttribute("stockType", type);

		return "/stock/out";
	}
	
	/**
	 * 旧的保存入口
	 * @param model
	 * @param request
	 * @param stock
	 * @return
	 */
	@RequestMapping(value="stock/save")
	@ResponseBody
	public Object stockinBuySave(ModelMap model, HttpServletRequest request, Stock stock){
		AjaxResult result = new AjaxResult();
		try
		{
			// json字符串转为JAVA对象
			String items = request.getParameter("itemsStr");
			List<StockItem> list = (List<StockItem>)JSONArray.toCollection(
					JSONArray.fromObject(items), StockItem.class);
			
			stock.setItems(list);
			stockService.save(stock);
		}
		catch (Exception e)
		{
			result.setIsError(true);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 导入excel保存入口
	 * @param model
	 * @param request
	 * @param stock
	 * @return
	 */
	@RequestMapping(value="stock/saveImportItems")
	@ResponseBody
	public Object saveImportItems(HttpServletRequest request, Integer stockType){
		AjaxResult result = new AjaxResult();
		try
		{
			// json字符串转为JAVA对象
			String items = request.getParameter("itemsStr");
			List<StockItem> list = (List<StockItem>)JSONArray.toCollection(
					JSONArray.fromObject(items), StockItem.class);
			
			stockService.saveImportItems(list, stockType);
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
	
	
	@RequestMapping(value="stock/uploadExcel")
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
					List<Entry> mList = materialService.findAllEntry();
					List<Dict> uList = dictService.findByType(1);
					Map<String, Entry> mMap = new HashMap<String, Entry>(0);
					Map<String, Dict> uMap = new HashMap<String, Dict>(0);
				
					if (mList != null && mList.size() > 0){
						for(Entry m : mList){
							mMap.put(m.getName(), m);
						}
					}
					if (uList != null && uList.size() > 0){
						for(Dict u : uList){
							uMap.put(u.getName(), u);
						}
					}
					
					List<StockItem> items = new ArrayList<StockItem>(0);
					for (int i = 0; i < sheetNum; i++){
						items.addAll(stockService.readItemFromSheet(wb.getSheetAt(i), mMap, uMap));
					}
					result.setObj(items);
				}
				else {
					result.setIsError(true);
					result.setMsg("无法读取xls文件中表单(sheet)！");
				}
				
			}else{
				result.setIsError(true);
				result.setMsg("请选择xls文件！");
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
