package com.warehouse.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.common.BaseMapper;
import com.warehouse.common.BaseService;
import com.warehouse.mapper.UserMapper;
import com.warehouse.model.User;

@Service
public class UserService extends BaseService<User>
{
	@Resource
	private UserMapper userMapper;

	@Override
	public BaseMapper<User> getMapper()
	{
		return userMapper;
	}
	
	public User findByName(String name)
	{
		return userMapper.findByName(name);
	}
	
	public User findByLoginName(String loginName)
	{
		return userMapper.findByLoginName(loginName);
	}

	public void updateByIdSelective(User user)
	{
		userMapper.updateByPrimaryKeySelective(user);
	}

	public List<User> findAll()
	{
		return userMapper.findAll();
	}

}
