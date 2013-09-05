package com.ku6ads.services.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.dao.entity.sysconfig.UserRole;
import com.ku6ads.services.base.BaseServiceIface;

public interface UserService extends BaseServiceIface {

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return User
	 */
	public User login(String username, String password);

	/**
	 * 按用户id查询用户对应角色组
	 * @param userName
	 * @return List<UserGroup>
	 */
	public List<UserGroup> getUserGroup(String userName);
	
	public void deleteByUserId(Integer id);
	
	public void updateByUserId(UserRole userRole);
}
