package com.ku6ads.services.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Group;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * 本地用户组service
 * @author xuxianan
 *
 */
public interface GroupService extends BaseServiceIface {

	/**
	 * 查询本地用户组
	 * @return List<Group>
	 */
	public List<Group> selectGroup();

	/**
	 * 新增用户所属组记录
	 * @param userGroup
	 */
	public void insertUserGroup(UserGroup userGroup);

	/**
	 * 根据用户组id查询组内所有用户
	 * @param groupId
	 * @return
	 */
	public List<User> selectUserByGroupId(int groupId);

	/**
	 * 根据用户组id统计组内成员数量
	 * @param groupId
	 * @return
	 */
	public Integer selectUserCountByGroupId(int groupId);

	/**
	 * 查看可以添加的用户列表
	 * @param groupId
	 * @return
	 */
	public List<User> selectAddUser(int groupId);

	/**
	 * 根据用户id删除对应用户组记录
	 * @param id
	 */
	public void deleteUserGroupById(int id);

	/**
	 * 指定用户为对应组管理员
	 * @param id
	 */
	public void specifiedAdmin(int id);

	/**
	 * 验证对应人员组内管理员
	 * @param groupId
	 * @return
	 */
	public List<UserGroup> checkUserGroupAdminById(int groupId);

	/**
	 * 取消指定用户管理员授权
	 * @param id
	 */
	public void cancelSpecifiedAdmin(int id);

	/**
	 * 根据权限对接平台登陆后返回的用户名查询用户组
	 * @param userName
	 * @return
	 */
	public List<UserGroup> selectUserGroupByUserName(String userName);

	/**
	 * 查询组内除自己外的所有成员
	 * @param username
	 * @return
	 */
	public List<UserGroup> selectUserGroupByUserNameNotIn(UserGroup userGroup);
	
	/**
	 * 查询所有邮件组内所有成员
	 * @return
	 */
	public List<User> selectMailGroupPersonnel();
}
