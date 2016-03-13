package com.warehouse.mapper;

import java.util.List;

import com.warehouse.common.BaseMapper;
import com.warehouse.model.User;

public interface UserMapper extends BaseMapper<User> {
    
	int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

	User findByName(String name);
	
	User findByLoginName(String loginName);

	List<User> findAll();
}