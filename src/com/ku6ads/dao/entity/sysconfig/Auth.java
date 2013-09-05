package com.ku6ads.dao.entity.sysconfig;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 权限
 * @author xuxianan
 *
 */
public class Auth extends ExtEntity {

	private Integer authId; // 编号
	private String name; // 名称
	private String description; // 描述
	private Date createDate; // 创建日期

	public Integer getAuthId() {
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
