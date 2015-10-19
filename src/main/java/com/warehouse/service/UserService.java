package com.warehouse.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.mapper.UserMapper;
import com.warehouse.model.User;

@Service
public class UserService
{

	@Resource
	private UserMapper userMapper;
	
	public User findByName(String userName)
	{
		return userMapper.findByName(userName);
	}

	public void updateByIdSelective(User user)
	{
		userMapper.updateByPrimaryKeySelective(user);
	}
	
}
