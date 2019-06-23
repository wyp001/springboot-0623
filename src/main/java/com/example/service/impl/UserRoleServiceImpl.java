package com.example.service.impl;

import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.pojo.DatabaseRole;
import com.example.pojo.DatabaseUser;
import com.example.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service

public class UserRoleServiceImpl implements UserRoleService {
	
//	@Autowired
	@Resource
	private UserDao userDao = null;
//	@Autowired
	@Resource
	private RoleDao roleDao = null;

	@Override
	@Cacheable(value = "redisCache", key = "'redis_user_'+#userName")
	@Transactional
	public DatabaseUser getUserByName(String userName) {
		return userDao.getUser(userName);
	}

	@Override
	@Cacheable(value = "redisCache", key = "'redis_user_role_'+#userName")
	@Transactional
	public List<DatabaseRole> findRolesByUserName(String userName) {
		return roleDao.findRolesByUserName(userName);
	}

}
