package com.ku6ads.struts.sysconfig;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.interceptor.auth.Role;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.struts.flowforecast.FlowForecastTask;
import com.ku6ads.util.AjaxOut;

public class LoginLocalAction extends BaseAction {
	
	private static final long serialVersionUID = -6702829355609430659L;
	
	private FlowForecastTask flowForecastTask;

	public void login() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		
		UserInfoEty userEty = new UserInfoEty();
		userEty.setUserId(13);
		userEty.setUsername("yanghanguang@ku6.com");
		userEty.setRealname("杨汉光");
		
		List<Role> roleList = new ArrayList<Role>();
		
		ServletActionContext.getRequest().getSession().setAttribute("UserEty", userEty);
		ServletActionContext.getRequest().getSession().setAttribute("UserRole", roleList);
		obj.put("result", "success");
		
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**
	 * 注销登陆
	 */
	public void logout() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			ServletActionContext.getRequest().getSession().removeAttribute("UserEty");
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	public void doTask() {
		flowForecastTask.dotask();
		AjaxOut.responseText(ServletActionContext.getResponse(), "完成");
	}

	public FlowForecastTask getFlowForecastTask() {
		return flowForecastTask;
	}

	public void setFlowForecastTask(FlowForecastTask flowForecastTask) {
		this.flowForecastTask = flowForecastTask;
	}
	
}
