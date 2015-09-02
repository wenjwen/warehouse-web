package com.warehouse.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.warehouse.model.Dict;
import com.warehouse.service.DictService;
import com.warehouse.util.AjaxResult;
import com.warehouse.util.Constant;

@Controller
public class DictController
{
	private Logger logger = Logger.getLogger(DictController.class);
	
	@Resource
	private DictService dictService;
	
	@RequestMapping(value="unitSetting")
	public String toUnitSettingPage(ModelMap model){
		return "/baseInfo/unitSetting";
	}
	
	/**
	 * 查找所有物料单位
	 * @param model
	 * @return
	 */
	@RequestMapping(value="unitList.json")
	@ResponseBody
	public Object getAllUnit(ModelMap model){
		return dictService.findByType(Constant.DICT_TYPE_UNIT);
	}
	
	
	@RequestMapping(value="saveUnit")
	@ResponseBody
	public Object saveUnit(ModelMap model, Dict unit){
		AjaxResult result = new AjaxResult();
		try
		{  
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			
			unit.setCreateTime(df.format(now));
			unit.setUpdateTime(df.format(now));
			unit.setType(Constant.DICT_TYPE_UNIT); // 所属类型
			unit.setDisabled(Constant.DISABLED_FALSE);  // 不是无效的
			
			dictService.save(unit);
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
	
	@RequestMapping(value="updateUnit")
	@ResponseBody
	public Object updateUnit(ModelMap model, Dict unit){
		AjaxResult result = new AjaxResult();
		try
		{
			dictService.updateByIdSelective(unit);
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
	
	@RequestMapping(value="deleteUnit")
	@ResponseBody
	public Object deleteUnit(ModelMap model, Dict unit){
		AjaxResult result = new AjaxResult();
		try
		{
			unit.setDisabled(1); // disable
			dictService.updateByIdSelective(unit);
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
}
