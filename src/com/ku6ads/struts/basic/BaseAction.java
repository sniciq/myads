package com.ku6ads.struts.basic;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.interceptor.auth.Role;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = -6926459153210309063L;

	/**
	 * 从session中取得用户登录信息;
	 * @return
	 */
	public UserInfoEty getLoginUser() {
		return (UserInfoEty) ServletActionContext.getRequest().getSession().getAttribute("UserEty");
	}

	/**
	 * 从session中获得用户角色列表,验证当前用户所拥有角色, 拥有'管理'和'主管'角色的则可以查询全部数据,
	 * 
	 * 否则只能查询自己的;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String validationUserGroup() {
		String userName = "";
		List<Role> roleList = (List<Role>) ServletActionContext.getRequest().getSession().getAttribute("UserRole");
		for (Role role : roleList) {
			if (role.getName().contains("管理") || role.getName().contains("主管")) {
				userName = "";
				break;
			} else {
				userName = this.getLoginUser().getUsername();
			}
		}
		return userName;
	}

}
