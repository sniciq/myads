package com.ku6ads.services.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.Email;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserRole;
import com.ku6ads.dao.iface.sysconfig.RoleDao;
import com.ku6ads.dao.iface.sysconfig.UserDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.sysconfig.RoleService;

public class RoleServiceImpl extends BaseAbstractService implements RoleService {

	private UserDao userDao;

	public List<UserRole> selectUserByRoleId(int roleId) {
		return ((RoleDao) getBaseDao()).selectUserByRoleId(roleId);
	}
	
	public Integer selectUserCountByRoleId(int roleId){
		return ((RoleDao) getBaseDao()).selectUserCountByRoleId(roleId);
	}
	
	public List<User> selectUserAdd(int roleId){
		return ((RoleDao) getBaseDao()).selectUserAdd(roleId);
	}
	
	public Integer selectUserAddCount(int roleId){
		return ((RoleDao) getBaseDao()).selectUserAddCount(roleId);
	}

	public List<User> selectUser(User user) {
		return userDao.selectByEntity(user);
	}

	public UserRole selectUserByRole(UserRole userRole) {
		return ((RoleDao) getBaseDao()).selectUserByRole(userRole);
	}

	public void insertUserByRole(UserRole userRole) {
		((RoleDao) getBaseDao()).insertUserByRole(userRole);
	}

	public void deleteUserByRole(UserRole userRole) {
		((RoleDao) getBaseDao()).deleteUserByRole(userRole);
	}

	public List<UserRole> selectByRoleName(String roleName) {
		return ((RoleDao) getBaseDao()).selectByRoleName(roleName);
	}
	
	public List<String> selectEmailList(String roleName) {
		return ((RoleDao) getBaseDao()).selectEmailList(roleName);
	}
	
	public void setMailProperty(Email mail){
		mail.setCharSet("utf-8");
		mail.setHostName("smtp.163.com");
		mail.setSender("shaoboy1984@163.com");
		mail.setTitle("审核信息");
		mail.setPassword("1984927");
	}
	
	public List<UserRole> selectUserRoleByName(UserRole userRole){
		return ((RoleDao) getBaseDao()).selectUserRoleByName(userRole);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
