package com.example.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Alias("xxx") 给当前的类起别名
 * 在roleMapper中resultType="dbrole"中有使用到
 * <select id="findRolesByUserName" parameterType="string"  resultType="dbrole">
 */
@Alias("dbrole")
public class DatabaseRole implements Serializable {
	private static final long serialVersionUID = -9187344495447048574L;
	
	private Long id;
	private String roleName;
	private String note;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	

}
