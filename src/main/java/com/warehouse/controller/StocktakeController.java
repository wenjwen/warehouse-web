package com.warehouse.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.model.Stocktake;
import com.warehouse.service.StocktakeService;
import com.warehouse.util.AjaxResult;

@Controller
public class StocktakeController
{
	private Logger logger = Logger.getLogger(StocktakeController.class);
	@Resource
	private StocktakeService stocktakeService;
	
	@RequestMapping(value="monthStocktake")
	public Object toCategorySettingPage(ModelMap model){
		//List<Category> list = categoryService.findAllEntry();
		//model.addAttribute("categoryJson", JSONArray.fromObject(list));
		
		return "/stocktake/month";
	}
	
	@RequestMapping(value="stocktake/month.json")
	@ResponseBody
	public Object findMonthStocktake(ModelMap model, Stocktake s){
		return stocktakeService.findAll(s);
	}
	
	@RequestMapping(value="stocktakeEntry.json")
	@ResponseBody
	public Object findAllEntry(){
		try
		{
			
			return stocktakeService.findAllEntry();
		}
		catch (Exception e)
		{
			logger.error("--findAllEntry()时出错！Msg:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="saveStocktake")
	@ResponseBody
	public Object saveStocktake(Stocktake s){
		AjaxResult result = new AjaxResult();
		try
		{
			Date now = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (s.getId() != null && s.getId() > 0){  // update 
				s.setUpdateTime(df.format(now));
				stocktakeService.updateSelective(s);
			}else{  // insert
				s.setCreateTime(df.format(now));
				s.setUpdateTime(df.format(now));
				stocktakeService.insert(s);
			}
			result.setIsError(false);
		}
		catch (Exception e)
		{
			result.setIsError(true);
			logger.error("---stocktake保存出错！ Msg:" + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="deleteStocktake")
	@ResponseBody
	public Object deleteStocktake(Integer id){
		AjaxResult result = new AjaxResult();
		try
		{
			stocktakeService.deleteById(id);
			result.setIsError(false);
		}
		catch (Exception e)
		{
			result.setIsError(true);
			logger.error("---stocktake保存出错！ Msg:" + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="stocktake/taking")
	public String taking(ModelMap model, Integer stocktakeId){
		model.addAttribute("stocktake", stocktakeService.findById(stocktakeId));
		
		return "/stocktake/taking";
	}
	
	
	@RequestMapping(value="stocktakeItem.json")
	@ResponseBody
	public Object findItems(Integer stocktakeId){
		try
		{
			return stocktakeService.findItemsByStocktakeId(stocktakeId);
		}
		catch (Exception e)
		{
			logger.error("---findItems(Integer stocktakeId)出错！ Msg:" + e.getMessage());
		}
		
		return null;
	}
	
}
