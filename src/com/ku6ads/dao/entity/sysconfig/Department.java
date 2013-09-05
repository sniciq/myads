package com.ku6ads.dao.entity.sysconfig;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 部门
 * @author xuxianan
 *
 */
public class Department extends ExtEntity {

	private Integer departmentId; // 部门编号
	private Integer parentDepartId; // 上级部门编号
	private String departmentName; // 名称
	private String parentName; // 上级部门名称
	private boolean status; // 状态 0:禁用 1:启用
	private Integer creatorId; // 创建者
	private Date createDate; // 创建日期

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getParentDepartId() {
		return parentDepartId;
	}

	public void setParentDepartId(Integer parentDepartId) {
		this.parentDepartId = parentDepartId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
