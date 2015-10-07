package com.warehouse.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.mapper.DictMapper;
import com.warehouse.model.Dict;

@Service
public class DictService
{
	@Resource
	private DictMapper dictMapper;
	
	/**
	 * 按类型查询字典信息
	 * @param type
	 * @return
	 */
	public List<Dict> findByType(int type){
		return dictMapper.findByType(type);
	}

	
	public int save(Dict unit)
	{
		return dictMapper.insert(unit);
	}


	public int updateByIdSelective(Dict unit)
	{
		return dictMapper.updateByPrimaryKeySelective(unit);
		
	}


	public Object findAllEntry()
	{
		return dictMapper.findAllEntry();
	}


	public void deleteById(Integer id) throws SQLException
	{
		dictMapper.deleteByPrimaryKey(id);
	}
}
