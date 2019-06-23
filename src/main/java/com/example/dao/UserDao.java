package com.example.dao;

import com.example.pojo.DatabaseUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

	public DatabaseUser getUser(String userName);
}
