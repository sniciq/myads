package com.ku6ads.struts.sysconfig;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Area;
import com.ku6ads.services.iface.sysconfig.AreaService;
import com.ku6ads.util.AjaxOut;
import com.opensymphony.xwork2.ActionSupport;

public class AreaAction extends ActionSupport {

	private static final long serialVersionUID = 2152455491758251474L;
	protected Logger log = Logger.getLogger(getClass());

	private AreaService areaService;

	/**
	 * 获得区域列表
	 */
	public void getAreaList() {
		List<Area> areaList = areaService.selectArea();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < areaList.size(); i++) {
			Area area = areaList.get(i);
			sb.append("['" + area.getAreaId() + "','" + area.getAreaName() + "']");
			if (i < areaList.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

}
