package com.ku6ads.struts.statistics;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.statistics.StatisticsAdvActiveDay;
import com.ku6ads.dao.entity.statistics.StatisticsAdvActiveMonth;
import com.ku6ads.dao.entity.statistics.StatisticsAdvactiveViewsMonth;
import com.ku6ads.dao.entity.statistics.StatisticsAdvertisementDay;
import com.ku6ads.dao.entity.statistics.StatisticsAdvertisementMonth;
import com.ku6ads.dao.entity.statistics.StatisticsAdvertisementViewsMonth;
import com.ku6ads.dao.iface.statistics.StatisticsAdvActiveDayDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvActiveMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvactiveViewsMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvertisementDayDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvertisementMonthDao;
import com.ku6ads.dao.iface.statistics.StatisticsAdvertisementViewsMonthDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;
import com.opensymphony.xwork2.ActionSupport;

public class StatisticsCustomerAction extends ActionSupport {
	
	private static final long serialVersionUID = 1928337160819739872L;
	private Logger logger = Logger.getLogger(StatisticsCustomerAction.class);
	
	@Resource(name="StatisticsAdvActiveDayDao")
	private StatisticsAdvActiveDayDao statisticsAdvActiveDayDao;
	
	@Resource(name="StatisticsAdvActiveMonthDao")
	private StatisticsAdvActiveMonthDao statisticsAdvActiveMonthDao;
	
	@Resource(name="StatisticsAdvertisementDayDao")
	private StatisticsAdvertisementDayDao statisticsAdvertisementDayDao;
	
	@Resource(name="StatisticsAdvertisementMonthDao")
	private StatisticsAdvertisementMonthDao statisticsAdvertisementMonthDao;
	
	@Resource(name="StatisticsAdvactiveViewsMonthDao")
	private StatisticsAdvactiveViewsMonthDao statisticsAdvactiveViewsMonthDao;
	
	@Resource(name="StatisticsAdvertisementViewsMonthDao")
	private StatisticsAdvertisementViewsMonthDao statisticsAdvertisementViewsMonthDao;
	/**
	 * 广告活动月报 
	 */
	public void getAdvActiveMonthData() {
		try {
			String advActiveId = ServletActionContext.getRequest().getParameter("advActiveId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeEnd = ServletActionContext.getRequest().getParameter("stateTimeEnd");
			
			StatisticsAdvActiveMonth statEty = new StatisticsAdvActiveMonth();
			statEty.setAdvactiveId(Integer.parseInt(advActiveId));
			
			if(statTime != null && !statTime.trim().equals(""))
				statEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeEnd != null && !stateTimeEnd.trim().equals(""))
				statEty.setStateTimeEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeEnd));
			
