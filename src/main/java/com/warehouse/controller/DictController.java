package com.warehouse.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.jdbc.UncategorizedSQLException;
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
		model.addAttribute("unitJson", JSONArray.fromObject(dictService.findAllEntry()));
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
		catch (UncategorizedSQLException ue){
			if (ue.getSQLException().getErrorCode() == 19 && ue.getMessage().contains("UNIQUE constraint failed")){
				// 违反唯一约束
				result.setCode("102");
			}
			else{
				result.setCode(ue.getSQLException().getErrorCode() + "");
			}
			
			result.setIsError(true);
			logger.error(ue.getMessage());
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
		catch (UncategorizedSQLException ue){
			result.setCode(ue.getSQLException().getErrorCode() + "");
			
			result.setIsError(true);
			logger.error(ue.getMessage());
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
			//unit.setDisabled(1); // disable
			//dictService.updateByIdSelective(unit);
			dictService.deleteById(unit.getId());
			result.setIsError(false);
		}
		catch (UncategorizedSQLException ue){
			result.setIsError(true);
			logger.error(ue.getMessage());
			result.setCode(ue.getSQLException().getErrorCode() + "");
		}
		catch (Exception e)
		{	
			result.setIsError(true);
			logger.error(e.getMessage());
		}
		
		return result;
	}
}
