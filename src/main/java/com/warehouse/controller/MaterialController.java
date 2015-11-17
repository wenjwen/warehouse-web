package com.warehouse.controller;

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

import com.warehouse.common.PageParamModel;
import com.warehouse.model.Material;
import com.warehouse.service.CategoryService;
import com.warehouse.service.DictService;
import com.warehouse.service.MaterialService;
import com.warehouse.util.AjaxResult;
import com.warehouse.util.Constant;

@Controller
public class MaterialController
{
	private Logger logger = Logger.getLogger(MaterialController.class);
	@Resource
	private MaterialService materialService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private DictService dictService;
	
	@RequestMapping(value="materialEntry.json")
	@ResponseBody
	public Object findMaterialEntry(ModelMap model){
		return materialService.findAllEntry();
	}
	
	@RequestMapping(value="materialSetting")
	public Object materialSetting(ModelMap model){
		
		model.addAttribute("materialJson", JSONArray.fromObject(materialService.findAllEntry()));
		return "/baseInfo/materialSetting";
	}
	
	
	@RequestMapping(value="searchMaterial.json")
	@ResponseBody
	public Object categorySetting(ModelMap model, Material material){
		// material.setDisabled(0); // 只查有效的
		return materialService.findSelective(material);
	}
	
	// 物料信息分页查询
	@RequestMapping(value="material.json")
	@ResponseBody
	public Object categorySetting(PageParamModel<Material> ppm, Material m){
		return materialService.paginQuery(m, ppm.getPage(), ppm.getRows());
	}
	
	@RequestMapping(value="saveMaterial")
	@ResponseBody
	public Object saveUnit(ModelMap model, Material material){
		AjaxResult result = new AjaxResult();
		try
		{  
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			
			material.setCreateTime(df.format(now));
			material.setUpdateTime(df.format(now));
			material.setDisabled(Constant.DISABLED_FALSE);  // 不是无效的
			
			materialService.save(material);
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
			logger.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="updateMaterial")
	@ResponseBody
	public Object updateUnit(ModelMap model, Material material){
		AjaxResult result = new AjaxResult();
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			material.setUpdateTime(df.format(now));
			
			materialService.update(material);
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
	
	@RequestMapping(value="deleteMaterial")
	@ResponseBody
	public Object deleteUnit(ModelMap model, Material material){
		AjaxResult result = new AjaxResult();
		try
		{
			//material.setDisabled(1); // disable
			//materialService.updateByIdSelective(material);
			materialService.deleteById(material.getId());
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
