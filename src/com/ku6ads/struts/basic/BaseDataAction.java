package com.ku6ads.struts.basic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.services.iface.baisc.BaseDataService;
import com.ku6ads.struts.basic.cache.DictionaryFactory;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.opensymphony.xwork2.ActionSupport;

public class BaseDataAction extends ActionSupport {
	private static final long serialVersionUID = 1464272831464363496L;
	private BaseDataService baseDataService;
	private Logger logger = Logger.getLogger(BaseDataAction.class);
	
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			BaseData baseDataEty = (BaseData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), BaseData.class);
			baseDataEty.setCreateTime(new Date());
			if(baseDataEty.getId() == null) {
				baseDataService.insert(baseDataEty);
			}
			else {
				baseDataService.updateById(baseDataEty);
			}
			obj.put("result", "success");
			//刷新数据字典 add by zhangyan 2010-11-23
			DictionaryFactory.getInstance().refreshDictionary();
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	public void search() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			BaseData baseDataEty = (BaseData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), BaseData.class);
			baseDataEty.setExtLimit(limit);
			int count = baseDataService.selectLimitCount(baseDataEty);
			List<BaseData> list = baseDataService.selectByLimit(baseDataEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public void getBaseDataDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("baseDataId");
			BaseData baseDataEty = (BaseData) baseDataService.selectById(Integer.parseInt(id));
			retObj.put("data", baseDataEty);
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("baseDataList");
			String[] idList = ids.split(",");
			baseDataService.deleteList(idList);
			retObj.put("result", "success");
			//刷新数据字典 add by zhangyan 2010-11-23
			DictionaryFactory.getInstance().refreshDictionary();
		}
		catch (Exception e) {
			logger.error("", e);
			retObj.put("result","error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public BaseDataService getBaseDataService() {
		return baseDataService;
	}

	public void setBaseDataService(BaseDataService baseDataService) {
		this.baseDataService = baseDataService;
	}
}
