package com.warehouse.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.model.StockSearch;
import com.warehouse.service.CategoryService;
import com.warehouse.service.DictService;
import com.warehouse.service.MaterialService;
import com.warehouse.service.StockService;
import com.warehouse.service.StocktakeService;

@Controller
public class SearchController
{
	@Resource
	private MaterialService materialService;
	@Resource
	private StockService stockService;
	@Resource
	private StocktakeService stocktakeService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private DictService dictService;
	
	@RequestMapping(value="materialSearchPage")
	public Object toInventorySearchPage(ModelMap model)
	{
		//model.addAttribute("categoryJson", JSONArray.fromObject(categoryService.findAllEntry()));
		//model.addAttribute("unitJson", JSONArray.fromObject(dictService.findAllEntry()));
		return "/search/material";
	}
	
	@RequestMapping(value="stockinoutSearchPage")
	public Object toStockinSearchPage(ModelMap model)
	{
		//model.addAttribute("categoryJson", JSONArray.fromObject(categoryService.findAllEntry()));
		//model.addAttribute("materialJson", JSONArray.fromObject(materialService.findAllEntry()));
		//model.addAttribute("unitJson", JSONArray.fromObject(dictService.findAllEntry()));
		
		return "/search/stockinout";
	}
	
	/**
	 * 出入库查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value="stockinout.json")
	@ResponseBody
	public Object StockinoutSearch(ModelMap model, StockSearch stockSearch)
	{
		return stockService.stockinoutSearch(stockSearch);
	}
	
	
	@RequestMapping(value="stocktakeSearchPage")
	public Object toStocktakeSearchPage(ModelMap model)
	{
		return "/search/stocktake";
	}
	
	/**
	 * 盘点查询
	 * @param model
	 * @return
	 */
	@RequestMapping(value="stocktakeSearch")
	@ResponseBody
	public Object StocktakeSearch(ModelMap model)
	{
		
		return null;
	}
}
