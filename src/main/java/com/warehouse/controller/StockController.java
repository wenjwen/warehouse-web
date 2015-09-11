package com.warehouse.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.model.Stock;
import com.warehouse.model.StockItem;
import com.warehouse.service.DictService;
import com.warehouse.service.StockService;
import com.warehouse.util.AjaxResult;

@Controller
public class StockController
{
	private Logger logger = Logger.getLogger(StockController.class);
	
	@Resource
	private StockService stockService;
	
	@Resource
	private DictService dictService;
	
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
	
	
	@RequestMapping(value="stockin/save")
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
	
	
}
