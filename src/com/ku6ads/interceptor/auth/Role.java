package com.ku6ads.interceptor.auth;

import java.io.Serializable;

/**
 * 用户拥有角色实体类
 * 
 * @author xuxianan
 *
 */
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
