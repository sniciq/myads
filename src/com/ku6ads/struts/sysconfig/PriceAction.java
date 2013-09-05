package com.ku6ads.struts.sysconfig;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.PriceEty;
import com.ku6ads.dao.iface.sysconfig.PriceDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.opensymphony.xwork2.ActionSupport;

public class PriceAction extends ActionSupport {

	private static final long serialVersionUID = -7645673664342487954L;
	public void search() {
		try {
			PriceEty priceEty = (PriceEty) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), PriceEty.class);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			priceEty.setExtLimit(limit);
			int count = priceDao.selectLimitCount(priceEty);
			List list = priceDao.selectByLimit(priceEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			PriceEty priceEty = (PriceEty) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), PriceEty.class);
			if(priceEty.getId() == null) {
				priceDao.insert(priceEty);
			} else { 
				priceDao.updateById(priceEty);
			}
			obj.put("result","success");		}
		catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	public void delete() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			priceDao.deleteById(Integer.parseInt(id));
			obj.put("result","success");
		}
		catch (Exception e) {
			e.printStackTrace();
			obj.put("result","error");
			obj.put("info",e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	public void getDetailInfo() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			PriceEty priceEty = (PriceEty) priceDao.selectById(Integer.parseInt(id));
			String dataStr = EntityReflect.getObjectJSonString(priceEty, new SimpleDateFormat("yyyy-MM-dd"));
			obj.put("data", dataStr);
		}
		catch (Exception e) {
			e.printStackTrace();
			obj.put("result","error");
			obj.put("info",e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	private PriceDao priceDao;
	public void setPriceDao(PriceDao priceDao) {
		this.priceDao = priceDao;
	}
	public PriceDao getPriceDao() {
		return this.priceDao;
	}
}