package com.ku6ads.struts.advproduct;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advproduct.AdvproductAdvbarEty;
import com.ku6ads.dao.iface.advert.AdvbarDao;
import com.ku6ads.dao.iface.advproduct.AdvproductAdvbarDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.opensymphony.xwork2.ActionSupport;

public class AdvproductAdvbarAction extends ActionSupport {

	private static final long serialVersionUID = 5195234992252170807L;
	private Logger logger = Logger.getLogger(AdvproductAdvbarAction.class);
	
	public void search() {
		try {
			AdvproductAdvbarEty advproductAdvbarEty = (AdvproductAdvbarEty) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), AdvproductAdvbarEty.class);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			advproductAdvbarEty.setExtLimit(limit);
			int count = advproductAdvbarDao.selectLimitCount(advproductAdvbarEty);
			List list = advproductAdvbarDao.selectByLimit(advproductAdvbarEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("查询错误！", e);
		}
	}

	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			String advproductId = ServletActionContext.getRequest().getParameter("advproductId");
			String[] advbarArr = ServletActionContext.getRequest().getParameterValues("advbarIds");
			AdvproductAdvbarEty advproductAdvbarEty ;
			for(int i = 0; i < advbarArr.length; i++) {
				advproductAdvbarEty = new AdvproductAdvbarEty();
				advproductAdvbarEty.setAdvbarId(Integer.parseInt(advbarArr[i]));
				advproductAdvbarEty.setAdvproductId(Integer.parseInt(advproductId));
				
				int count = advproductAdvbarDao.selectLimitCount(advproductAdvbarEty);
				if(count >= 1)
					continue;
				else
					advproductAdvbarDao.insert(advproductAdvbarEty);
			}
			obj.put("result","success");		
		}
		catch (Exception e) {
			logger.error("保存错误！", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	public void delete() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			advproductAdvbarDao.deleteById(Integer.parseInt(id));
			obj.put("result","success");
		}
		catch (Exception e) {
			logger.error("删除错误！", e);
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
			AdvproductAdvbarEty advproductAdvbarEty = (AdvproductAdvbarEty) advproductAdvbarDao.selectById(Integer.parseInt(id));
			String dataStr = EntityReflect.getObjectJSonString(advproductAdvbarEty, new SimpleDateFormat("yyyy-MM-dd"));
			obj.put("data", dataStr);
		}
		catch (Exception e) {
			logger.error("错误！", e);
			obj.put("result","error");
			obj.put("info",e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	public void searchAdvbar() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Advbar advbar = (Advbar) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),Advbar.class);
			int count = advbarDao.selectLimitCount(advbar);
			
			advbar.setExtLimit(limit);
			List<Advbar> advbarList = advbarDao.selectByLimit(advbar);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advbarList, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("错误！", e);
		}
	}

	private AdvbarDao advbarDao;
	private AdvproductAdvbarDao advproductAdvbarDao;
	
	public void setAdvproductAdvbarDao(AdvproductAdvbarDao advproductAdvbarDao) {
		this.advproductAdvbarDao = advproductAdvbarDao;
	}
	public AdvproductAdvbarDao getAdvproductAdvbarDao() {
		return this.advproductAdvbarDao;
	}
	public AdvbarDao getAdvbarDao() {
		return advbarDao;
	}

	public void setAdvbarDao(AdvbarDao advbarDao) {
		this.advbarDao = advbarDao;
	}
	
}