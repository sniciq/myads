package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.dao.entity.sysconfig.UserRole;
import com.ku6ads.dao.iface.sysconfig.UserDao;

public class UserDaoImpl extends BaseAbstractDao implements UserDao {

	@Override
	public User login(User user) {
		return (User) getSqlMapClientTemplate().queryForObject("sysconfig.User.userLogin", user);
	}

	@SuppressWarnings("unchecked")
	public List<UserGroup> getUserGroup(String userName) {
		return getSqlMapClientTemplate().queryForList("sysconfig.User.getUserGroupByName", userName);
	}

	public void deleteByUserId(Integer id){
		getSqlMapClientTemplate().update("sysconfig.User.deleteByUserId", id);
	}
	
	public void updateByUserId(UserRole userRole){
		getSqlMapClientTemplate().update("sysconfig.User.updateByUserId", userRole);
	}
}
