package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 角色组
 * @author xuxianan
 *
 */
public class RoleGroup extends ExtEntity {

	private Integer groupId;
	private Role roleId;
	private Auth authId;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public Auth getAuthId() {
		return authId;
	}

	public void setAuthId(Auth authId) {
		this.authId = authId;
	}

}
