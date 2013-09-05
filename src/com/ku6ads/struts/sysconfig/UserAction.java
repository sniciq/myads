package com.ku6ads.struts.sysconfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.ClassPathResource;

import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.entity.advflight.ProjectCount;
import com.ku6ads.dao.entity.sysconfig.Menu;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.dao.entity.sysconfig.UserRole;
import com.ku6ads.dao.iface.sysconfig.MenuDao;
import com.ku6ads.interceptor.auth.AuthUtils;
import com.ku6ads.interceptor.auth.Role;
import com.ku6ads.services.iface.advflight.ProjectService;
import com.ku6ads.services.iface.sysconfig.UserService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.struts.permission.PermissionValidation;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.HttpUtils;
import com.ku6ads.util.MD5Encode;
import com.ku6ads.util.PropertiesUtils;
import com.ku6ads.util.RSA;
import com.ku6ads.util.TypeConverterUtil;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = -5856387514983667467L;
	private Logger logger = Logger.getLogger(UserAction.class);
	private UserService userService;
	private ProjectService projectSerivce;
	private MenuDao menuDao;

	/**
	 * 新增用户,成功或失败则返回json信息;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			User user = (User) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), User.class);
			if (user.getId() == null) {
				user.setCreateTime((new Date()));
				user.setStatus(0);
				userService.insert(user);
			} else {
				userService.updateById(user);
				UserRole userRole = new UserRole();
				userRole.setUserId(user.getId());
				userRole.setUsername(user.getUsername());
				userService.updateByUserId(userRole);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
			obj.put("result", "err");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 查询用户信息,返回json格式;
	 */
	@SuppressWarnings("unchecked")
	public void getUserInfo() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					ExtLimit.class);
			User user = (User) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), User.class);
			user.setStatus(0);
			user.setExtLimit(limit);
			int count = userService.selectLimitCount(user);
			List<Role> roleList = userService.selectByLimit(user);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), roleList, count, new SimpleDateFormat(
					"yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获取对应用户详细信息;
	 */
	public void getUserDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			User user = (User) userService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", user);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除用户信息
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("userList");
			String[] idList = ids.split(",");
			@SuppressWarnings("unused")
			List<UserRole> userList = new ArrayList<UserRole>();
			for (int i = 0; i < idList.length; i++) {
				userService.deleteById(TypeConverterUtil.parseInt(idList[i]));
				userService.deleteByUserId(TypeConverterUtil.parseInt(idList[i]));
				retObj.put("result", "success");
			}
		} catch (Exception e) {
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 获取用户一级菜单
	 */
	public void getUserResourcesMenu() {
		try {
			String node = ServletActionContext.getRequest().getParameter("node");
			JSONArray array = new JSONArray();
			if (node == null || node.equals("0")) {
				List<Menu> list = menuDao.selectUserMenu(this.getLoginUser().getUserId());
				Menu menu = null;
				for (int i = 0; i < list.size(); i++) {
					menu = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", menu.getNodeId());
					obj.put("text", menu.getName());
					obj.put("leaf", false);
					array.add(obj);
				}
			}
			AjaxOut.responseText(ServletActionContext.getResponse(), array.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 根据用户一级菜单id获取用户二级菜单
	 */
	public void getUserResourcesTreeNode() {

		String PROJECT_MANAGEMENT = "排期管理";

		try {
			String node = ServletActionContext.getRequest().getParameter("node");
			String menuId = ServletActionContext.getRequest().getParameter("menuId");
			String menuName = ServletActionContext.getRequest().getParameter("menuName");

			JSONArray array = new JSONArray();

			if (node.equals("0") && menuId != null && !menuId.equals("")) {
				node = menuId;
			}

			if (null != menuName && menuName.equals(PROJECT_MANAGEMENT)) {
				Project project = new Project();
				
				ExtLimit limit = new ExtLimit();
				limit.setPermissionstr(PermissionValidation.getVldPermissionStr(this.getLoginUser().getUsername()));
				project.setExtLimit(limit);
				List<ProjectCount> projectCountList = projectSerivce.statisticProjectCount(project);

				Menu menu1 = new Menu();
				menu1.setNodeId(Integer.parseInt(node));
				menu1.setUserId(this.getLoginUser().getUserId());

				List<Menu> list = menuDao.selectMenuByPnodeId(menu1);
				Menu menu = null;
				for (int i = 0; i < list.size(); i++) {
					menu = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", menu.getNodeId());
					obj.put("text", getMenuName(menu.getName(), projectCountList));
					obj.put("url", menu.getUrl());
					obj.put("leaf", (menu.getUrl() == null ? false : true));
					array.add(obj);
				}
			} else {

				Menu menu1 = new Menu();
				menu1.setNodeId(Integer.parseInt(node));
				menu1.setUserId(this.getLoginUser().getUserId());

				List<Menu> list = menuDao.selectMenuByPnodeId(menu1);
				Menu menu = null;
				for (int i = 0; i < list.size(); i++) {
					menu = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", menu.getNodeId());
					obj.put("text", menu.getName());
					obj.put("url", menu.getUrl());
					obj.put("leaf", (menu.getUrl() == null ? false : true));
					array.add(obj);
				}
			}
			AjaxOut.responseText(ServletActionContext.getResponse(), array.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 
	 * 根据menuName和状态值查询对应的结果集
	 * 
	 * @param menuName
	 * @param projectList
	 * @return
	 */
	private String getMenuName(String menuName, List<ProjectCount> projectList) {

		String DRAFT = "草稿项目单";
		String BACK = "退回项目单";
		String VERIFY_FIRST = "待一审项目单";
		String VERIFY_SECOND = "待二审项目单";
		String VERIFY_CUSTOMER = "客户待确认项目单";
		String VERIFY_NOT_PASS = "未通过项目单";

		int draft = 0;
		int back = 4;
		int verify_first = 1;
		int verify_second = 3;
		int verify_customer = 2;
		int verify_not_pass = 5;

		String result = "";

		try {
			ProjectCount projectCount = null;
			for (int i = 0; i < projectList.size(); i++) {
				projectCount = projectList.get(i);
				if (menuName.equals(DRAFT)) {
					if (projectCount.getBussinessStatus() == draft)
						result = "(<font color='red'>" + projectCount.getNum() + "</font>)";
				} else if (menuName.equals(BACK)) {
					if (projectCount.getBussinessStatus() == back)
						result = "(<font color='red'>" + projectCount.getNum() + "</font>)";
				} else if (menuName.equals(VERIFY_FIRST)) {
					if (projectCount.getBussinessStatus() == verify_first)
						result = "(<font color='red'>" + projectCount.getNum() + "</font>)";
				} else if (menuName.equals(VERIFY_SECOND)) {
					if (projectCount.getBussinessStatus() == verify_second)
						result = "(<font color='red'>" + projectCount.getNum() + "</font>)";
				} else if (menuName.equals(VERIFY_CUSTOMER)) {
					if (projectCount.getBussinessStatus() == verify_customer)
						result = "(<font color='red'>" + projectCount.getNum() + "</font>)";
				} else if (menuName.equals(VERIFY_NOT_PASS)) {
					if (projectCount.getBussinessStatus() == verify_not_pass)
						result = "(<font color='red'>" + projectCount.getNum() + "</font>)";
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		return menuName + result;
	}

	/**
	 * 用户登录
	 * 
	 * 流程: 得到用户名和密码后按照文档约束带相应的条件调用权限接口验证用户,成功或失败均返回json
	 * 
	 * 登陆成功以后根据用户的token和相关参数调用权限接口中的菜单接口,返回用户相应的权限菜单;
	 */
	public void login() {

		JSONObject obj = new JSONObject();
		obj.put("success", true);

		try {
			// 1. 初始化配置信息
			PropertiesUtils.load(new ClassPathResource("auth.properties"));
			String url = PropertiesUtils.getValue("AUTH_REQUEST_URL");
			String appid = PropertiesUtils.getValue("APPID");
			String publicKey = PropertiesUtils.getValue("PUBLIC_KEY");
			String secretKey = PropertiesUtils.getValue("SECRET_KEY");

			String username = ServletActionContext.getRequest().getParameter("username");
			String password = ServletActionContext.getRequest().getParameter("password");
			String pwd = RSA.encryptBASE64(RSA.encryptByPublicKey(password.getBytes(), publicKey));
			String path = url + "/api/access?t=login";
			String callid = System.currentTimeMillis() + "";

			// 2. 构建参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", username);
			params.put("password", pwd);
			params.put("appid", appid);
			params.put("callid", callid);

			// 3. 请求登陆
			String result = HttpUtils.post(path, params);
			JSONObject json = JSONObject.fromObject(result);

			String LOGIN_SUCCESS = "1"; // 登陆成功
			String LOGIN_ERROR = "-1"; // 登陆失败

			String loginStatus = json.getString("status");
			if (loginStatus.equals(LOGIN_SUCCESS)) {

				UserInfoEty userEty = new UserInfoEty();
				userEty.setTokenId(json.getJSONObject("data").getString("tokenId"));
				userEty.setUsername(json.getJSONObject("data").getJSONObject("user").getString("username"));
				userEty.setRealname(json.getJSONObject("data").getJSONObject("user").getString("realname"));
				userEty.setDepartment(json.getJSONObject("data").getJSONObject("user").getString("department"));
				userEty.setJob(json.getJSONObject("data").getJSONObject("user").getString("job"));
				userEty.setUserId(TypeConverterUtil.parseInt(json.getJSONObject("data").getJSONObject("user")
						.getString("id")));

				// 4. 请求用户权限菜单
				String getTreePath = url + "/api/auth?t=getUserAppMenuTree";
				String sig = MD5Encode.MD5Encode(appid + "_" + callid + "_" + userEty.getTokenId() + "_" + secretKey);

				Map<String, String> getTreeParams = new HashMap<String, String>();
				getTreeParams.put("appid", appid);
				getTreeParams.put("callid", callid);
				getTreeParams.put("tokenid", userEty.getTokenId());
				getTreeParams.put("sig", sig);

				String treeResult = HttpUtils.post(getTreePath, getTreeParams);

				menuDao.deleteById(userEty.getUserId());

				JSONArray arr = AuthUtils.getMenulist(treeResult); // 解析json
				List<Role> roleList = AuthUtils.getUserRole(treeResult); // 解析json获取用户角色列表
				List<Menu> list = this.getMenuList(arr, userEty.getUserId()); // 解析菜单返回列表

				for (Menu menu : list) {
					menuDao.insert(menu);
				}

				ServletActionContext.getRequest().getSession().setAttribute("UserEty", userEty);
				ServletActionContext.getRequest().getSession().setAttribute("UserRole", roleList);

				obj.put("result", "success");
			} else if (loginStatus.equals(LOGIN_ERROR)) {
				obj.put("result", "用户名或密码错误,请检查用户名或密码!");
			}
		} catch (Exception e) {
			obj.put("result", "登录繁忙,请稍后再试!");
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 本地user表登陆(供本地测试使用),使用的时候记得更改baseaction中的validation函数
	 */
	public void localLogin() {

		JSONObject obj = new JSONObject();
		obj.put("success", true);

		try {
			String username = ServletActionContext.getRequest().getParameter("username").trim();
			String password = ServletActionContext.getRequest().getParameter("password").trim();
			User user = userService.login(username, password);
			if (user == null) {
				obj.put("result", "用户名或密码错误,请检查用户名或密码!");
			} else {
				// 获得用户角色组列表
				List<UserGroup> groupList = userService.getUserGroup(user.getUsername());

				ServletActionContext.getRequest().getSession().setAttribute("UserEty", user);
				ServletActionContext.getRequest().getSession().setAttribute("UserGroup", groupList);
				obj.put("result", "success");
			}
		} catch (Exception e) {
			obj.put("result", "登录繁忙,请稍后再试!");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 注销登陆
	 */
	public void logout() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			// 1. 初始化配置
			PropertiesUtils.load(new ClassPathResource("auth.properties"));
			String url = PropertiesUtils.getValue("AUTH_REQUEST_URL");
			String logoutPath = url + "/api/access?t=logout";

			// 2. 参数
			Map<String, String> logoutParams = new HashMap<String, String>();
			logoutParams.put("tokenid", this.getLoginUser().getTokenId());

			// 3. 发送请求
			HttpUtils.post(logoutPath, logoutParams);

			ServletActionContext.getRequest().getSession().removeAttribute("UserEty");
			obj.put("result", "success");
		} catch (Exception e) {
			obj.put("result", "系统忙,请稍后再试!");
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 
	 * 解析用户菜单json,拼接菜单对象,返回菜单列表
	 * 
	 * @param arr json
	 * @param userId 用户id
	 * @return
	 */
	private List<Menu> getMenuList(JSONArray arr, int userId) {
		List<Menu> list = new ArrayList<Menu>();
		try {
			for (Object object : arr) {
				Menu menu = new Menu();
				JSONObject tableJson = JSONObject.fromObject(object);
				menu.setNodeId(Integer.parseInt(tableJson.getString("id")));
				menu.setPnodeId(0);
				menu.setName(tableJson.getString("name"));
				menu.setUserId(userId);
				Object[] childTableObj = AuthUtils.getDTOArray(tableJson.getString("childTable"));
				if (childTableObj != null) {
					for (Object object1 : childTableObj) {
						Menu menu1 = new Menu();
						JSONObject module = JSONObject.fromObject(object1);
						menu1.setNodeId(Integer.parseInt(module.getString("id")));
						menu1.setPnodeId(menu.getNodeId());
						menu1.setName(module.getString("name"));
						menu1.setUserId(userId);
						Object[] modulesObj = AuthUtils.getDTOArray(module.getString("modules"));
						if (modulesObj != null) {
							for (Object object2 : modulesObj) {
								Menu menu2 = new Menu();
								JSONObject module1 = JSONObject.fromObject(object2);
								menu2.setNodeId(Integer.parseInt(module1.getString("id")));
								menu2.setPnodeId(menu1.getNodeId());
								menu2.setName(module1.getString("name"));
								menu2.setUrl(module1.getString("url"));
								menu2.setUserId(userId);
								list.add(menu2);
							}
						}
						list.add(menu1);
					}
				}
				Object[] modulesObj = AuthUtils.getDTOArray(tableJson.getString("modules"));
				if (modulesObj != null) {
					for (Object object3 : modulesObj) {
						Menu menu3 = new Menu();
						JSONObject module = JSONObject.fromObject(object3);
						menu3.setNodeId(Integer.parseInt(module.getString("id")));
						menu3.setPnodeId(menu.getNodeId());
						menu3.setName(module.getString("name"));
						menu3.setUrl(module.getString("url"));
						menu3.setUserId(userId);
						list.add(menu3);
					}
				}
				list.add(menu);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return list;
	}

	/*******************************GETTER/SETTER************************************/
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public ProjectService getProjectSerivce() {
		return projectSerivce;
	}

	public void setProjectSerivce(ProjectService projectSerivce) {
		this.projectSerivce = projectSerivce;
	}

}
