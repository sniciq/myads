package com.ku6ads.struts.advflight;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.entity.advflight.ProjectCount;
import com.ku6ads.dao.entity.advflight.ProjectReason;
import com.ku6ads.dao.entity.sysconfig.Advertiser;
import com.ku6ads.dao.entity.sysconfig.Consumer;
import com.ku6ads.dao.entity.sysconfig.Product;
import com.ku6ads.dao.entity.sysconfig.ProductLine;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advflight.AdvActiveService;
import com.ku6ads.services.iface.advflight.BookPackageService;
import com.ku6ads.services.iface.advflight.ProjectService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.struts.permission.PermissionValidation;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.BussinessStatus;
import com.ku6ads.util.DateUtils;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 执行单Action
 * @author xuxianan
 *
 */
public class ProjectAction extends BaseAction {

	private static final long serialVersionUID = -6640082664031460281L;
	private Logger logger = Logger.getLogger(ProjectAction.class);
	private ProjectService projectService;
	private AdvActiveService advActiveService;
	private BookPackageService bookPackageService;

	/**
	 * 新增执行单
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Project project = (Project) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Project.class);
			UserInfoEty user = this.getLoginUser();
			if (project.getId() == null) {
				project.setBussinessStatus(BussinessStatus.draft);	// 0为草稿
				project.setCreator(user.getUsername());
				project.setCreateTime(new Date());
				projectService.insert(project);
			} else {
				project.setModifier(user.getUsername());
				project.setModifyTime(new Date());
				projectService.updateById(project);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得执行单列表(首页分页显示列表)
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Project project = (Project) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Project.class);
			String bussinessStatus = ServletActionContext.getRequest().getParameter("p");
			String isDefault = ServletActionContext.getRequest().getParameter("d");	// isDefault
			if (bussinessStatus != null) {
				project.setBussinessStatus(TypeConverterUtil.parseInt(bussinessStatus));
			}
			project.setIsDefault(null == isDefault ? 0 : 1);
			limit.setPermissionstr(PermissionValidation.getVldPermissionStr(this.getLoginUser().getUsername()));
			project.setExtLimit(limit);
			int count = projectService.selectLimitCount(project);
			List<Project> advpositionList = projectService.selectByLimit(project);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advpositionList, count, new SimpleDateFormat(
					"yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获得执行单详细信息
	 */
	public void getProjectDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Project project = (Project) projectService.selectById(TypeConverterUtil.parseInt(id));
			String s = EntityReflect.getObjectJSonString(project, new SimpleDateFormat("yyyy-MM-dd"));
			retObj.put("data", s);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	
	/**
	 * 删除执行单;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			Integer projectId = Integer.parseInt(ServletActionContext.getRequest().getParameter("projectId"));
			boolean flag = projectService.deleteProject(projectId);
			if (flag)
				retObj.put("result", "success");
			else
				retObj.put("result", "exist");
		} catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 获得客户列表;
	 */
	public void getConsumerList() {
		List<Consumer> list = projectService.selectConsumer();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Consumer consumer = list.get(i);
			sb.append("['" + consumer.getConsumerId() + "','" + consumer.getConsumerName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	/**
	 * 获得广告主列表;
	 */
	public void getAdvertiserList() {
		List<Advertiser> list = projectService.selectAdvertiser();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Advertiser advertiser = list.get(i);
			sb.append("['" + advertiser.getId() + "','" + advertiser.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	/**
	 * 获得产品列表;
	 */
	public void getProductList() {
		List<Product> list = projectService.selectProduct();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Product product = list.get(i);
			sb.append("['" + product.getProductId() + "','" + product.getProductName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	/**
	 * 获得产品线列表;
	 */
	public void getProductLineList() {
		List<ProductLine> list = projectService.selectProductLine();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			ProductLine productLine = list.get(i);
			sb.append("['" + productLine.getProductLineId() + "','" + productLine.getProductLineName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	/**
	 * 获得执行单列表;
	 */
	public void getProjectList() {
		//FIXME 杨汉光
		List<Project> list = projectService.selectProjectList();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Project project = list.get(i);
			sb.append("['" + project.getId() + "','" + project.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	/**
	 * 统计排期相应状态记录数
	 */
	public void statisticProjectCount() {
		Project project = new Project();
		project.setCreator(this.validationUserGroup());
		List<ProjectCount> list = projectService.statisticProjectCount(project);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			ProjectCount projectCount = list.get(i);
			sb.append("['" + projectCount.getBussinessStatus() + "','" + projectCount.getNum() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
		
	/**
	 * save执行单审核原因
	 */
	public void saveProjectVerifyReason() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			ProjectReason projectReason = (ProjectReason) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ProjectReason.class);
			ProjectReason projectReasonTemp = projectService.selectProjectReasonById(projectReason.getProjectId());
			
			UserInfoEty user = this.getLoginUser();
			projectReason.setCreateTime(new Date());
			projectReason.setCreator(user.getUsername());
			if (projectReasonTemp == null) {
				projectService.saveProjectReason(projectReason);
			} else {
				String data = EntityReflect.getObjectJSonString(projectReasonTemp, new SimpleDateFormat("yyyy-MM-dd"));
				obj.put("data", data);
				
				projectReasonTemp.setCreateTime(new Date());
				projectReasonTemp.setCreator(user.getUsername());
				
				projectReasonTemp.setReason(projectReasonTemp.getReason() + 
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
						obj.getJSONObject("data").getString("createTime") + 
						"<br>" + "------------------------------------------<br>" + projectReason.getReason() +
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + DateUtils.getDateToStr(new Date()));
				
				projectService.updateProjectReason(projectReasonTemp);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			obj.put("result", "error");
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**
	 * 获得执行单审核失败原因
	 */
	public void getProjectVerifyReasonDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String projectId = ServletActionContext.getRequest().getParameter("projectId");
			ProjectReason projectReason = (ProjectReason) projectService.selectProjectReasonById(TypeConverterUtil.parseInt(projectId));

			String s = "";
			if(projectReason != null)
				s = EntityReflect.getObjectJSonString(projectReason, new SimpleDateFormat("yyyy-MM-dd"));
			retObj.put("data", s);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
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

	public BookPackageService getBookPackageService() {
		return bookPackageService;
	}

	public void setBookPackageService(BookPackageService bookPackageService) {
		this.bookPackageService = bookPackageService;
	}
}
