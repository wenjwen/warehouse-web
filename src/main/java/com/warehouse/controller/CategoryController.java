package com.warehouse.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.model.Category;
import com.warehouse.service.CategoryService;
import com.warehouse.util.AjaxResult;
import com.warehouse.util.Constant;

@Controller
public class CategoryController
{
	private Logger logger = Logger.getLogger(CategoryController.class);
	@Resource
	private CategoryService categoryService;
	
	@RequestMapping(value="categorySetting")
	public Object toCategorySettingPage(ModelMap model){
		List<Category> list = categoryService.findAllEntry();
		
//		if (list == null){
//			list = new ArrayList<Category>(1);
//		}
//		Category c = new Category();
//		c.setId(-1);
//		c.setName("无");
//		list.add(0, c);
		
		model.addAttribute("categoryJson", JSONArray.fromObject(list));
		return "/baseInfo/categorySetting";
	}
	
	@RequestMapping(value="categoryEntry.json")
	@ResponseBody
	public Object findAllEntry(ModelMap model){
		return categoryService.findAllEntry();
	}
	
	
	@RequestMapping(value="category.json")
	@ResponseBody
	public Object categorySetting(ModelMap model, Category category){
		return categoryService.findSelective(category);
	}
	
	@RequestMapping(value="saveCategory")
	@ResponseBody
	public Object saveUnit(ModelMap model, Category category){
		AjaxResult result = new AjaxResult();
		try
		{  
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			
			category.setCreateTime(df.format(now));
			category.setUpdateTime(df.format(now));
			category.setDisabled(Constant.DISABLED_FALSE);  // 不是无效的
			
			categoryService.save(category);
			result.setIsError(false);
		}
		catch (Exception e)
		{
			result.setIsError(true);
			result.setMsg(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="updateCategory")
	@ResponseBody
	public Object updateUnit(ModelMap model, Category category){
		AjaxResult result = new AjaxResult();
		try
		{
			// categoryService.updateByIdSelective(category);
			categoryService.update(category);
			result.setIsError(false);
		}
		catch (Exception e)
		{
			result.setIsError(true);
			result.setMsg(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="deleteCategory")
	@ResponseBody
	public Object deleteUnit(ModelMap model, Category category){
		AjaxResult result = new AjaxResult();
		try
		{
			//category.setDisabled(1); // disable
			//categoryService.updateByIdSelective(category);
			categoryService.deleteById(category.getId());
			result.setIsError(false);
		}
		catch (UncategorizedSQLException ue){
			result.setIsError(true);
			result.setCode(ue.getSQLException().getErrorCode() + "");
			logger.error(ue.getMessage());
		}
		catch (Exception e)
		{
			result.setIsError(true);
			logger.error(e.getMessage());
		}
		return result;
	}
}
