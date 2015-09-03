package com.warehouse.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.service.CategoryService;

@Controller
public class CategoryController
{
	@Resource
	private CategoryService categoryService;
	
	@RequestMapping(value="categorySetting")
	public Object toCategorySettingPage(ModelMap model){
		return "/baseInfo/categorySetting";
	}
	
	
	@RequestMapping(value="category.json")
	@ResponseBody
	public Object categorySetting(ModelMap model){
		return categoryService.findAll();
	}
}
