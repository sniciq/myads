package com.ku6ads.services.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Group;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.dao.iface.sysconfig.GroupDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.sysconfig.GroupService;

public class GroupServiceImpl extends BaseAbstractService implements GroupService {

	public List<Group> selectGroup() {
		return ((GroupDao) getBaseDao()).selectGroup();
	}

	@Override
	public void insertUserGroup(UserGroup userGroup) {
		((GroupDao) getBaseDao()).insertUserGroup(userGroup);
	}

	@Override
	public List<User> selectUserByGroupId(int groupId) {
		return ((GroupDao) getBaseDao()).selectUserByGroupId(groupId);
	}

	@Override
	public Integer selectUserCountByGroupId(int groupId) {
		return ((GroupDao) getBaseDao()).selectUserCountByGroupId(groupId);
	}

	@Override
	public List<User> selectAddUser(int groupId) {
		return ((GroupDao) getBaseDao()).selectAddUser(groupId);
	}

	@Override
	public void deleteUserGroupById(int id) {
		((GroupDao) getBaseDao()).deleteUserGroupById(id);
	}

	@Override
	public void specifiedAdmin(int id) {
		((GroupDao) getBaseDao()).specifiedAdmin(id);
	}

	@Override
	public List<UserGroup> checkUserGroupAdminById(int groupId) {
		return ((GroupDao) getBaseDao()).checkUserGroupAdminById(groupId);
	}

	@Override
	public void cancelSpecifiedAdmin(int id) {
		((GroupDao) getBaseDao()).cancelSpecifiedAdmin(id);
	}

	@Override
	public List<UserGroup> selectUserGroupByUserName(String userName) {
		return ((GroupDao) getBaseDao()).selectUserGroupByUserName(userName);
	}

	@Override
	public List<UserGroup> selectUserGroupByUserNameNotIn(UserGroup userGroup) {
		return ((GroupDao) getBaseDao()).selectUserGroupByUserNameNotIn(userGroup);
	}

	@Override
	public List<User> selectMailGroupPersonnel() {
		return ((GroupDao) getBaseDao()).selectMailGroupPersonnel();
	}

}
