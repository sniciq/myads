package com.ku6ads.struts.webdata;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.iface.webdata.FlowDataDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流量数据查询
 * @author YangHanguang
 *
 */
public class FlowDataAction extends ActionSupport {
	
	private static final long serialVersionUID = 1312718917720339159L;
	private FlowDataDao flowDataDao;
	private Logger logger = Logger.getLogger(FlowDataAction.class);
	
	/**
	 * 查询流量数据
	 */
	public void search() {
		try {
			FlowData searchEty = (FlowData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), FlowData.class);
			if(searchEty.getAreaIdList() != null && !searchEty.getAreaIdList().trim().equals("")) {
				String[] ids = StringUtils.split(searchEty.getAreaIdList(), ",");
				searchEty.setAreaIdList(StringUtils.join(ids, ","));
			}
			if(searchEty.getProdIdList() != null && !searchEty.getProdIdList().trim().equals("")) {
				String[] ids = StringUtils.split(searchEty.getProdIdList(), ",");
				searchEty.setProdIdList(StringUtils.join(ids, ","));
			}
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			
			if(limit.getExp_name() != null && !limit.getExp_name().trim().equals("")) {//导出
				if(searchEty.getDataType().equalsIgnoreCase("每天详细")) {
					String sql = flowDataDao.getSql("default.webdata.FlowData.searchEveryDayData", searchEty);
					XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), flowDataDao.getConnection(), sql, "数据", limit );
					return;
				}
				else if(searchEty.getDataType().equalsIgnoreCase("周期平均")) {
					String sql = flowDataDao.getSql("default.webdata.FlowData.searchAvgData", searchEty);
					XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), flowDataDao.getConnection(), sql, "数据", limit );
					return;
				}
			}
			
			searchEty.setExtLimit(limit);
			int count = 0;
			List<FlowData> list;
			if(searchEty.getDataType().equalsIgnoreCase("每天详细")) {
				count = flowDataDao.searchEveryDayDataCount(searchEty);
				list = flowDataDao.searchEveryDayData(searchEty);
				AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
			}
			else if(searchEty.getDataType().equalsIgnoreCase("周期平均")) {
				count = flowDataDao.searchAvgDataCount(searchEty);
				list = flowDataDao.searchAvgData(searchEty);
				AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
			} 
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 查询分省份数据
	 */
	public void searcProvinceData() {
		try {
			String startTime = ServletActionContext.getRequest().getParameter("startTime");
			String endTime = ServletActionContext.getRequest().getParameter("endTime");
			FlowData searchEty = (FlowData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), FlowData.class);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			if(limit.getExp_name() != null && !limit.getExp_name().trim().equals("")) {
				List<FlowData> list = flowDataDao.searchReportProvince(searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), list, "分城市数据表", limit);
				return;
			}
			
			searchEty.setStartTime(startTime);
			searchEty.setEndTime(endTime);
			searchEty.setExtLimit(limit);
			int count = flowDataDao.searchReportProvinceCount(searchEty);
			List<FlowData> list = flowDataDao.searchReportProvince(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 查询分城市数据
	 */
	public void searchCityReportData() {
		try {
			FlowData searchEty = (FlowData) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), FlowData.class);
			String startTime = ServletActionContext.getRequest().getParameter("startTime");
			String endTime = ServletActionContext.getRequest().getParameter("endTime");
			searchEty.setStartTime(startTime);
			searchEty.setEndTime(endTime);
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			
			if(limit.getExp_name() != null && !limit.getExp_name().trim().equals("")) {
				List<FlowData> list = flowDataDao.searchReportCity(searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), list, "分城市数据表", limit);
				return;
			}
			
			searchEty.setExtLimit(limit);
			int count = flowDataDao.searchReportCityCount(searchEty);
			List<FlowData> list = flowDataDao.searchReportCity(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 分时段报表
	 */
	public void searchHourReportData() {
		try {
			FlowData searchEty = new FlowData();
			String startTime = ServletActionContext.getRequest().getParameter("startTime");
			String endTime = ServletActionContext.getRequest().getParameter("endTime");
			searchEty.setStartTime(startTime);
			searchEty.setEndTime(endTime);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			
			if(limit.getExp_name() != null && !limit.getExp_name().trim().equals("")) {
				List<FlowData> list = flowDataDao.searchReportHour(searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), list, "分时段数据表", limit);
				return;
			}
			
			searchEty.setExtLimit(limit);
			List<FlowData> list = flowDataDao.searchReportHour(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, list.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 频道报表
	 */
	public void searchProdReport() {
		try {
			FlowData searchEty = new FlowData();
			String startTime = ServletActionContext.getRequest().getParameter("startTime");
			String endTime = ServletActionContext.getRequest().getParameter("endTime");
			searchEty.setStartTime(startTime);
			searchEty.setEndTime(endTime);
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			if(limit.getExp_name() != null && !limit.getExp_name().trim().equals("")) {
				List<FlowData> list = flowDataDao.searchReportProd(searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), list, "分频道数据表", limit);
				return;
			}
			
			searchEty.setExtLimit(limit);
			List<FlowData> list = flowDataDao.searchReportProd(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, list.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 分星期日报表
	 */
	public void searchReportWeekDay() {
		try {
			String startTime = ServletActionContext.getRequest().getParameter("startTime");
			String endTime = ServletActionContext.getRequest().getParameter("endTime");
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			Date selDate = df.parse(startTime);
//			Calendar cal = Calendar.getInstance();
//			cal.setFirstDayOfWeek(Calendar.MONDAY);
//			cal.setTime(selDate);
//			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//			Date startDate = cal.getTime();
//			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//			Date endDate = cal.getTime();
			FlowData searchEty = new FlowData();
			searchEty.setStartTime(startTime);
			searchEty.setEndTime(endTime);
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			if(limit.getExp_name() != null && !limit.getExp_name().trim().equals("")) {
				List<FlowData> list = flowDataDao.searchReportWeekDay(searchEty);
				XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), list, "分频道数据表", limit);
				return;
			}
			
			searchEty.setExtLimit(limit);
			List<FlowData> list = flowDataDao.searchReportWeekDay(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, list.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}

	public FlowDataDao getFlowDataDao() {
		return flowDataDao;
	}

	public void setFlowDataDao(FlowDataDao flowDataDao) {
		this.flowDataDao = flowDataDao;
	}
}
