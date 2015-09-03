package com.warehouse.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.mapper.CategoryMapper;
import com.warehouse.model.Category;

@Service
public class CategoryService
{
	@Resource
	private CategoryMapper categoryMapper;
	
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

	public void update(Category category)
	{
		categoryMapper.updateByPrimaryKey(category);	
	}

}
