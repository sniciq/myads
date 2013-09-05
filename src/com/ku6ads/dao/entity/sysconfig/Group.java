package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 用户本地组
 * @author xuxianan
 *
 */
public class Group extends ExtEntity {

	private Integer id;
	private String name;
	private String remark;
	private String flag;

	public static final int STATUS_START = 0; // 启用
	public static final int STATUS_DELETE = 1; // 停用

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
