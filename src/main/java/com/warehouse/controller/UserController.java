package com.warehouse.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.model.User;
import com.warehouse.service.UserService;
import com.warehouse.util.AjaxResult;
import com.warehouse.util.Constant;
import com.warehouse.util.PwdUtil;

@Controller
public class UserController
{
	@Resource
	private UserService userService;
	
	@RequestMapping("/passwordModifyPage")
	public String toPasswordModifyPage(){
		return "/user/passwordModify";
	}
	
	@RequestMapping("/passwordModify")
	@ResponseBody
	public Object passwordModify(HttpServletRequest request, ModelMap model, String oldPassword, String newPassword){
		AjaxResult result = new AjaxResult();
		User user = (User)request.getSession().getAttribute(Constant.USER);
		if(!user.getPassword().equals(PwdUtil.md5Encryption(oldPassword))){
			result.setCode("-1"); // 旧密码不正确
		} else {
			user.setPassword(PwdUtil.md5Encryption(newPassword));
			user.setUpdateTime(Constant.DATETIME_FORMATTER.format(new Date()));
			userService.updateByIdSelective(user);
			result.setCode("1"); // 修改成功，下次登录生效
		}
		
		return result;
	}
}
