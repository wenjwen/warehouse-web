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
	
	public List<Category> findAll()
	{
		return categoryMapper.findAll();
	}

}
