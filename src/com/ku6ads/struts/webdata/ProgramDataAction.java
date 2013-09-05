package com.ku6ads.struts.webdata;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.iface.webdata.HotDataDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;
import com.opensymphony.xwork2.ActionSupport;

public class ProgramDataAction extends ActionSupport {
	
	private static final long serialVersionUID = 4083372686814140976L;
	private HotDataDao hotDataDao;
	private Logger logger = Logger.getLogger(FlowDataAction.class);
	
	public void search() {
		try {
			ProgramData programData = (ProgramData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ProgramData.class);
			ExtLimit extLimit =  (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			programData.setExtLimit(extLimit);
			
			int count = hotDataDao.selectProgramLimitCount(programData);
			List<ProgramData> list = hotDataDao.selectProgramLimit(programData);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public void export() {
		try {
			ProgramData programData = (ProgramData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ProgramData.class);
			ExtLimit extLimit =  (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			String sql = hotDataDao.getSql("default.webdata.hotdata.selectProgramLimit", programData);
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), hotDataDao.getConnection(), sql, "数据", extLimit);
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}

	public HotDataDao getHotDataDao() {
		return hotDataDao;
	}

	public void setHotDataDao(HotDataDao hotDataDao) {
		this.hotDataDao = hotDataDao;
	}
}