			List<StatisticsAdvActiveMonth> dataList = statisticsAdvActiveMonthDao.statisticsSearch(statEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("广告活动月报错误！！！", e);
		}
	}
	
	/**
	 * 广告活动月报导出
	 */
	public void exportAdvActiveMonthData() {
		try {
			String advActiveId = ServletActionContext.getRequest().getParameter("advActiveId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeEnd = ServletActionContext.getRequest().getParameter("stateTimeEnd");
			StatisticsAdvActiveMonth statEty = new StatisticsAdvActiveMonth();
			statEty.setAdvactiveId(Integer.parseInt(advActiveId));
			
			if(statTime != null && !statTime.trim().equals(""))
				statEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeEnd != null && !stateTimeEnd.trim().equals(""))
				statEty.setStateTimeEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeEnd));
			
			String[] exp_column_names = {"名称", "日期", "月份", "PV", "UV", "Click", "UC", "省份", "城市"};
			String[] exp_column_indexs = {"advactiveName", "statTime", "month", "pv", "uv", "click", "uc", "province", "city"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsAdvActiveMonthDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", statEty, "广告活动月报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("广告活动月报错误！！！", e);
		}
	}
	
	/**
	 * 广告活动日报
	 */
	public void getAdvActiveDailyData() {
		try {
			String advActiveId = ServletActionContext.getRequest().getParameter("advActiveId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsAdvActiveDay searchEty = new StatisticsAdvActiveDay();
			searchEty.setAdvactiveId(Integer.parseInt(advActiveId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			List<StatisticsAdvActiveDay> dataList = statisticsAdvActiveDayDao.statisticsSearch(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("广告活动日报错误！！！", e);
		}
	}
	
	/**
	 * 广告活动日报导出
	 */
	public void exportAdvActiveDailyData() {
		try {
			String advActiveId = ServletActionContext.getRequest().getParameter("advActiveId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsAdvActiveDay searchEty = new StatisticsAdvActiveDay();
			searchEty.setAdvactiveId(Integer.parseInt(advActiveId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			String[] exp_column_names = {"名称", "日期", "hour", "PV", "UV", "Click", "UC"};
			String[] exp_column_indexs = {"advactiveName", "statTime", "hour", "pv", "uv", "click", "uc"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsAdvActiveDayDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchEty, "频道日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("广告活动日报错误！！！", e);
		}
	}
	
	/**
	 * 广告月报
	 */
	public void getAdvertisementMonthData() {
		try {
			String advertiseId = ServletActionContext.getRequest().getParameter("advertiseId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeEnd = ServletActionContext.getRequest().getParameter("stateTimeEnd");
			StatisticsAdvertisementMonth searchEty = new StatisticsAdvertisementMonth();
			searchEty.setAdvertiseId(Integer.parseInt(advertiseId));
			
			if(statTime != null && !statTime.trim().equals(""))
				searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeEnd != null && !stateTimeEnd.trim().equals(""))
				searchEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeEnd));
			
			List<StatisticsAdvertisementMonth> dataList = statisticsAdvertisementMonthDao.statisticsSearch(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("广告月报错误！！！", e);
		}
	}
	
	/**
	 * 广告月报导出
	 */
	public void exportAdvertisementMonthData() {
		try {
			String advertiseId = ServletActionContext.getRequest().getParameter("advertiseId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			String stateTimeEnd = ServletActionContext.getRequest().getParameter("stateTimeEnd");
			StatisticsAdvertisementMonth searchEty = new StatisticsAdvertisementMonth();
			searchEty.setAdvertiseId(Integer.parseInt(advertiseId));
			if(statTime != null && !statTime.trim().equals(""))
				searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			if(stateTimeEnd != null && !stateTimeEnd.trim().equals(""))
				searchEty.setStateTimeMonthEnd(new SimpleDateFormat("yyyy-MM-dd").parse(stateTimeEnd));
			
			String[] exp_column_names = {"名称", "日期", "月份", "PV", "UV", "Click", "UC", "省份", "城市"};
			String[] exp_column_indexs = {"advertiseName", "statTime", "month", "pv", "uv", "click", "uc", "province", "city"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsAdvertisementMonthDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchEty, "广告位日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("广告月报导出错误！！！", e);
		}
	}
	
	/**
	 * 广告日报
	 */
	public void getAdvertisementDailyData() {
		try {
			String advertiseId = ServletActionContext.getRequest().getParameter("advertiseId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsAdvertisementDay searchEty = new StatisticsAdvertisementDay();
			searchEty.setAdvertiseId(Integer.parseInt(advertiseId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			List<StatisticsAdvertisementDay> dataList = statisticsAdvertisementDayDao.statisticsSearch(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, dataList.size(), new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("广告日报错误！！！", e);
		}
	}
	
	/**
	 * 广告日报导出
	 */
	public void exportAdvertisementDailyData() {
		try {
			String advertiseId = ServletActionContext.getRequest().getParameter("advertiseId");
			String statTime = ServletActionContext.getRequest().getParameter("statTime");
			StatisticsAdvertisementDay searchEty = new StatisticsAdvertisementDay();
			searchEty.setAdvertiseId(Integer.parseInt(advertiseId));
			searchEty.setStatTime(new SimpleDateFormat("yyyy-MM-dd").parse(statTime));
			String[] exp_column_names = {"名称", "日期", "hour", "PV", "UV", "Click", "UC"};
			String[] exp_column_indexs = {"advertiseName", "statTime", "hour", "pv", "uv", "click", "uc"};
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), statisticsAdvertisementDayDao, "statisticsSearchLimitCount", "statisticsSearchByLimit", searchEty, "广告位日报", "data.xls", exp_column_names, exp_column_indexs);
		}
		catch (Exception e) {
			logger.error("广告日报错误！！！", e);
		}
	}
	
	/**
	 * 广告类电视数据
	 */
	public void getViewAdvertisement() {
		try {
			StatisticsAdvertisementViewsMonth statisticsAdvertisementViewsMonth = new StatisticsAdvertisementViewsMonth();
			
			String stateDayTimeStr = ServletActionContext.getRequest().getParameter("stateDayTime");
			String stateMonthTimeStart = ServletActionContext.getRequest().getParameter("stateMonthTimeStart");
			String stateMonthTimeEnd = ServletActionContext.getRequest().getParameter("stateMonthTimeEnd");
			String advertiseId = ServletActionContext.getRequest().getParameter("advertiseId");
			
			if(stateDayTimeStr != null && !stateDayTimeStr.trim().equals("")) 
				statisticsAdvertisementViewsMonth.setStateDayTime(new SimpleDateFormat("yyyy-MM-dd").parse(stateDayTimeStr));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			if(stateMonthTimeStart != null && !stateMonthTimeStart.trim().equals(""))
				statisticsAdvertisementViewsMonth.setStateMonthTimeStart(df.parse(stateMonthTimeStart));
			if(stateMonthTimeEnd != null && !stateMonthTimeEnd.trim().equals(""))
				statisticsAdvertisementViewsMonth.setStateMonthTimeEnd(df.parse(stateMonthTimeEnd));

			statisticsAdvertisementViewsMonth.setAdvertisementId(Integer.parseInt(advertiseId));
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			statisticsAdvertisementViewsMonth.setExtLimit(limit);
			int count = statisticsAdvertisementViewsMonthDao.selectLimitCount(statisticsAdvertisementViewsMonth);
			List list = statisticsAdvertisementViewsMonthDao.selectByLimit(statisticsAdvertisementViewsMonth);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("类电视数据错误！！！", e);
		}
	}
	
	/**
	 * 
	 */
	public void getViewAdvActive() {
		try {
			StatisticsAdvactiveViewsMonth statisticsAdvactiveViewsMonth = new StatisticsAdvactiveViewsMonth();
			String stateDayTimeStr = ServletActionContext.getRequest().getParameter("stateDayTime");
			String stateMonthTimeStart = ServletActionContext.getRequest().getParameter("stateMonthTimeStart");
			String stateMonthTimeEnd = ServletActionContext.getRequest().getParameter("stateMonthTimeEnd");
			String advActiveId = ServletActionContext.getRequest().getParameter("advActiveId");
			if(stateDayTimeStr != null && !stateDayTimeStr.trim().equals("")) 
				statisticsAdvactiveViewsMonth.setStateDayTime(new SimpleDateFormat("yyyy-MM-dd").parse(stateDayTimeStr));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			if(stateMonthTimeStart != null && !stateMonthTimeStart.trim().equals(""))
				statisticsAdvactiveViewsMonth.setStateMonthTimeStart(df.parse(stateMonthTimeStart));
			if(stateMonthTimeEnd != null && !stateMonthTimeEnd.trim().equals(""))
				statisticsAdvactiveViewsMonth.setStateMonthTimeEnd(df.parse(stateMonthTimeEnd));

			statisticsAdvactiveViewsMonth.setAdvActiveId(Integer.parseInt(advActiveId));
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			statisticsAdvactiveViewsMonth.setExtLimit(limit);
			
			int count = statisticsAdvactiveViewsMonthDao.selectLimitCount(statisticsAdvactiveViewsMonth);
			List list = statisticsAdvactiveViewsMonthDao.selectByLimit(statisticsAdvactiveViewsMonth);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("类电视数据错误！！！", e);
		}
	}
}
