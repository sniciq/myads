package com.ku6ads.struts.advflight;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.ClassPathResource;

import com.ku6ads.dao.entity.advflight.AdvActive;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.entity.sysconfig.Email;
import com.ku6ads.services.iface.advflight.AdvActiveService;
import com.ku6ads.services.iface.advflight.AdvbarBookedService;
import com.ku6ads.services.iface.advflight.AdvbarPreBookService;
import com.ku6ads.services.iface.advflight.BookPackageService;
import com.ku6ads.services.iface.advflight.ProjectService;
import com.ku6ads.services.iface.sysconfig.GroupService;
import com.ku6ads.services.iface.sysconfig.RoleService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.BussinessStatus;
import com.ku6ads.util.Dozer;
import com.ku6ads.util.PropertiesUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author chenshaofeng
 *
 */
public class ProjectVerifyAction extends ActionSupport {

	private static final long serialVersionUID = 8796593007295114403L;

	private ProjectService projectService;
	private AdvActiveService advActiveService;
	private RoleService roleService;
	private BookPackageService bookPackageService;
	private AdvbarBookedService advbarBookedService;
	private AdvbarPreBookService advbarPreBookService;
	private GroupService groupService;

	public static final Integer ISDEFAULT = 1;	// 是默认执行单
	
	/**
	 * 待客户确认;
	 */
	public void verifyCustomer() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			/*
			Email mail = new Email();
			roleService.setMailProperty(mail);
			mail.setMessage("有一条执行单需要得到你的审核");
			List<String> roleNameList = roleService.selectEmailList("客户确认人员");
			for (int i = 0; i < roleNameList.size(); i++) {
				String receiver = roleNameList.get(i);
				mail.setReceiver(receiver);
				MailUtil.sentMobileMes(mail);
			}
			*/
			String id = ServletActionContext.getRequest().getParameter("projectId");
			projectService.update(Integer.parseInt(id), BussinessStatus.verify_customer);
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 退回待修改; (一审)
	 */
	public void back() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("projectId");
			/*
			
			
			
			Email mail = getMailProperties();
			mail.setTitle("您有一条执行单审核信息,请查收!");
			mail.setMessage("您有一条执行单被退回修改,执行单名称为 : " + project.getName() + ", 创建时间 : " + 
					DateUtils.getDateToStr(project.getCreateTime()) + ", 创建人 : " + project.getCreator() + 
					", 客户 : " + project.getConsumerName() +
					" 请及时查看并进行修改!");
			
			List<User> mailList = groupService.selectMailGroupPersonnel();
			for (User user : mailList) {
				mail.setReceiver(user.getMail());
				MailUtil.sentMobileMes(mail);
			}
			*/
			/*变更执行单状态*/
			Project project = (Project) projectService.selectById(Integer.parseInt(id));
			if(project.getStatusFlag().intValue() == BussinessStatus.statusFlag_hasExcuted)//加标记回到执行中修改
				projectService.update(Integer.parseInt(id), BussinessStatus.running_update);
			else
				projectService.update(Integer.parseInt(id), BussinessStatus.back);
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 未通过(二审);
	 */
	@SuppressWarnings("unchecked")
	public void verifyNotPass() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			String projectIdStr = ServletActionContext.getRequest().getParameter("projectId");
			int projectId = Integer.parseInt(projectIdStr);
			/*
			Project project = (Project) projectService.selectById(TypeConverterUtil.parseInt(projectId));
			
			Email mail = getMailProperties();
			mail.setTitle("您有一条执行单审核信息,请查收!");
			mail.setMessage("您有一条执行单被退回修改,执行单名称为 : " + project.getName() + ", 创建时间 : " + 
					DateUtils.getDateToStr(project.getCreateTime()) + ", 创建人 : " + project.getCreator() + 
					", 客户 : " + project.getConsumerName() +
					" 请及时查看并进行修改!");
			
			List<User> mailList = groupService.selectMailGroupPersonnel();
			for (User user : mailList) {
				mail.setReceiver(user.getMail());
				MailUtil.sentMobileMes(mail);
			}
			*/
			
			Project project = (Project) projectService.selectById(projectId);
			if(project.getStatusFlag().intValue() == BussinessStatus.statusFlag_hasExcuted) {
				projectService.update(projectId, BussinessStatus.run_over);
			}
			else {
				projectService.update(projectId, BussinessStatus.verify_not_pass);
			}
			
