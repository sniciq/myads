package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.sysconfig.Role;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserRole;
import com.ku6ads.dao.iface.sysconfig.RoleDao;

public class RoleDaoImpl extends BaseAbstractDao implements RoleDao {

	@SuppressWarnings("unchecked")
	public List<UserRole> selectUserByRoleId(int roleId) {
		return getSqlMapClientTemplate().queryForList("sysconfig.Role.selectUserByRoleId", roleId);
	}
	
	public Integer selectUserCountByRoleId(int roleId) {
		return (Integer)getSqlMapClientTemplate().queryForObject("sysconfig.Role.selectUserCountByRoleId", roleId);
	}
	
	public List<User> selectUserAdd(int roleId){
		return getSqlMapClientTemplate().queryForList("sysconfig.Role.selectUserAdd", roleId);
	}
	
	public Integer selectUserAddCount(int roleId){
		return (Integer)getSqlMapClientTemplate().queryForObject("sysconfig.Role.selectUserAddCount", roleId);
	}

	@SuppressWarnings("unchecked")
	public List<Role> selectRole() {
		return getSqlMapClientTemplate().queryForList("sysconfig.Role.selectByEntity");
	}
	
	public UserRole selectUserByRole(UserRole userRole){
		return (UserRole)getSqlMapClientTemplate().queryForObject("sysconfig.Role.selectUserByRole", userRole);
	}
	
	public void insertUserByRole(UserRole userRole){
		getSqlMapClientTemplate().insert("sysconfig.Role.insertUserByRole",userRole);
	}
	
	public void deleteUserByRole(UserRole userRole){
		getSqlMapClientTemplate().delete("sysconfig.Role.deleteUserByRole",userRole);
	}
	
	public List<UserRole> selectByRoleName(String roleName){
		return getSqlMapClientTemplate().queryForList("sysconfig.Role.selectByRoleName",roleName);
	}
	
	public List<String> selectEmailList(String roleName){
		return getSqlMapClientTemplate().queryForList("sysconfig.Role.selectEmailList",roleName);
	}
	
	public List<UserRole> selectUserRoleByName(UserRole userRole){
		return getSqlMapClientTemplate().queryForList("sysconfig.Role.selectUserRoleByName",userRole);
	}
}
