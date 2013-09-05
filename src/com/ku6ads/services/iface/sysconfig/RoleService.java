package com.ku6ads.services.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Email;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserRole;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * @author xuxianan
 *
 */
public interface RoleService extends BaseServiceIface {

	/**
	 * 根据角色id查询用户表看该id是否使用
	 * @param roleId
	 * @return List<User>
	 */
	public List<UserRole> selectUserByRoleId(int roleId);
	
	public Integer selectUserCountByRoleId(int roleId);

	public List<User> selectUserAdd(int roleId);
	
	public Integer selectUserAddCount(int roleId);
	/**
	 * 查询用户表,返回其集合
	 * @return List<Role>
	 */
	public List<User> selectUser(User user);
	
	public UserRole selectUserByRole(UserRole userRole);
	
	public void insertUserByRole(UserRole userRole);
	
	public void deleteUserByRole(UserRole userRole);
	
	public List<UserRole> selectByRoleName(String roleName);
	
	public List<String> selectEmailList(String roleName);
	
	public void setMailProperty(Email mail);
	
	public List<UserRole> selectUserRoleByName(UserRole userRole);
}
