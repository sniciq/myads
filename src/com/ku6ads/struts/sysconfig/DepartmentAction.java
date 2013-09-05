package com.ku6ads.struts.sysconfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Department;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.services.iface.sysconfig.DepartmentService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;
import com.opensymphony.xwork2.ActionSupport;

public class DepartmentAction extends ActionSupport {

	private static final long serialVersionUID = 3905159867337538938L;
	private Logger logger = Logger.getLogger(DepartmentAction.class);
	private DepartmentService departmentService;
	
	/**
	 * 新增部门信息;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Department department = (Department) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Department.class);
			String departId = ServletActionContext.getRequest().getParameter("ids");
			if (department.getDepartmentId() == null) {
				department.setCreateDate(new Date());
				department.setStatus(true);
				department.setCreatorId(1);
				department.setParentDepartId(TypeConverterUtil.parseInt(departId));
				departmentService.insert(department);
			} else {
				department.setParentDepartId(TypeConverterUtil.parseInt(departId));
				departmentService.updateById(department);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得部门名称列表;
	 */
	public void getDepartmentList() {
		List<Department> list = departmentService.selectDepartment();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Department department = list.get(i);
			sb.append("['" + department.getDepartmentId() + "','" + department.getDepartmentName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得部门列表全部信息;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Department department = (Department) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Department.class);
			department.setExtLimit(limit);
			int count = departmentService.selectLimitCount(department);
			List<Department> departmentList = departmentService.selectByLimit(department);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), departmentList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获得对应的部门信息;
	 */
	public void getDepartmentDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("departmentId");
			Department department = (Department) departmentService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", department);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除部门记录;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("departmentList");
			String[] idList = StringUtils.split(ids, ",");
			List<User> user = new ArrayList<User>();
			for (int i = 0; i < idList.length; i++) {
					departmentService.deleteById(TypeConverterUtil.parseInt(idList[i]));
			}
		} catch (Exception e) {
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
			//FIXME 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

}
