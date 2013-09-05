package com.ku6ads.struts.advproduct;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.iface.advproduct.UpdateStatusDao;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;

public class UpdateStateAction extends BaseAction {
	
	private static final long serialVersionUID = 7374874802752864440L;
	private Logger logger = Logger.getLogger(UpdateStateAction.class);
	
	private UpdateStatusDao updateStatusDao;
	
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			String tableName = ServletActionContext.getRequest().getParameter("tableName");
			String dataId = ServletActionContext.getRequest().getParameter("dataId");
			String idColumn = ServletActionContext.getRequest().getParameter("idColumn");
			String nowStateValue = ServletActionContext.getRequest().getParameter("statusValue");
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("tableName", tableName);
			dataMap.put("id", idColumn);
			dataMap.put("dataId", dataId);
			dataMap.put("nowStateValue", nowStateValue);
			updateStatusDao.updateStatus(dataMap);
			obj.put("result","success");	
		}
		catch (Exception e) {
			logger.error("更新状态错误！", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	public UpdateStatusDao getUpdateStatusDao() {
		return updateStatusDao;
	}

	public void setUpdateStatusDao(UpdateStatusDao updateStatusDao) {
		this.updateStatusDao = updateStatusDao;
	}
	
}
