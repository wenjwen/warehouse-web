package com.warehouse.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.mapper.MaterialMapper;
import com.warehouse.model.Material;
import com.warehouse.util.Entry;

@Service
public class MaterialService
{
	@Resource
	private MaterialMapper materialMapper;

	/**
	 *  找查所有id,name
	 * @return list
	 */
	public List<Entry> findAllEntry()
	{
		return materialMapper.findAllEntry();
	}

	public List<Material> findAll()
	{
		return materialMapper.findAll();
	}

	public List<Material> findSelective(Material m)
	{
		return materialMapper.findSelective(m);
	}

	public void updateByIdSelective(Material material)
	{
		materialMapper.updateByPrimaryKeySelective(material);
	}

	public void update(Material material)
	{
		materialMapper.updateByPrimaryKey(material);
	}

	public void save(Material material)
	{
		materialMapper.insert(material);
	}

}
