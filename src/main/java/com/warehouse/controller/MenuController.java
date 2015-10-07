package com.warehouse.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.model.User;
import com.warehouse.service.MenuService;
import com.warehouse.util.Constant;


@Controller
public class MenuController
{
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value="menuTree.json")
	@ResponseBody
	public Object getMenu(HttpServletRequest request, ModelMap model){
		Object user = (User) request.getSession().getAttribute(Constant.USER);
		if (user != null){
			return menuService.getMenuTree();  // 所有菜单
		}else{
			return menuService.getLimitedMenuTree(); // 没有登录限制菜单，只取查询菜单
		}
	}
	
}
