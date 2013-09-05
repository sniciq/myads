package com.ku6ads.services.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.dao.entity.sysconfig.UserRole;
import com.ku6ads.dao.iface.sysconfig.UserDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.sysconfig.UserService;

public class UserServiceImpl extends BaseAbstractService implements UserService {

	@Override
	public User login(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		return ((UserDao) getBaseDao()).login(user);
	}

	@Override
	public List<UserGroup> getUserGroup(String userName) {
		return ((UserDao) getBaseDao()).getUserGroup(userName);
	}

	@Override
	public void deleteByUserId(Integer id) {
		((UserDao) getBaseDao()).deleteByUserId(id);
	}
	
	public void updateByUserId(UserRole userRole){
		((UserDao) getBaseDao()).updateByUserId(userRole);
	}
}
