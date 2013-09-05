package com.ku6ads.struts.webdata;

import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.webdata.DimCdnArea;
import com.ku6ads.dao.entity.webdata.DimCdnCity;
import com.ku6ads.dao.iface.webdata.DimCdnAreaDao;
import com.ku6ads.dao.iface.webdata.DimCdnCityDao;
import com.ku6ads.dao.iface.webdata.HotDataDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;
import com.opensymphony.xwork2.ActionSupport;

public class HotFilmDataAction extends ActionSupport {
	
	private static final long serialVersionUID = -5631411302290884690L;

	private Logger logger = Logger.getLogger(HotFilmDataAction.class);
	
	private HotDataDao hotDataDao;
	private DimCdnAreaDao dimCdnAreaDao;
	private DimCdnCityDao dimCdnCityDao;

	public void search() {
		try {
			HotFilmData searchEty = (HotFilmData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), HotFilmData.class);
			if(searchEty.getCityDcodeList() != null && searchEty.getCityDcodeList().endsWith(",")) {
				String cityDcodes = searchEty.getCityDcodeList();
				cityDcodes = cityDcodes.replaceAll("DimCdnCity_", "");
				String[] ids = StringUtils.split(cityDcodes, ",");
				searchEty.setCityDcodeList(StringUtils.join(ids, ","));
			}
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			
			searchEty.setExtLimit(limit);
			int count = 0;
			List<HotFilmData> list;
			if(searchEty.getDataType().equalsIgnoreCase("每天详细")) {
				count = hotDataDao.searchEveryDayDataCount(searchEty);
				list = hotDataDao.searchEveryDayData(searchEty);
				AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
			}
			else if(searchEty.getDataType().equalsIgnoreCase("周期平均")) {
				count = hotDataDao.searchAvgDataCount(searchEty);
				list = hotDataDao.searchAvgData(searchEty);
				AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
			} 
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public void export() {
		try {
			HotFilmData searchEty = (HotFilmData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), HotFilmData.class);
			if(searchEty.getCityDcodeList() != null && !searchEty.getCityDcodeList().trim().equals("")) {
				String cityDcodes = searchEty.getCityDcodeList();
				cityDcodes = cityDcodes.replaceAll("DimCdnCity_", "");
				String[] ids = StringUtils.split(cityDcodes, ",");
				searchEty.setCityDcodeList(StringUtils.join(ids, ","));
			}
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			if(searchEty.getDataType().equalsIgnoreCase("每天详细")) {
				String sql = hotDataDao.getSql("default.webdata.hotdata.searchEveryDayData", searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), hotDataDao.getConnection(), sql, "数据", limit );
				return;
			}
			else if(searchEty.getDataType().equalsIgnoreCase("周期平均")) {
				String sql = hotDataDao.getSql("default.webdata.hotdata.searchAvgData", searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), hotDataDao.getConnection(), sql, "数据", limit );
				return;
			} 
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public void getAreaCityList() {
		try {
			String node = ServletActionContext.getRequest().getParameter("node");
			JSONArray array = new JSONArray();
			
			if(node == null || node.trim().equals("") || node.trim().equals("0")) {//查询地区
				List list = dimCdnAreaDao.selectByEntity(null);
				for(int i = 0; i < list.size(); i++) {
					DimCdnArea dimCdnAreaEty = (DimCdnArea) list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", "DimCdnArea_" + dimCdnAreaEty.getPcode());//使用PCODE作为ID
					obj.put("text", dimCdnAreaEty.getProvince());
					obj.put("leaf", false);
					array.add(obj);
				}
			}
			else {//查询地区下的城市
				node = node.replace("DimCdnArea_", "");
				DimCdnCity cityEty = new DimCdnCity();
				cityEty.setPcode(Integer.parseInt(node));
				List list = dimCdnCityDao.selectByEntity(cityEty);
				for(int i = 0; i < list.size(); i++) {
					DimCdnCity dimCdnCityEty = (DimCdnCity) list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", "DimCdnCity_" + dimCdnCityEty.getId());//使用PCODE作为ID
					obj.put("text", dimCdnCityEty.getCity());
					obj.put("leaf", true);
					array.add(obj);
				}
			}
			
			AjaxOut.responseText(ServletActionContext.getResponse(), array.toString());
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

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public DimCdnAreaDao getDimCdnAreaDao() {
		return dimCdnAreaDao;
	}

	public void setDimCdnAreaDao(DimCdnAreaDao dimCdnAreaDao) {
		this.dimCdnAreaDao = dimCdnAreaDao;
	}

	public DimCdnCityDao getDimCdnCityDao() {
		return dimCdnCityDao;
	}

	public void setDimCdnCityDao(DimCdnCityDao dimCdnCityDao) {
		this.dimCdnCityDao = dimCdnCityDao;
	}
}
