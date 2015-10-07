package com.warehouse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.warehouse.mapper.MenuMapper;
import com.warehouse.model.Menu;

@Service
public class MenuService
{
	private Logger logger = Logger.getLogger(MenuService.class);
	
	@Resource
	private MenuMapper menuMapper;
	
	public List<Menu> findAll(){
		return menuMapper.findAll();
	}

	public List<Menu> getMenuTree()
	{
		List<Menu> list = this.findAll();
		if (list != null && list.size() > 0){
			Map<String, Menu> map = new TreeMap<String, Menu>();
			
			// 此处逻辑只适合两级菜单
			for (Menu m : list){
				logger.info("---m.id = " + m.getId());
				if (m.getIsTop() == 1 || m.getParentId() == null){ // 是顶级节点
					map.put(m.getId().toString(), m);
				}
			}
			
			for (Menu m : list){
				if (m.getIsTop() == 0 && m.getParentId() != null){ // 不是顶级，0否1是
					Menu parent = map.get(m.getParentId().toString());
					parent.addChildren(m);  // 添加到父菜单中
				}
			}
			
			list = new ArrayList<Menu>(map.size());
			list.addAll(map.values());
		}
		
		return list;
	}

	public List<Menu> getLimitedMenuTree()
	{
		List<Menu> list = menuMapper.findByCode("05");
		if (list != null && list.size() > 0){
			Map<String, Menu> map = new TreeMap<String, Menu>();
			
			// 此处逻辑只适合两级菜单
			for (Menu m : list){
				logger.info("---m.id = " + m.getId());
				if (m.getIsTop() == 1 || m.getParentId() == null){ // 是顶级节点
					map.put(m.getId().toString(), m);
				}
			}
			
			for (Menu m : list){
				if (m.getIsTop() == 0 && m.getParentId() != null){ // 不是顶级，0否1是
					Menu parent = map.get(m.getParentId().toString());
					parent.addChildren(m);  // 添加到父菜单中
				}
			}
			
			list = new ArrayList<Menu>(map.size());
			list.addAll(map.values());
		}
		return list;
	}

}
