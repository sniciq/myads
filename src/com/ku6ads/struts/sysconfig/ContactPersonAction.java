package com.ku6ads.struts.sysconfig;

import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.ContactPerson;
import com.ku6ads.dao.iface.sysconfig.ContactPersonDao;
import com.opensymphony.xwork2.ActionSupport;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

public class ContactPersonAction extends ActionSupport {

	private static final long serialVersionUID = 3905159867337538938L;
	private ContactPersonDao contactPersonDao;

	private Logger logger = Logger.getLogger(ContactPersonAction.class);
	
	/**
	 * 新增联系人;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			
			ContactPerson contactPerson = (ContactPerson) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ContactPerson.class);
			if (contactPerson.getContactPersonId() == null || contactPerson.getContactPersonId() == 0) {
				contactPersonDao.insertContactPerson(contactPerson);
			} else {
				contactPersonDao.updateContactPerson(contactPerson);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
			logger.error(e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 显示全部
	 */
	public void showAll() {
		
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			ContactPerson contactPerson = (ContactPerson) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ContactPerson.class);
			List<ContactPerson> list = contactPersonDao.selectContactPersonByType(contactPerson);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, 20, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error(e);
		}
	}

	/**
	 * 删除记录;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("contactPersonId");
			contactPersonDao.deleteById(TypeConverterUtil.parseInt(id));
			retObj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
			
			logger.error(e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public ContactPersonDao getContactPersonDao() {
		return contactPersonDao;
	}

	public void setContactPersonDao(ContactPersonDao contactPersonDao) {
		this.contactPersonDao = contactPersonDao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
