package com.ku6ads.struts.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.City;
import com.ku6ads.dao.entity.sysconfig.Province;
import com.ku6ads.services.iface.common.ProviceService;
import com.ku6ads.util.AjaxOut;
import com.opensymphony.xwork2.ActionSupport;

public class ProviceAction extends ActionSupport {
	
	private static final long serialVersionUID = -8360817041611744527L;
	private Logger logger = Logger.getLogger(ProviceAction.class);
	
	private ProviceService proviceService;
	
	/**
	 * 得到所有的省份
	 */
	public void getAllProvice() {
		try {
			List<Province> list = proviceService.getProviceList();
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, list.size(), null);
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 得到省份下的所有城市
	 */
	public void getAllCityInProvice() {
		try {
			String pcode = ServletActionContext.getRequest().getParameter("pcode");
			List<City> list = proviceService.getCityList(Integer.parseInt(pcode));
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, list.size(), null);
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}

	public ProviceService getProviceService() {
		return proviceService;
	}

	public void setProviceService(ProviceService proviceService) {
		this.proviceService = proviceService;
	}
	
}
