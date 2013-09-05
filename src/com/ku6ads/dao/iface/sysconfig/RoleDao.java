package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.Role;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserRole;

public interface RoleDao extends BaseDao {

	public List<UserRole> selectUserByRoleId(int roleId);
	
	public Integer selectUserCountByRoleId(int roleId);
	
	public List<User> selectUserAdd(int roleId);
	
	public Integer selectUserAddCount(int roleId);
	
	public List<Role> selectRole();
	
	public UserRole selectUserByRole(UserRole userRole);
	
	public void insertUserByRole(UserRole userRole);
	
	public void deleteUserByRole(UserRole userRole);
	
	public List<UserRole> selectByRoleName(String roleName);
	
	public List<String> selectEmailList(String roleName);
	
	public List<UserRole> selectUserRoleByName(UserRole userRole);
}
