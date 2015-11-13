package com.warehouse.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.warehouse.model.User;
import com.warehouse.service.CategoryService;
import com.warehouse.service.DictService;
import com.warehouse.service.MaterialService;
import com.warehouse.service.UserService;
import com.warehouse.util.Constant;
import com.warehouse.util.PwdUtil;

@Controller
public class LoginController
{
	@Resource
	private DictService dictService;
	@Resource
	private MaterialService materialService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET, value="/")
	public String toLoginPage(HttpServletRequest request, ModelMap model){
		request.getSession().setAttribute(Constant.USER, null);
		return "/login";
	}
	
	@RequestMapping(value="/main")
	public String toMainPage(HttpServletRequest request, ModelMap model, String userName, String password){
		if (request.getSession().getAttribute(Constant.USER) == null){
			User user = userService.findByName(userName);
			if (user == null || user.getId() == null){
				model.addAttribute("code", -1); // 用户名不存在
				return "/login";
			}
			if (!user.getPassword().equals(PwdUtil.md5Encryption(password))){
				model.addAttribute("code", -2); // 密码不正确
				return "/login";
			}
			
			request.getSession().setAttribute(Constant.USER, user);
		}
		
		model.addAttribute("unitJson", JSONArray.fromObject(dictService.findByType(1))); // 1 单位
		model.addAttribute("categoryJson", JSONArray.fromObject(categoryService.findAllEntry()));
		model.addAttribute("materialJson", JSONArray.fromObject(materialService.findAllEntry()));
		return "/main";
	}
	
	@RequestMapping(value="/searchonly")
	public String toMainPage(HttpServletRequest request, ModelMap model){
		// 只查询功能，把session中user清空
		request.getSession().setAttribute(Constant.USER, null);
		model.addAttribute("unitJson", JSONArray.fromObject(dictService.findByType(1))); // 1 单位
		model.addAttribute("categoryJson", JSONArray.fromObject(categoryService.findAllEntry()));
		model.addAttribute("materialJson", JSONArray.fromObject(materialService.findAllEntry()));
		return "/main";
	}
	
}
