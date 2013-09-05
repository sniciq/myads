package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.Group;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;

/**
 * 本地用户组dao
 * @author xuxianan
 *
 */
public interface GroupDao extends BaseDao {

	public List<Group> selectGroup();

	public void insertUserGroup(UserGroup userGroup);
	
	public List<User> selectUserByGroupId(int groupId);
	
	public Integer selectUserCountByGroupId(int groupId);
	
	public List<User> selectAddUser(int groupId);
	
	public void deleteUserGroupById(int id);
	
	public void specifiedAdmin(int id);
	
	public void cancelSpecifiedAdmin(int id);
	
	public List<UserGroup> checkUserGroupAdminById(int groupId);
	
	public List<UserGroup> selectUserGroupByUserName(String username);
	
	public List<UserGroup> selectUserGroupByUserNameNotIn(UserGroup userGroup);
	
	public List<User> selectMailGroupPersonnel();
}
