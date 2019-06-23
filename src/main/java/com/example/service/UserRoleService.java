package com.example.service;

import com.example.pojo.DatabaseRole;
import com.example.pojo.DatabaseUser;

import java.util.List;

public interface UserRoleService {

	public DatabaseUser getUserByName(String userName);

	public List<DatabaseRole> findRolesByUserName(String userName);
}
