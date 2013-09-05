package com.ku6ads.struts.advproduct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advproduct.AdvproductEty;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.dao.iface.advproduct.AdvproductDao;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;

public class AdvproductAction extends BaseAction {

	private static final long serialVersionUID = -4570409929577852352L;
	private Logger logger = Logger.getLogger(AdvproductAction.class);
	
	public void searchByName() {
		try {
			String advproductName = ServletActionContext.getRequest().getParameter("advproductName");
			AdvproductEty advproductEty = new AdvproductEty();
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			advproductEty.setExtLimit(limit);
			advproductEty.setAdvproductName(advproductName);
			
			List list = advproductDao.selectByAdvproductName(advproductEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, list.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("查询广告产品错误！", e);
		}
	}
	
	public void search() {
		try {
			AdvproductEty advproductEty = (AdvproductEty) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), AdvproductEty.class);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			advproductEty.setExtLimit(limit);
			int count = advproductDao.selectLimitCount(advproductEty);
			List list = advproductDao.selectByLimit(advproductEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("查询广告产品错误！", e);
		}
	}

	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			UserInfoEty userEty = getLoginUser();
			AdvproductEty advproductEty = (AdvproductEty) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), AdvproductEty.class);
			if(advproductEty.getId() == null) {
				advproductEty.setCreateTime(new Date());
				advproductEty.setCreator(userEty.getUsername());
				advproductDao.insert(advproductEty);
			} else { 
				advproductEty.setModifier(userEty.getUsername());
				advproductEty.setModifyTime(new Date());
				advproductDao.updateById(advproductEty);
			}
			obj.put("result","success");		
		}
		catch (Exception e) {
			logger.error("保存广告产品错误！", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	public void delete() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			advproductDao.deleteById(Integer.parseInt(id));
			obj.put("result","success");
		}
		catch (Exception e) {
			logger.error("删除广告产品错误！", e);
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
			AdvproductEty advproductEty = (AdvproductEty) advproductDao.selectById(Integer.parseInt(id));
			String dataStr = EntityReflect.getObjectJSonString(advproductEty, new SimpleDateFormat("yyyy-MM-dd"));
			obj.put("data", dataStr);
		}
		catch (Exception e) {
			logger.error("得到广告产品错误！", e);
			obj.put("result","error");
			obj.put("info",e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	@Resource(name="AdvproductDao")
	private AdvproductDao advproductDao;
	
}