package com.warehouse.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.service.StocktakeService;

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
	public Object findMonthStocktake(ModelMap model){
		
		return null;
	}
}
