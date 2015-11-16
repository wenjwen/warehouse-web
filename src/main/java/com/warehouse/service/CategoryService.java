package com.warehouse.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.common.BaseMapper;
import com.warehouse.common.BaseService;
import com.warehouse.mapper.CategoryMapper;
import com.warehouse.model.Category;

@Service
public class CategoryService extends BaseService<Category>
{
	@Resource
	private CategoryMapper categoryMapper;

	@Override
	public BaseMapper<Category> getMapper()
	{
		return categoryMapper;
	}
	
	public List<Category> findSelective(Category category)
	{
		return categoryMapper.findSelective(category);
	}

	public void save(Category category)
	{
		categoryMapper.insert(category);
	}

	public void updateByIdSelective(Category category)
	{
		categoryMapper.updateByPrimaryKeySelective(category);
	}

	public List<Category> findAllEntry()
	{
		return categoryMapper.findAllEntry();
	}


}
