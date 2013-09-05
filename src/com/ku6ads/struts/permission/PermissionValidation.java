package com.ku6ads.struts.permission;

import java.util.List;

import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.services.iface.sysconfig.GroupService;

/**
 * permission util
 * @author xuxianan
 * 
 */
public class PermissionValidation {

	private GroupService groupService;
	private static PermissionValidation missionVld;
	private static final int PERMISSION_ADMIN = 3; // 成员组管理员
	private static final int SUPEP_GROUP = 1;	// 超级管理员组

	public void init() {
		missionVld = this;
		missionVld.groupService = this.groupService;
	}

	/**
	 * 根据当前登录用户名查找所在用户组, 如果存在用户组还是管理员的情况下, 则返回用户组内所有组员的用户名<br>
	 * 如果不是组内管理员的话则查询自己<br>
	 * 
	 * @param userName
	 * @return
	 */
	public static String getVldPermissionStr(String userName) {
		StringBuffer sb = new StringBuffer("creator in (");
		try {
			List<UserGroup> userGroupList = missionVld.groupService.selectUserGroupByUserName(userName);
			sb.append("'" + userName + "',");
			
			/* 循环添加管理员组内的成员 */
			if (!userGroupList.isEmpty()) {
				for (int j = 0; j < userGroupList.size(); j++) {
					UserGroup userGroup = userGroupList.get(j);
					if (userGroup.getFlag() == SUPEP_GROUP)
						return "";
					if (userGroup.getStatus() == PERMISSION_ADMIN) {
						List<UserGroup> userList = missionVld.groupService.selectUserGroupByUserNameNotIn(userGroup);
						for (int i = 0; i < userList.size(); i++) {
							UserGroup userGroupTmp = userList.get(i);
							sb.append("'" + userGroupTmp.getUserName()).append("'");
							if (i < userList.size() - 1)
								sb.append(",");
						}
						if (!sb.toString().endsWith(",")) 
							sb.append(",");
//						if (j < userGroupList.size() - 1)
//							sb.append(",");
					}
				}
			}
			if (sb.toString().endsWith(","))
				sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}
