package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.sysconfig.Group;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.dao.iface.sysconfig.GroupDao;

public class GroupDaoImpl extends BaseAbstractDao implements GroupDao {

	@SuppressWarnings("unchecked")
	public List<Group> selectGroup() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Group.selectByEntity");
	}

	@Override
	public void insertUserGroup(UserGroup userGroup) {
		getSqlMapClientTemplate().insert("sysconfig.Group.insertUserGroup", userGroup);
	}

	@SuppressWarnings("unchecked")
	public List<User> selectUserByGroupId(int groupId) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Group.selectUserByGroupId", groupId);
	}

	@Override
	public Integer selectUserCountByGroupId(int groupId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sysconfig.Group.selectUserCountByGroupId", groupId);
	}

	@SuppressWarnings("unchecked")
	public List<User> selectAddUser(int groupId) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Group.selectAddUser", groupId);
	}

	@Override
	public void deleteUserGroupById(int id) {
		getSqlMapClientTemplate().delete("sysconfig.Group.deleteUserGroupById", id);
	}

	@Override
	public void specifiedAdmin(int id) {
		getSqlMapClientTemplate().update("sysconfig.Group.specifiedAdmin", id);
	}

	@SuppressWarnings("unchecked")
	public List<UserGroup> checkUserGroupAdminById(int groupId) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Group.checkUserGroupAdminById", groupId);
	}

	@Override
	public void cancelSpecifiedAdmin(int id) {
		getSqlMapClientTemplate().update("sysconfig.Group.cancelSpecifiedAdmin", id);
	}

	@SuppressWarnings("unchecked")
	public List<UserGroup> selectUserGroupByUserName(String username) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Group.selectUserGroupByUserName", username);
	}

	@SuppressWarnings("unchecked")
	public List<UserGroup> selectUserGroupByUserNameNotIn(UserGroup userGroup) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Group.selectUserGroupByUserNameNotIn", userGroup);
	}

	@SuppressWarnings("unchecked")
	public List<User> selectMailGroupPersonnel() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Group.selectMailGroupPersonnel");
	}

}