			BookPackage bPackage = new BookPackage();
			bPackage.setProjectId(projectId);
			bPackage.setStatus(0);
			List<BookPackage> bookPackageList = bookPackageService.selectByEntity(bPackage);//得到所有的排期包
			for(int j = 0; j < bookPackageList.size(); j++){
				bookPackageService.deleteBookPackage(bookPackageList.get(j).getId(), project);
			}
			
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 通过;
	 */
	public void verifyPass() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			/*
			Email mail = new Email();
			roleService.setMailProperty(mail);
			mail.setMessage("有一条执行单需要得到你的审核！");
			List<String> roleNameList = roleService.selectEmailList("通过人员");
			for (int i = 0; i < roleNameList.size(); i++) {
				String receiver = roleNameList.get(i);
				mail.setReceiver(receiver);
				MailUtil.sentMobileMes(mail);
			}
			*/
			String id = ServletActionContext.getRequest().getParameter("projectId");
			projectService.update(Integer.parseInt(id), BussinessStatus.running);
			Project project = projectService.selectById(Integer.parseInt(id));
			//查询是否已经有相应的广告活动
			AdvActive advAc = new AdvActive();
			advAc.setName(project.getName() + "广告活动");
			List advList = advActiveService.selectByName(advAc);
			//生成一个与该执行单对应的广告活动
			if (advList.size() == 0) {
				AdvActive advActive = (AdvActive) Dozer.getInstance().mapper(project, AdvActive.class);
				advActive.setProjectId(project.getId());
				advActive.setProjectName(project.getName());
				advActive.setStatus(0);
				advActive.setName(project.getName() + "广告活动");
				advActive.setId(null);
				advActiveService.insert(advActive);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
		}
		//将排期数据推到投放表中
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 提交审核
	 */
	public void submit() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			/*
			Email mail = new Email();
			roleService.setMailProperty(mail);
			mail.setMessage("有一条执行单需要得到你的审核！");
			List<String> roleNameList = roleService.selectEmailList("二审人员");
			for (int i = 0; i < roleNameList.size(); i++) {
				String receiver = roleNameList.get(i);
				mail.setReceiver(receiver);
				MailUtil.sentMobileMes(mail);
			}
			*/
			String id = ServletActionContext.getRequest().getParameter("projectId");
			Project project = projectService.selectById(Integer.parseInt(id));
			Integer bussinessStatus = project.getBussinessStatus();
			Integer isDefault = project.getIsDefault();
			if (isDefault == BussinessStatus.project_default) {
				bussinessStatus = BussinessStatus.running;
			} else {
				if (bussinessStatus == BussinessStatus.draft || bussinessStatus == BussinessStatus.verify_customer) {
					bussinessStatus++;
				} else
					bussinessStatus = BussinessStatus.verify_first;
			}
			projectService.update(Integer.parseInt(id), bussinessStatus);
			obj.put("result", "success");
		} catch (Exception e) {
			obj.put("result", "error");
			obj.put("info", e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**
	 * 获得mail常用配置 如: 编码\smtp\发送地址\发送pwd. 未获取的有: title\message\收件人
	 * @return
	 */
	public static Email getMailProperties() {
		Email mail = new Email();
		try {
			// 1. 初始化
			PropertiesUtils.load(new ClassPathResource("mail.properties"));
			String charset = PropertiesUtils.getValue("charset");
			String smtp = PropertiesUtils.getValue("smtp");
			String from = PropertiesUtils.getValue("from");
			String pwd = PropertiesUtils.getValue("pwd");
			
			mail.setCharSet(charset);	// 编码
			mail.setHostName(smtp);	// smtp server
			mail.setSender(from);	// from
			mail.setPassword(pwd);	// pwd
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mail;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public AdvActiveService getAdvActiveService() {
		return advActiveService;
	}

	public void setAdvActiveService(AdvActiveService advActiveService) {
		this.advActiveService = advActiveService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public BookPackageService getBookPackageService() {
		return bookPackageService;
	}

	public void setBookPackageService(BookPackageService bookPackageService) {
		this.bookPackageService = bookPackageService;
	}

	public AdvbarBookedService getAdvbarBookedService() {
		return advbarBookedService;
	}

	public void setAdvbarBookedService(AdvbarBookedService advbarBookedService) {
		this.advbarBookedService = advbarBookedService;
	}

	public AdvbarPreBookService getAdvbarPreBookService() {
		return advbarPreBookService;
	}

	public void setAdvbarPreBookService(AdvbarPreBookService advbarPreBookService) {
		this.advbarPreBookService = advbarPreBookService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}
