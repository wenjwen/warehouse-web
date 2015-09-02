package com.warehouse.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.service.MaterialService;

@Controller
public class MaterialController
{
	@Resource
	private MaterialService materialService;
	
	@RequestMapping(value="materialEntry.json")
	@ResponseBody
	public Object findMaterialEntry(ModelMap model){
		
		return materialService.findAllEntry();
	}
}
