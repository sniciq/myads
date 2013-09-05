package com.ku6ads.struts.sysconfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Role;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserRole;
import com.ku6ads.services.iface.sysconfig.RoleService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

public class RoleAction extends BaseAction{

	private static final long serialVersionUID = -8174013275550144896L;
	private Logger logger = Logger.getLogger(RoleAction.class);
	private RoleService roleService;

	/**
	 * 新增角色,成功或失败则返回json信息;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Role role = (Role) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Role.class);
			if (role.getId() == null) {
				role.setCreateTime(new Date());
				role.setStatus(0);
				Role tRole = new Role();
				tRole.setStatus(0);
				tRole.setName(role.getName());
				List<Role> roleList = roleService.selectByEntity(tRole);
				if(roleList==null||roleList.size()==0)
				{
					roleService.insert(role);
					obj.put("result", "success");
				}else{
					obj.put("result", "exist");
				}
			} else {
				roleService.updateById(role);
				obj.put("result", "success");
			}
		} catch (Exception e) {
			logger.error("", e);
			obj.put("result", "false");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 查询用户列表,按照json格式返回;
	 */
	public void getUserList() {
		try {
			Integer roleId = Integer.parseInt(ServletActionContext.getRequest().getParameter("id"));
			List<UserRole> userList = roleService.selectUserByRoleId(roleId);
			Integer userListCount = roleService.selectUserCountByRoleId(roleId);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), userList, userListCount);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 查询可以添加的用户列表,按照json格式返回;
	 */
	public void getUserAddList() {
		try {
			Integer roleId = Integer.parseInt(ServletActionContext.getRequest().getParameter("id"));
			List<User> userList = roleService.selectUserAdd(roleId);
			Integer userListCount = roleService.selectUserAddCount(roleId);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), userList, userListCount);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	/**
	 * 查询角色信息,返回json格式;
	 */
	@SuppressWarnings("unchecked")
	public void getRoleInfo() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Role role = (Role) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Role.class);
			role.setExtLimit(limit);
			role.setStatus(0);
			int count = roleService.selectLimitCount(role);
			List<Role> roleList = roleService.selectByLimit(role);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), roleList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获取对应角色详细信息;
	 */
	public void getRoleDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Role role = (Role) roleService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", role);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 根据页面传递角色id集合,删除对应的角色;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("roleList");
			String[] idList = ids.split(",");
			List<UserRole> userList = new ArrayList<UserRole>();
			for (int i = 0; i < idList.length; i++) {
				userList = roleService.selectUserByRoleId(TypeConverterUtil.parseInt(idList[i]));
				if (userList.isEmpty()) {
					roleService.deleteById(TypeConverterUtil.parseInt(idList[i]));
					retObj.put("result", "success");
				} else {
					retObj.put("result", "use");
					continue;
				}
			}
		} catch (Exception e) {
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 为角色添加相应的用户
	 */
	public void insertUserByRole(){
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			UserRole userRole = (UserRole) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), UserRole.class);
			userRole.setStatus(0);
			UserRole ur = new UserRole();
			ur.setStatus(0);
			ur.setUsername(userRole.getUsername());
			ur.setRoleId(userRole.getRoleId());
			List<UserRole> uRoleList = roleService.selectUserRoleByName(ur);
			if(uRoleList.size() > 0) 
				retObj.put("result", "exist");
			else{
				roleService.insertUserByRole(userRole);
				retObj.put("result", "success");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 为角色删除相应的用户
	 */
	public void deleteUserByRole(){
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			UserRole userRole = (UserRole) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), UserRole.class);
			roleService.deleteUserByRole(userRole);
			retObj.put("result", "success");
		}catch(Exception e)
		{
			e.printStackTrace();
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	public void getUserInfoList() {
		try {
			String roleName = java.net.URLDecoder.decode(ServletActionContext.getRequest().getParameter("roleName"),"UTF-8");
			List<UserRole> userRoleList = roleService.selectByRoleName(roleName);
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < userRoleList.size(); i++) {
				UserRole userRole = userRoleList.get(i);
				sb.append("['" + userRole.getUserId() + "','" + userRole.getUsername() + "']");
				if (i < userRoleList.size() - 1)
					sb.append(",");
			}
			sb.append("]");
			AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
