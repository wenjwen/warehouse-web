package com.warehouse.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.service.MenuService;


@Controller
public class MenuController
{
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value="menuTree.json")
	@ResponseBody
	public Object getMenu(ModelMap model){
		
		return menuService.getMenuTree();
	}
	
}
