package com.ku6ads.struts.sysconfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Group;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.services.iface.sysconfig.GroupService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;

/**
 * 本地用户组action
 * @author xuxianan
 *
 */
public class GroupAction extends BaseAction {

	private static final long serialVersionUID = -5792963629540549654L;
	protected Logger logger = Logger.getLogger(GroupAction.class);
	private GroupService groupService;

	/**
	 * 新增本地用户组
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Group group = (Group) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Group.class);
			if (group.getId() == null) {
				group.setCreateTime(new Date());
				group.setStatus(Group.STATUS_START);
				groupService.insert(group);
			} else {
				groupService.updateById(group);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得用户组列表信息;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					ExtLimit.class);
			Group group = (Group) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Group.class);
			group.setExtLimit(limit);
			int count = groupService.selectLimitCount(group);
			List<Group> groupList = groupService.selectByLimit(group);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), groupList, count, new SimpleDateFormat(
					"yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 删除用户组;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("groupList");
			String[] idList = StringUtils.split(ids, ",");
			for (int i = 0; i < idList.length; i++) {
				List<User> userList = groupService.selectUserByGroupId(Integer.parseInt(idList[i]));
				if (userList.isEmpty()) {
					groupService.deleteById(Integer.parseInt(idList[i]));
					retObj.put("result", "success");
				} else {
					retObj.put("result", "use"); // 用户组使用中
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 获得对应的用户组;
	 */
	public void getGroupDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Group group = (Group) groupService.selectById(Integer.parseInt(id));
			retObj.put("data", group);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 新增用户所属组记录
	 */
	public void insertUserGroup() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			UserGroup userGroup = (UserGroup) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					UserGroup.class);
			groupService.insertUserGroup(userGroup);
			obj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	public void getUserList() {
		try {
			int groupId = Integer.parseInt(ServletActionContext.getRequest().getParameter("id"));
			List<User> userList = groupService.selectUserByGroupId(groupId);
			int userListCount = groupService.selectUserCountByGroupId(groupId);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), userList, userListCount);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 查询可以添加的用户列表
	 */
	public void getAddUserList() {
		try {
			String groupId = ServletActionContext.getRequest().getParameter("id");
			List<User> userList = groupService.selectAddUser(Integer.parseInt(groupId));
			int userCount = groupService.selectUserCountByGroupId(Integer.parseInt(groupId));
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), userList, userCount);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 删除用户组用户对应记录
	 */
	public void deleteUserGroupById() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			groupService.deleteUserGroupById(Integer.parseInt(id));
			retObj.put("result", "success");
		} catch (Exception e) {
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 分配用户组管理员
	 */
	public void specifiedAdmin() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			String groupId = ServletActionContext.getRequest().getParameter("groupId");
			List<UserGroup> groupList = groupService.checkUserGroupAdminById(Integer.parseInt(groupId));
			if (groupList.isEmpty()) {
				groupService.specifiedAdmin(Integer.parseInt(id));
				retObj.put("result", "success");
			} else {
				retObj.put("result", "exist"); // 已经存在
			}
		} catch (Exception e) {
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 取消指定用户管理员授权
	 */
	public void cancelSpecifiedAdmin() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			groupService.cancelSpecifiedAdmin(Integer.parseInt(id));
			retObj.put("result", "success");
		} catch (Exception e) {
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}
