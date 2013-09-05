package com.ku6ads.struts.webdata;

import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.webdata.Hotfilm;
import com.ku6ads.services.iface.webdata.HotfilmService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;
import com.opensymphony.xwork2.ActionSupport;

public class HotFilmAction extends ActionSupport {
	private static final long serialVersionUID = 9144713234734971920L;
	private HotfilmService hotfilmService;
	private Logger logger = Logger.getLogger(HotFilmAction.class);
	
	public void search() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Hotfilm searchEty = (Hotfilm) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Hotfilm.class);
			if(limit.getExp_name() != null && !limit.getExp_name().trim().equals("")) {
				List<Hotfilm> list = hotfilmService.selectByEntity(searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), list, "热播剧", limit);
				return;
			}
			
			searchEty.setExtLimit(limit);
			int count = hotfilmService.selectLimitCount(searchEty);
			List<Hotfilm> list = hotfilmService.selectByLimit(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			Hotfilm hotFilmEty = (Hotfilm) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Hotfilm.class);
			if(hotFilmEty.getId() == null) {
				hotfilmService.insert(hotFilmEty);
			}
			else {
				hotfilmService.updateById(hotFilmEty);
			}
			obj.put("result", "success");
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	public void deleteById() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			hotfilmService.deleteHotfilm(Integer.parseInt(id));
			retObj.put("result", "success");
		}
		catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	public void showDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Hotfilm hotFilmEty = (Hotfilm) hotfilmService.selectById(Integer.parseInt(id));
			String s = EntityReflect.getObjectJSonString(hotFilmEty, new SimpleDateFormat("yyyy-MM-dd"));
			retObj.put("data", s);
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public HotfilmService getHotfilmService() {
		return hotfilmService;
	}

	public void setHotfilmService(HotfilmService hotfilmService) {
		this.hotfilmService = hotfilmService;
	}
	
	
}
