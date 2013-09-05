package com.ku6ads.struts.webdata;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.webdata.HotProgram;
import com.ku6ads.dao.iface.webdata.HotProgramDao;
import com.opensymphony.xwork2.ActionSupport;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;

public class HotProgramAction  extends ActionSupport {
	private static final long serialVersionUID = 7239951437520555994L;
	private HotProgramDao hotProgramDao;
	private Logger logger = Logger.getLogger(HotProgramAction.class);
	
	public void search() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			HotProgram searchEty = (HotProgram) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), HotProgram.class);
			if(limit.getExp_name() != null && !limit.getExp_name().trim().equals("")) {
				List<HotProgram> list = hotProgramDao.searchByHotProgram(null);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), list, "热播节目", limit);
				return;
			}
			
			searchEty.setExtLimit(limit);
			int count = hotProgramDao.searchByHotProgramCount(searchEty);
			List<HotProgram> list = hotProgramDao.searchByHotProgram(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, null);
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			HotProgram hotP = (HotProgram) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), HotProgram.class);
			if(hotP.getId() == null) {
				hotProgramDao.insertHotProgram(hotP);
			}
			else {
				hotProgramDao.updateHotProgram(hotP);
			}
			obj.put("result", "success");
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	public void showDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			HotProgram hotP = hotProgramDao.selectById(Integer.parseInt(id));
			retObj.put("data", hotP);
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	public void deleteById() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			hotProgramDao.deleteById(Integer.parseInt(id));
			retObj.put("result", "success");
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public HotProgramDao getHotProgramDao() {
		return hotProgramDao;
	}
	public void setHotProgramDao(HotProgramDao hotProgramDao) {
		this.hotProgramDao = hotProgramDao;
	}
}
