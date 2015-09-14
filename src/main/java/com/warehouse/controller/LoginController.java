package com.warehouse.controller;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.warehouse.service.CategoryService;
import com.warehouse.service.DictService;
import com.warehouse.service.MaterialService;

@Controller
public class LoginController
{
	@Resource
	private DictService dictService;
	@Resource
	private MaterialService materialService;
	@Resource
	private CategoryService categoryService;
	
	@RequestMapping(method=RequestMethod.GET, value="/")
	public String toMainPage(ModelMap model){
		model.addAttribute("unitJson", JSONArray.fromObject(dictService.findAllEntry()));
		model.addAttribute("categoryJson", JSONArray.fromObject(categoryService.findAllEntry()));
		model.addAttribute("materialJson", JSONArray.fromObject(materialService.findAllEntry()));
		return "/main";
	}
	
}
