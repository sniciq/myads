package com.ku6ads.struts.webdata;

import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.webdata.DimProductVV;
import com.ku6ads.dao.iface.webdata.DimProductVVDao;
import com.ku6ads.dao.iface.webdata.HotDataDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;
import com.opensymphony.xwork2.ActionSupport;

public class HotFilmDataVVAction extends ActionSupport {
	
	private static final long serialVersionUID = -7975393878950512315L;

	private Logger logger = Logger.getLogger(HotFilmDataAction.class);
	
	private HotDataDao hotDataDao;
	private DimProductVVDao dimProductVVDao;
	public void search() {
		try {
			HotFilmData searchEty = (HotFilmData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), HotFilmData.class);
			if(searchEty.getCityDcodeList() != null && !searchEty.getCityDcodeList().trim().equals("")) {
				String cityDcodes = searchEty.getCityDcodeList();
				cityDcodes = cityDcodes.replaceAll("DimCdnCity_", "");
				String[] ids = StringUtils.split(cityDcodes, ",");
				searchEty.setCityDcodeList(StringUtils.join(ids, ","));
			}
			if(searchEty.getProdIdList() != null && !searchEty.getProdIdList().trim().equals("")) {
				String[] ids = StringUtils.split(searchEty.getProdIdList(), ",");
				searchEty.setProdIdList(StringUtils.join(ids, ","));
			}
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			
			searchEty.setExtLimit(limit);
			int count = 0;
			List<HotFilmData> list;
			if(searchEty.getDataType().equalsIgnoreCase("每天详细")) {
				count = hotDataDao.searchEveryDayVVDataCount(searchEty);
				list = hotDataDao.searchEveryDayVVData(searchEty);
				AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
			}
			else if(searchEty.getDataType().equalsIgnoreCase("周期平均")) {
				count = hotDataDao.searchAvgVVDataCount(searchEty);
				list = hotDataDao.searchAvgVVData(searchEty);
				AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
			} 
		}
		catch (Exception e) {
			logger.error("VV查询错误！", e);
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
			if(searchEty.getProdIdList() != null && !searchEty.getProdIdList().trim().equals("")) {
				String[] ids = StringUtils.split(searchEty.getProdIdList(), ",");
				searchEty.setProdIdList(StringUtils.join(ids, ","));
			}
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			
			if(searchEty.getDataType().equalsIgnoreCase("每天详细")) {
				String sql = hotDataDao.getSql("default.webdata.hotdata.searchEveryDayVVData", searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), hotDataDao.getConnection(), sql, "数据", limit );
				return;
			}
			else if(searchEty.getDataType().equalsIgnoreCase("周期平均")) {
				String sql = hotDataDao.getSql("default.webdata.hotdata.searchAvgVVData", searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), hotDataDao.getConnection(), sql, "数据", limit );
				return;
			} 
			else {
				return;
			}
		}
		catch (Exception e) {
			logger.error("VV导出错误！", e);
		}
	}
	
	public void getProndList() {
		try {
			List list = dimProductVVDao.selectByEntity(null);
			JSONArray array = new JSONArray();
			for(int i = 0; i < list.size(); i++) {
				DimProductVV ety = (DimProductVV) list.get(i);
				JSONObject obj = new JSONObject();
				obj.put("id", ety.getVv_prod_id());
				obj.put("text", ety.getProd_name());
				obj.put("leaf", true);
				array.add(obj);
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

	public DimProductVVDao getDimProductVVDao() {
		return dimProductVVDao;
	}

	public void setDimProductVVDao(DimProductVVDao dimProductVVDao) {
		this.dimProductVVDao = dimProductVVDao;
	}
	
}
