package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.dao.entity.sysconfig.UserRole;

public interface UserDao extends BaseDao {
	
	public User login(User user);
	
	public List<UserGroup> getUserGroup(String userName);
	
	public void deleteByUserId(Integer id);
	
	public void updateByUserId(UserRole userRole);
}
