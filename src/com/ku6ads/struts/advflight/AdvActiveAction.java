package com.ku6ads.struts.advflight;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.ku6ads.dao.entity.advflight.AdvActive;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advflight.AdvActiveService;
import com.ku6ads.services.iface.advflight.ProjectService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 广告活动
 * @author liujunshi
 *
 */
public class AdvActiveAction extends BaseAction{
	
	AdvActiveService advActiveService;
	ProjectService projectService;
	private Logger logger = Logger.getLogger(AdvActiveAction.class);
	/**
	 * 新增广告活动
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			AdvActive advActive = (AdvActive) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),AdvActive.class);
			UserInfoEty user = this.getLoginUser();
			if (advActive.getId() == null) {
				//two project have same property name;
				
				Project project = (Project)projectService.selectById(advActive.getProjectId());
				if(project!=null){
					String advActiveName = advActive.getName();
					String projectName = project.getName();
					//使用springBenaUtil进行对象赋值
					BeanUtils.copyProperties(project, advActive);
					//赋其他值	
					advActive.setName(advActiveName);
					advActive.setProjectName(projectName);
				}
				advActive.setStatus(AdvActive.STATUS_USE);
				advActive.setCreator(user.getUsername());
				advActive.setCreateTime(new Date());
				advActiveService.insert(advActive);
			} else {
				advActive.setModifier(user.getUsername());
				advActive.setModifyTime(new Date());
				advActiveService.updateById(advActive);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得广告活动列表(首页分页显示列表)
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			AdvActive advActive = (AdvActive) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), AdvActive.class);
			advActive.setExtLimit(limit);
			int count = advActiveService.selectLimitCount(advActive);
			List<AdvActive> advActiveList = advActiveService.selectByLimit(advActive);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advActiveList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获得广告活动详细信息
	 */
	public void getProjectDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Project advActive = (Project) advActiveService.selectById(TypeConverterUtil.parseInt(id));
			String s = EntityReflect.getObjectJSonString(advActive, new SimpleDateFormat("yyyy-MM-dd"));
			retObj.put("data", s);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 获得广告活动列表
	 */
	public void getAdvactiveList() {
		List<AdvActive> advActiveList = advActiveService.selectAdvactive();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < advActiveList.size(); i++) {
			AdvActive advActive = advActiveList.get(i);
			sb.append("['" + advActive.getId() + "','" + advActive.getName() + "']");
			if (i < advActiveList.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 删除广告活动;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("advActiveList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				advActiveService.deleteById(TypeConverterUtil.parseInt(idList[i]));
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}




	//++++++++++++++++GETTER SETTER++++++++++++++++++//
	public AdvActiveService getAdvActiveService() {
		return advActiveService;
	}

	public void setAdvActiveService(AdvActiveService advActiveService) {
		this.advActiveService = advActiveService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
}
